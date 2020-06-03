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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class SelectGodsController extends GUIController {

    private final String imageStyle = "-fx-background-image: url(";
    private final String font = "/font/LillyBelle400.ttf" ;
    private ArrayList<Card> immutableGods;
    private ArrayList<Card> gods;
    private HashMap<Card, ImageView> selectedGodsMap = new HashMap<>();
    private int maxSelection;
    private Card centerCard;
    private Message buffer;

    @FXML
    private Pane initPane,waitPane,selectPane, selectedGodsPane, disconnectionPane;

    @FXML
    private Text waitingText,disconnectionText,selectionText,errorText;

    @FXML
    private StackPane centerStack,leftStack,rightStack;

    @FXML
    private Button rightButton,leftButton,sendGameGods, sendPlayerGod,closeButton;

    @FXML
    private VBox selectedGods,playerBox;


    /**
     * Initialize the scene setting the visibility of the panes , loading fonts and style
     */

    public void initialize(){
        disconnectionPane.setVisible(false);


        waitScene();

        leftStack.getStyleClass().add("stack-pane");
        centerStack.getStyleClass().add("stack-pane");
        rightStack.getStyleClass().add("stack-pane");

        leftStack.getStyleClass().add("leftArrow");
        rightStack.getStyleClass().add("rightArrow");

        sendGameGods.getStyleClass().add("doneButton");
        sendPlayerGod.getStyleClass().add("doneButton");
        waitingText.setFont(Font.loadFont(getClass().getResource(font).toString(),40));
        selectionText.setFont(Font.loadFont(getClass().getResource(font).toString(),40));
        disconnectionText.setFont(Font.loadFont(getClass().getResource(font).toString(),33));
        errorText.setFont(Font.loadFont(getClass().getResource(font).toString(),27));
        playerBox.setAlignment(Pos.CENTER);
        playerBox.setSpacing(8);

        selectedGods.setSpacing(26);


        initializePlayerBox();



    }

    /**
     * Called when the {@link SelectGodsController#leftButton} is pressed, rotate to left the list of cards and update the visualization
     */

    @FXML
    void goLeft(ActionEvent event) {
        Collections.rotate(gods, +1);
        if(sendGameGods.isVisible()){
            updateStackPaneForGameGods();
        }else{
            updateStackPaneForPlayerGod();
        }
    }

    /**
     * Called when the {@link SelectGodsController#rightButton} is pressed, rotate to right the list of cards and update the visualization
     */

    @FXML
    void goRight(ActionEvent event) {
        Collections.rotate(gods, -1);
        if(sendGameGods.isVisible()){
            updateStackPaneForGameGods();
        }else{
            updateStackPaneForPlayerGod();
        }
    }

    /**
     * Show a tip the the cursor is holt on {@link SelectGodsController#centerStack}
     */

    @FXML
    public void showTip(MouseEvent mouseEvent) {
        Tooltip tp = new Tooltip("click to select");
        Tooltip.install(centerStack,tp);
    }

    /**
     * Called when the {@link SelectGodsController#sendGameGods} button is pressed,if it's possible send the selected gods to the server, show an error text otherwise
     */

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
                errorText.setVisible(true);
                errorText.setText("KEEP CHOOSING!");
            }
        });


    }

    /**
     *Called when the {@link SelectGodsController#closeButton} is pressed, close the stage
     */

    @FXML
    public void closeClient(ActionEvent event){
        Stage stage = (Stage) initPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Add to the {@link SelectGodsController#playerBox} the name of the player in game
     */

    public void initializePlayerBox(){
        double width = playerBox.getPrefWidth();
        Text selfText = new Text(getNickname());
        selfText.setFont(Font.loadFont(getClass().getResource(font).toString(),15));
        selfText.setWrappingWidth(width);
        selfText.setFill(Paint.valueOf(getColor().toString().toLowerCase()));
        selfText.setTextAlignment(TextAlignment.CENTER);
        playerBox.getChildren().add(selfText);

        for(PlayerInfo player: getOpponents()){
            Text opponentText = new Text(player.getName());
            opponentText.setFont(Font.loadFont(getClass().getResource(font).toString(),15));
            opponentText.setFill(Paint.valueOf(player.getColor().toString().toLowerCase()));
            opponentText.setWrappingWidth(width);
            opponentText.setTextAlignment(TextAlignment.CENTER);
            playerBox.getChildren().add(opponentText);
        }
    }

    /**
     *Called when the {@link SelectGodsController#sendPlayerGod} button is pressed, if it's possible send the selected god to the server, show an error text otherwise
     */

    @FXML
    public void sendSelectionForPlayerGod(ActionEvent event) {
        Platform.runLater(() -> {
            if(selectedGodsMap.size()==1){
                setPlayerCard(new ArrayList<>(selectedGodsMap.keySet()).get(0));
                getClient().asyncWrite(new SelectPlayerGodResponse(immutableGods.indexOf(new ArrayList<>(selectedGodsMap.keySet()).get(0))));
                selectedGodsMap.clear();
                waitScene();
            }else{
                errorText.setVisible(true);
                errorText.setText("CHOOSE ONE GOD");
            }
        });



    }

    /**
     * Called when the {@link SelectGodsController#centerStack} is pressed, add to the {@link SelectGodsController#selectedGodsMap} and {@link SelectGodsController#selectedGods} the {@link SelectGodsController#centerCard}. If the card is already in the {@link SelectGodsController#selectedGodsMap} then it is removed.
     */

    @FXML
    void selectStackPane(MouseEvent event) {
        Platform.runLater(()-> {
                    errorText.setVisible(false);
                    if (!selectedGodsMap.containsKey(centerCard)) {
                        if(selectedGodsMap.size() < maxSelection) {
                            ImageView image = new ImageView();

                            image.setImage(new Image(getClass().getResource(centerCard.getTexture()).toString()));
                            image.setFitWidth(selectedGods.getPrefWidth());
                            image.setFitHeight(selectedGods.getPrefHeight());


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


    /**
     * Update the image in the {@link SelectGodsController#leftStack}, {@link SelectGodsController#rightStack}, {@link SelectGodsController#centerStack} and set the new {@link SelectGodsController#centerCard}
     */

    public void updateStackPaneForGameGods(){
        Platform.runLater(()->{
            String god1 = imageStyle + getClass().getResource(gods.get(0).getTexture()) +");";
            String god2 = imageStyle + getClass().getResource(gods.get(1).getTexture()) +")";
            String god3 = imageStyle + getClass().getResource(gods.get(2).getTexture()) +")";
            leftStack.setStyle(god1);
            centerStack.setStyle(god2);
            rightStack.setStyle(god3);

            centerCard = gods.get(1);
        });
    }

    /**
     * Update the image in the {@link SelectGodsController#leftStack}, {@link SelectGodsController#rightStack}, {@link SelectGodsController#centerStack} and set the new {@link SelectGodsController#centerCard}
     */

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


                centerCard = gods.get(0);
            }
            else{
                leftStack.setVisible(true);
                rightStack.setVisible(true);

                leftButton.setVisible(true);
                rightButton.setVisible(true);

                centerCard = gods.get(1);



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

    /**
     * Set the {@link SelectGodsController#selectPane} and the {@link SelectGodsController#sendGameGods} visible and update the {@link SelectGodsController#selectionText}
     * @param numOfPlayers number of players in the game
     */

    private void selectGameGodScene(int numOfPlayers) {
        waitPane.setVisible(false);
        selectionText.setText("CHOOSE " + numOfPlayers + " GODS TO USE IN THE GAME");
        selectPane.setVisible(true);
        selectedGodsPane.setVisible(true);

        sendGameGods.setVisible(true);
        sendPlayerGod.setVisible(false);

        updateStackPaneForGameGods();

    }

    /**
     * Set the {@link SelectGodsController#selectPane} and the {@link SelectGodsController#sendPlayerGod} visible and update the {@link SelectGodsController#selectionText}
     */

    private void selectPlayerGodScene() {
        waitPane.setVisible(false);
        selectPane.setVisible(true);
        selectionText.setText("CHOOSE YOUR GOD");
        selectedGodsPane.setVisible(true);

        sendGameGods.setVisible(false);
        sendPlayerGod.setVisible(true);


        updateStackPaneForPlayerGod();

    }

    /**
     * set the {@link SelectGodsController#waitPane} visible and load a text animation
     */

    private void waitScene(){
        waitPane.setVisible(true);
        selectPane.setVisible(false);
        selectedGodsPane.setVisible(false);

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

    /**
     * Called when a {@link ConnectionClosedMessage} is sent by the server, set the  {@link SelectGodsController#disconnectionPane} visible
     * @param message the disconnection message of the server
     */

    public void connectionClosedView(String message){
        Platform.runLater(()->{
            waitPane.setEffect(new GaussianBlur());
            selectPane.setEffect(new GaussianBlur());
            disconnectionPane.setVisible(true);
            disconnectionText.setText(message.toUpperCase());
        });
    }


    /**
     * {@inheritDoc}
     */

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
                if(buffer!=null){
                    getClient().getGuiController().handleMessage(buffer);
                }

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


    /**
     *{@inheritDoc}
     */

    @Override
    public void handleMessage(Message message) {

        if(message instanceof SelectGameGodsRequest){
            gods = ((SelectGameGodsRequest) message).getGods();
            maxSelection = ((SelectGameGodsRequest) message).getNumOfPlayers();
            selectGameGodScene( ((SelectGameGodsRequest) message).getNumOfPlayers());
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
            getClient().setActive(false);
            connectionClosedView(message.getMessage());
        }
        else if(message instanceof PlaceWorkerRequest){
            buffer = message;
        }
    }



}

