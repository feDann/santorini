package it.polimi.ingsw.PSP11.view.gui.controller;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.SelectGameGodResponse;
import it.polimi.ingsw.PSP11.messages.SelectGameGodsRequest;
import it.polimi.ingsw.PSP11.messages.SelectPlayerGodRequest;
import it.polimi.ingsw.PSP11.model.Card;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SelectGodsController extends GUIController {
    private final String imageStyle = "-fx-background-image: url(";
    private ArrayList<Card> gods;
    private HashMap<Card, ImageView> selectedGodsMap = new HashMap<>();
    private int numOfPlayers;


    @FXML
    private Pane initPane,waitPane,selectPane;

    @FXML
    private Text waitingText;

    @FXML
    private StackPane centerStack,leftStack,rightStack;

    @FXML
    private Button rightButton,leftButton,send;

    @FXML
    private VBox selectedGods;

    @FXML
    private TextArea description,player1,player2,player3;

    public void initialize(){
        //initPane.getStyleClass().add();
        waitPane.setVisible(true);
        selectPane.setVisible(false);
        player1.setText(getNickname());
        player1.setStyle("-fx-text-fill: " + getColor()+";");
        player2.setText(getOpponents().get(0).getName());
        player2.setStyle("-fx-text-fill: " + getOpponents().get(0).getColor()+";");

        if(getOpponents().size()>1){
            player3.setText(getOpponents().get(1).getName());
            player3.setStyle("-fx-text-fill: " + getOpponents().get(1).getColor()+";");
        }
        else{
            player3.setVisible(false);
        }
    }


    @FXML
    void goLeft(ActionEvent event) {
        Collections.rotate(gods, +1);
        updateStackPane();
    }

    @FXML
    void goRight(ActionEvent event) {
        Collections.rotate(gods, -1);
        updateStackPane();
    }
    @FXML
    public void sendSelection(ActionEvent event) {
        if(selectedGodsMap.size()==numOfPlayers){
            ArrayList<Integer> ids = new ArrayList<>();
            for(Card card : selectedGodsMap.keySet()){
                ids.add(card.getIdCard()-1);
                selectedGods.getChildren().remove(selectedGodsMap.get(card));
            }
            selectedGodsMap.clear();
            getClient().asyncWrite(new SelectGameGodResponse(ids));
        }
    }

    @FXML
    void selectStackPane(MouseEvent event) {
        Platform.runLater(()-> {

                    if (!selectedGodsMap.containsKey(gods.get(1))) {
                        if(selectedGodsMap.size()<numOfPlayers) {
                            ImageView image = new ImageView();

                            image.setImage(new Image(getClass().getResource(gods.get(1).getTexture()).toString()));
                            image.setFitWidth(selectedGods.getWidth());
                            image.setPreserveRatio(true);

                            selectedGodsMap.put(gods.get(1), image);
                            System.out.println(selectedGodsMap);
                            selectedGods.getChildren().add(image);
                        }

                    } else {
                        selectedGods.getChildren().remove(selectedGodsMap.get(gods.get(1)));
                        selectedGodsMap.remove(gods.get(1));

                    }

                }
        );
    }



    public void updateStackPane(){
        String god1 = imageStyle + getClass().getResource(gods.get(0).getTexture()) +");";
        String god2 = imageStyle + getClass().getResource(gods.get(1).getTexture()) +")";
        String god3 = imageStyle + getClass().getResource(gods.get(2).getTexture()) +")";
        leftStack.setStyle(god1);
        centerStack.setStyle(god2);
        rightStack.setStyle(god3);
        description.setText(gods.get(1).getDescription());
    }


    private void selectGodScene() {
        waitPane.setVisible(false);
        selectPane.setVisible(true);

        leftStack.getStyleClass().add("stack-pane");
        centerStack.getStyleClass().add("stack-pane");
        rightStack.getStyleClass().add("stack-pane");



        updateStackPane();

    }


    @Override
    public void changeStage() {

    }

    @Override
    public void handleMessage(Message message) {
        if(message instanceof SelectGameGodsRequest){
            gods = ((SelectGameGodsRequest) message).getGods();
            numOfPlayers = ((SelectGameGodsRequest) message).getNumOfPlayers();
            selectGodScene();
        }
        if(message instanceof SelectPlayerGodRequest){
            gods = ((SelectPlayerGodRequest) message).getChosenCards();
            selectGodScene();
        }
    }


}
