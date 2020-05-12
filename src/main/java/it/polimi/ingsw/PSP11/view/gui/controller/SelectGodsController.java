package it.polimi.ingsw.PSP11.view.gui.controller;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Card;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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


        waitPane.setVisible(true);
        selectPane.setVisible(false);

        leftStack.getStyleClass().add("stack-pane");
        centerStack.getStyleClass().add("stack-pane");
        rightStack.getStyleClass().add("stack-pane");

        leftStack.getStyleClass().add("leftArrow");
        rightStack.getStyleClass().add("rightArrow");

        sendGameGods.getStyleClass().add("doneButton");
        sendPlayerGod.getStyleClass().add("doneButton");

        player1.setScrollLeft(0);
        player2.setScrollLeft(0);
        player3.setScrollLeft(0);

        player1.setScrollTop(0);
        player2.setScrollTop(0);
        player3.setScrollTop(0);

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

                selectedGodsMap.clear();
                getClient().asyncWrite(new SelectGameGodResponse(ids));
                waitScene();
            } else{
                //TODO create alertbox
            }
        });


    }


    @FXML
    public void sendSelectionForPlayerGod(ActionEvent event) {
        Platform.runLater(() -> {
            if(selectedGodsMap.size()==1){
                getClient().asyncWrite(new SelectPlayerGodResponse(immutableGods.indexOf(new ArrayList<>(selectedGodsMap.keySet()).get(0))));
                selectedGodsMap.clear();
                waitScene();
            }else{
                //TODO create alertbox
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
                            System.out.println(selectedGodsMap);
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
    }


    @Override
    public void changeStage() {

    }

    @Override
    public void handleMessage(Message message) {

        if(message instanceof SelectGameGodsRequest){
            gods = ((SelectGameGodsRequest) message).getGods();
            maxSelection = ((SelectGameGodsRequest) message).getNumOfPlayers();
            selectGameGodScene();
        }

        if(message instanceof SelectPlayerGodRequest){
            maxSelection = 1;
            immutableGods = ((SelectPlayerGodRequest) message).getChosenCards();
            gods = new ArrayList<>(immutableGods);
            selectPlayerGodScene();
        }
    }



}
