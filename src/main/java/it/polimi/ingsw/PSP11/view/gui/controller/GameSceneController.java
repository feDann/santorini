package it.polimi.ingsw.PSP11.view.gui.controller;


import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Board;

import it.polimi.ingsw.PSP11.model.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.awt.*;

public class GameSceneController extends GUIController {


    @FXML
    private Pane initPane,actionPane,imagePane;

    @FXML
    private TextArea serverLog;

    @FXML
    private GridPane imageGrid,actionGrid;

    @FXML
    public void initialize(){
        initPane.setVisible(true);
        imagePane.setVisible(true);
//        imageGrid.setVisible(true);
        initializeActionGridButtons();
        actionPane.setVisible(false);
        serverLog.setWrapText(true);
        serverLog.setEditable(false);


    }

    @FXML
    public void placeWorkerAction(ActionEvent event){
        Platform.runLater(() -> {
            String[] cord = ((Button) event.getSource()).getId().split(",");
            Point position = new Point(Integer.parseInt(cord[0]),Integer.parseInt(cord[1]));
            getClient().asyncWrite(new PlaceWorkerResponse(position));
            actionPane.setVisible(false);
        });
    }


    public void initializeActionGridButtons(){
        for(int x = 0;x <5;x++){
            for(int y = 0; y<5; y++){
                Button button = new Button();
                button.setOnAction(this::placeWorkerAction);
                button.setId(x+","+y);
                button.getStyleClass().add("moveButton");
                button.setPrefWidth(actionGrid.getPrefWidth()/5);
                button.setPrefHeight(actionGrid.getPrefHeight()/5);
                actionGrid.add(button,y,x);
            }
        }
    }



    public String chooseWorker(Color color){
        if(color == Color.RED){
            return "/images/worker/redWorker.png";
        }
        else if(color == Color.BLUE){
            return "/images/worker/blueWorker.png";
        }
        else if(color == Color.GREEN){
            return "/images/worker/greenWorker.png";
        }
        return null;
    }



    public void placeWorker(){
        Platform.runLater(() -> {
            actionPane.setVisible(true);
            actionGrid.setVisible(true);
        });
    }



    public void updateBoard(Board board){
        Platform.runLater(()->{
            for(int x = 0; x <5 ; x++){
                for(int y = 0; y<5; y++){
                    if(board.hasWorkerOnTop(new Point(x,y))){
                        ImageView image = new ImageView();
                        image.setImage(new Image(getClass().getResource(chooseWorker(board.getWorker(new Point(x,y)).getColor())).toString()));
                        image.setFitWidth(imageGrid.getPrefWidth()/5);
                        image.setFitHeight(imageGrid.getPrefHeight()/5);
                        imageGrid.add(image,y,x);
                    }
                }
            }
        });
    }



    public String formatString(String message){
        return message.replaceAll("\n", "").replaceAll("\\[31m","").replaceAll("\\[32m","").replaceAll("\\[33m","").replaceAll("\\[34m","").replaceAll("\\[35m","").replaceAll("\\[0m","").replaceAll(">>>","");
    }


    @Override
    public void changeStage() {

    }


    @Override
    public void handleMessage(Message message) {
        if(message instanceof PlaceWorkerRequest){
            placeWorker();
        }
        else if(message instanceof BoardUpdate){
            updateBoard(((BoardUpdate) message).getBoard());
        }
        else if(message instanceof EndTurnMessage){
            //TODO view per End Turn
        }
        else if(message instanceof InvalidWorkerPosition){
            serverLog.appendText("[SERVER]: " + formatString(message.getMessage()) +"\n");
            placeWorker();
        }
        else if(message instanceof SimpleMessage){
            serverLog.appendText("[SERVER]: " + formatString(message.getMessage()) +"\n");
        }
    }
}
