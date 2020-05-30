package it.polimi.ingsw.PSP11.view.gui.controller;

import it.polimi.ingsw.PSP11.messages.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class InitialSceneController extends GUIController{

    private Message buffer;
    private final String font = "/font/LillyBelle400.ttf" ;

    @FXML
    private Pane initialPane,connectionPane,disconnectionPane,waitPane,setupPane;

    @FXML
    private TextField ip, nickname;

    @FXML
    private Button connection,playButton,exit;

    @FXML
    private Text errorText,numOfPlayersText,errorText2,opponentText,disconnectionText;

    @FXML
    private Group radioButtons;

    @FXML
    private RadioButton twoPlayers,threePlayers;

    @FXML
    private ToggleGroup numOfPlayers;


    @FXML
    public void initialize(){
        ip.setVisible(true);
        disconnectionPane.setVisible(false);
        setupPane.setVisible(false);
        waitPane.setVisible(false);
        connectionPane.setVisible(true);
        disconnectionText.setFont(Font.loadFont(getClass().getResource(font).toString(),33));
        opponentText.setFont(Font.loadFont(getClass().getResource(font).toString(),25));
    }


    @FXML
    public void sendIp(ActionEvent event) {
        Task task = new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                errorText.setVisible(false);
                getClient().setIp(ip.getText());
                try{
                    getClient().start();
                }catch (Exception e) {
                    errorText.setVisible(true);
                    errorText.setText("Invalid Ip!");
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void sendNickname(ActionEvent event){
        Platform.runLater(()->{
            errorText2.setVisible(false);
            if(numOfPlayers.getSelectedToggle() != null ) {
                if(!nickname.getText().equals("")) {
                    String nick = nickname.getText();
                    getClient().asyncWrite(new NicknameMessage(nick));
                    setNickname(nick);
                }else{
                    nickname.setPromptText("Nickname can't be empty!");
                    nickname.setStyle("-fx-prompt-text-fill: indianred;");
                }
            }
            else{
                errorText2.setVisible(true);
            }
        });

    }

    @FXML
    public void resetField(MouseEvent event){
        nickname.setText("");
        nickname.setPromptText("");
    }

    @FXML
    public void exit(ActionEvent event){
        Stage stage = (Stage) initialPane.getScene().getWindow();
        stage.close();
    }

    private void setupInfoScene() {
        connectionPane.setVisible(false);
        setupPane.setVisible(true);

    }

    private void waitOpponentScene(){
        setupPane.setVisible(false);
        connectionPane.setVisible(false);
        waitPane.setVisible(true);

        Platform.runLater(() ->{
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> {
                        String text = opponentText.getText();
                        opponentText.setText(
                                ("WAIT FOR OPPONENT . . .".equals(text))
                                        ? "WAIT FOR OPPONENT ."
                                        : text + " ."
                        );
                    }),
                    new KeyFrame(Duration.millis(500))
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }

    private void connectionClosedView(String message){
        Platform.runLater(()->{
            if(setupPane.isVisible()){
                setupPane.setEffect(new GaussianBlur());
            }
            if(waitPane.isVisible()){
                waitPane.setEffect(new GaussianBlur());
            }
            disconnectionText.setText(message);
            disconnectionPane.setVisible(true);
        });
    }


    @Override
    public void changeStage() {
        Platform.runLater(() -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/selectGods.fxml"));
                Parent root = loader.load();
                GUIController newController = loader.getController();
                getClient().setController(newController);
                newController.setClient(getClient());
                Scene scene = new Scene(root);
                Stage newStage = (Stage) initialPane.getScene().getWindow();
                newStage.setScene(scene);
                newStage.show();
                if(buffer!=null){
                    getClient().getGuiController().handleMessage(buffer);
                }

            }catch (Exception e){
                e.getStackTrace();
            }
        });
    }


    @Override
    public void handleMessage(Message message) {
        if(message instanceof WelcomeMessage){
            setupInfoScene();
        }
        else if(message instanceof ConnectionMessage){
            String numOfPlayers = ((RadioButton)this.numOfPlayers.getSelectedToggle()).getText();
            getClient().asyncWrite(new PlayerSetupMessage(numOfPlayers));
        }
        else if(message instanceof DuplicateNicknameMessage){
            nickname.clear();
            nickname.setPromptText("Nickname already in use...");
            nickname.setStyle("-fx-prompt-text-fill: indianred;");
        }
        else if(message instanceof WaitMessage){
            waitOpponentScene();
        }
        else if(message instanceof OpponentMessage){
            setOpponents(((OpponentMessage)message).getOpponents());
            setColor(((OpponentMessage) message).getColor());
            changeStage();
        }
        else if(message instanceof SelectGameGodsRequest){
            buffer=message;
        }
        else if(message instanceof ConnectionClosedMessage){
            getClient().setActive(false);
            connectionClosedView(message.getMessage().toUpperCase());
        }

    }

}
