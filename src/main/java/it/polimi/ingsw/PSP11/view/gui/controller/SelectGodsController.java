package it.polimi.ingsw.PSP11.view.gui.controller;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Card;
import it.polimi.ingsw.PSP11.utils.PlayerInfo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class SelectGodsController extends GUIController {

    private final String imageStyle = "-fx-background-image: url(";
    private final String textFillStyle = "-fx-text-fill:";
    private ArrayList<Card> immutableGods;
    private ArrayList<Card> gods;
    private HashMap<Card, ImageView> selectedGodsMap = new HashMap<>();
    private int maxSelection;
    private Card centerCard;

    @FXML
    private Pane initPane,waitPane,selectPane;

    @FXML
    private Text waitingText;

    @FXML
    private StackPane centerStack,leftStack,rightStack;

    @FXML
    private Button rightButton,leftButton,sendGameGods, sendPlayerGod;

    @FXML
    private VBox selectedGods;

    @FXML
    private TextArea description,player1,player2,player3;

    public void initialize(){


        waitScene();

        leftStack.getStyleClass().add("stack-pane");
        centerStack.getStyleClass().add("stack-pane");
        rightStack.getStyleClass().add("stack-pane");

        leftStack.getStyleClass().add("leftArrow");
        rightStack.getStyleClass().add("rightArrow");

        sendGameGods.getStyleClass().add("doneButton");
        sendPlayerGod.getStyleClass().add("doneButton");
        waitingText.setFont(Font.loadFont(getClass().getResource("/font/LillyBelle400.ttf").toString(),40));

        player1.setWrapText(true);
        player2.setWrapText(true);
        player3.setWrapText(true);

        //player1.getStyleClass().add("text-area");

        description.setWrapText(true);

        player1.setText(getNickname());

        player1.setStyle(textFillStyle + getColor()+";");
        player2.setText(getOpponents().get(0).getName());
        player2.setStyle(textFillStyle + getOpponents().get(0).getColor()+";");


        if(getOpponents().size()>1){
            player3.setText(getOpponents().get(1).getName());
            player3.setStyle(textFillStyle + getOpponents().get(1).getColor()+";");
        }
        else{
            player3.setVisible(false);
        }
    }


    @FXML
    void goLeft(ActionEvent event) {
        Collections.rotate(gods, +1);
        if(sendGameGods.isVisible()){
            updateStackPaneForGameGods();
        }else{
            updateStackPaneForPlayerGod();
        }
    }

    @FXML
    void goRight(ActionEvent event) {
        Collections.rotate(gods, -1);
        if(sendGameGods.isVisible()){
            updateStackPaneForGameGods();
        }else{
            updateStackPaneForPlayerGod();
        }
    }

    public void showTip(MouseEvent mouseEvent) {
        Tooltip tp = new Tooltip("clicca per selezionare");
        Tooltip.install(centerStack,tp);
    }

    @FXML
    public void sendSelectionForGameGods(ActionEvent event) {
        Platform.runLater(() -> {
            if(selectedGodsMap.size()==maxSelection){
                ArrayList<Integer> ids = new ArrayList<>();

                for(Card card : selectedGodsMap.keySet()){
                    ids.add(card.getIdCard()-1);
                    selectedGods.getChildren().remove(selectedGodsMap.get(card));
                }
                centerStack.getStyleClass().clear();
                leftStack.getStyleClass().clear();
                rightStack.getStyleClass().clear();


                leftStack.getStyleClass().add("stack-pane");
                centerStack.getStyleClass().add("stack-pane");
                rightStack.getStyleClass().add("stack-pane");
                selectedGodsMap.clear();
                getClient().asyncWrite(new SelectGameGodResponse(ids));
                waitScene();
            } else{
                //TODO create Text Field for error
            }
        });


    }


    @FXML
    public void sendSelectionForPlayerGod(ActionEvent event) {
        Platform.runLater(() -> {
            if(selectedGodsMap.size()==1){
                setPlayerCard(new ArrayList<>(selectedGodsMap.keySet()).get(0));
                getClient().asyncWrite(new SelectPlayerGodResponse(immutableGods.indexOf(new ArrayList<>(selectedGodsMap.keySet()).get(0))));
                selectedGodsMap.clear();
                waitScene();
            }else{
                //TODO create Text Field for error
            }
        });



    }

    @FXML
    void selectStackPane(MouseEvent event) {
        Platform.runLater(()-> {

                    if (!selectedGodsMap.containsKey(centerCard)) {
                        if(selectedGodsMap.size() < maxSelection) {
                            ImageView image = new ImageView();

                            image.setImage(new Image(getClass().getResource(centerCard.getTexture()).toString()));
                            image.setFitWidth(selectedGods.getWidth());
                            image.setPreserveRatio(true);

                            selectedGodsMap.put(centerCard, image);
                            selectedGods.getChildren().add(image);
                        }

                    } else {
                        selectedGods.getChildren().remove(selectedGodsMap.get(centerCard));
                        selectedGodsMap.remove(centerCard);

                    }

                }
        );
    }



    public void updateStackPaneForGameGods(){
        Platform.runLater(()->{
            String god1 = imageStyle + getClass().getResource(gods.get(0).getTexture()) +");";
            String god2 = imageStyle + getClass().getResource(gods.get(1).getTexture()) +")";
            String god3 = imageStyle + getClass().getResource(gods.get(2).getTexture()) +")";
            leftStack.setStyle(god1);
            centerStack.setStyle(god2);
            rightStack.setStyle(god3);
            description.setText(gods.get(1).getDescription());
            centerCard = gods.get(1);
        });
    }


    public void updateStackPaneForPlayerGod(){
        Platform.runLater(() ->{
            if(gods.size() == 1){
                String god = imageStyle +  getClass().getResource(gods.get(0).getTexture()) +");";

                centerStack.setStyle(god);
                centerStack.getStyleClass().add("centerStackPane");
                leftStack.setVisible(false);
                rightStack.setVisible(false);
                leftButton.setVisible(false);
                rightButton.setVisible(false);

                description.setText(gods.get(0).getDescription());
                centerCard = gods.get(0);
            }
            else{
                leftStack.setVisible(true);
                rightStack.setVisible(true);

                leftButton.setVisible(true);
                rightButton.setVisible(true);

                centerCard = gods.get(1);

                description.setText(gods.get(1).getDescription());

                String god1 = imageStyle + getClass().getResource(gods.get(0).getTexture()) +");";
                String god2 = imageStyle + getClass().getResource(gods.get(1).getTexture()) +")";

                leftStack.setStyle(god1);
                centerStack.setStyle(god2);

                if(gods.size() == 3){
                    String god3 = imageStyle + getClass().getResource(gods.get(2).getTexture()) +")";
                    rightStack.setStyle(god3);
                }
                else{
                    rightStack.setStyle(god1);
                }
            }
        });

    }


    private void selectGameGodScene() {
        waitPane.setVisible(false);
        selectPane.setVisible(true);

        sendGameGods.setVisible(true);
        sendPlayerGod.setVisible(false);


        updateStackPaneForGameGods();

    }

    private void selectPlayerGodScene() {
        waitPane.setVisible(false);
        selectPane.setVisible(true);

        sendGameGods.setVisible(false);
        sendPlayerGod.setVisible(true);


        updateStackPaneForPlayerGod();

    }

    private void waitScene(){
        waitPane.setVisible(true);
        selectPane.setVisible(false);

        Platform.runLater(() ->{
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> {
                        String text = waitingText.getText();
                        waitingText.setText(
                                ("GOD SELECTION IN PROCESS, PLEASE WAIT . . .".equals(text))
                                        ? "GOD SELECTION IN PROCESS, PLEASE WAIT ."
                                        : text + " ."
                        );
                    }),
                    new KeyFrame(Duration.millis(500))
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }


    @Override
    public void changeStage() {
        Platform.runLater(() -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameScene.fxml"));
                Parent root = loader.load();
                GUIController newController = loader.getController();
                getClient().setController(newController);
                newController.setClient(getClient());
                Scene scene = new Scene(root);
                Stage newStage = (Stage) initPane.getScene().getWindow();
                newStage.setScene(scene);
                newStage.show();

            }catch (Exception e){
                e.getStackTrace();
            }
        });
    }
    private void setOpponentCard(Card card, String opponent){
        for(PlayerInfo info : getOpponents()){
            if(info.getName().equals(opponent)){
                info.setCard(card);
            }
        }
    }



    @Override
    public void handleMessage(Message message) {

        if(message instanceof SelectGameGodsRequest){
            gods = ((SelectGameGodsRequest) message).getGods();
            maxSelection = ((SelectGameGodsRequest) message).getNumOfPlayers();
            selectGameGodScene();
        }

        else if(message instanceof SelectPlayerGodRequest){
            maxSelection = 1;
            immutableGods = ((SelectPlayerGodRequest) message).getChosenCards();
            gods = new ArrayList<>(immutableGods);
            selectPlayerGodScene();
        }
        else if(message instanceof StartGameMessage){
            changeStage();
        }
        else if(message instanceof OpponentCardMessage){
            setOpponentCard(((OpponentCardMessage) message).getOpponentCard(), ((OpponentCardMessage) message).getOpponent());
        }
        else if(message instanceof ConnectionClosedMessage){
            //TODO popup for closure (on popup close also close the application)
        }
    }



}
