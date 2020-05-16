package it.polimi.ingsw.PSP11.view.gui.controller;


import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Board;

import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.model.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;

public class GameSceneController extends GUIController {


    @FXML
    private Pane initPane,actionPane,imagePane,heroPowerPane;

    @FXML
    private TextArea serverLog;

    @FXML
    private GridPane imageGrid,actionGrid;

    @FXML
    private Text requestText;

    @FXML
    private Button yesButton,noButton;

    @FXML
    public void initialize(){
        initPane.setVisible(true);
        imagePane.setVisible(true);
        heroPowerPane.setVisible(false);
        initializeActionGridButtons();
        initializeImageGrid();
        actionPane.setVisible(false);
        serverLog.setWrapText(true);
        serverLog.setEditable(false);
        requestText.setFont(Font.loadFont(getClass().getResource("/font/LillyBelle400.ttf").toString(),13));



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



    @FXML
    public void selectWorker(ActionEvent event){
        Button button = (Button)event.getSource();
        getClient().asyncWrite(new SelectWorkerResponse(Integer.parseInt(button.getId())));
        actionPane.setVisible(false);
        //TODO button.setOnAction(null)
    }

    @FXML
    public void moveWorker(ActionEvent event) {
        Platform.runLater(() -> {
            Button button = (Button) event.getSource();
            String[] cord = button.getId().split(",");
            Point position = new Point(Integer.parseInt(cord[0]), Integer.parseInt(cord[1]));
            getClient().asyncWrite(new MoveResponse(position));
            actionPane.setVisible(false);

        });
    }

    @FXML
    public void buildBlock(ActionEvent event) {
        Platform.runLater(() -> {
            Button button = (Button) event.getSource();
            String[] cord = button.getId().split(",");
            Point position = new Point(Integer.parseInt(cord[0]), Integer.parseInt(cord[1]));
            getClient().asyncWrite(new BuildResponse(position));
            actionPane.setVisible(false);

        });
    }

    @FXML
    public void booleanResponseHandler(ActionEvent event){
        heroPowerPane.setVisible(false);
        String id = ((Button)event.getSource()).getId();
        if(id.equals("yesButton")){
            getClient().asyncWrite(new BooleanResponse(true));
        }
        else{
            getClient().asyncWrite(new BooleanResponse(false));
        }

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

    public void initializeImageGrid(){
        for(int x = 0;x <5;x++){
            for(int y = 0; y<5; y++){
                StackPane stackpane = new StackPane();
                stackpane.setPrefWidth(imageGrid.getPrefWidth()/5);
                stackpane.setPrefHeight(imageGrid.getPrefHeight()/5);
                stackpane.getStylesheets().add(getClass().getResource("/css/gameScene.css").toString());
                stackpane.getStyleClass().add("stack-pane");
                stackpane.setVisible(true);
                imageGrid.add(stackpane,y,x);
            }
        }
    }

    public void clearStackPanes(){
        for(Node node: imageGrid.getChildren()){
            ((StackPane) node).getChildren().clear();
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


    public void setAllInvisible(GridPane pane){
        for(Node n : pane.getChildren()){
            n.setVisible(false);
        }
    }

    public void selectWorkerView(ArrayList<Worker> workers){
        Platform.runLater(()->{
            actionPane.setVisible(true);
            setAllInvisible(actionGrid);
            for(Worker worker : workers){
                Point workerPosition = worker.getPosition();
                Button button = (Button )actionGrid.getChildren().get(workerPosition.x * 5 + workerPosition.y);
                button.setId(String.valueOf(worker.getId()));
                button.setOnAction(this::selectWorker);
                button.setVisible(true);
            }
        });

    }

    public void moveView(ArrayList<Point> possibleMoves){

        Platform.runLater(()->{
            setAllInvisible(actionGrid);
            for(Point position : possibleMoves){
                Button button = (Button )actionGrid.getChildren().get(position.x * 5 + position.y);
                button.getStyleClass().clear();
                button.getStyleClass().add("moveButton");
                button.setId(position.x +","+position.y);
                button.setOnAction(this::moveWorker);
                button.setVisible(true);
            }
            actionPane.setVisible(true);
        });

    }

    public void buildView(ArrayList<Point> possibleBuilds){

        Platform.runLater(()->{
            setAllInvisible(actionGrid);
            for(Point position : possibleBuilds){
                Button button = (Button )actionGrid.getChildren().get(position.x * 5 + position.y);
                button.getStyleClass().clear();
                button.getStyleClass().add("buildButton");
                button.setId(position.x +","+position.y);
                button.setOnAction(this::buildBlock);
                button.setVisible(true);
            }
            actionPane.setVisible(true);
        });

    }

    public void heroRequestView(String message){
        requestText.setText(message);
        heroPowerPane.setVisible(true);
    }




    public void updateBoardView(Board board){
        Platform.runLater( ()->{
            clearStackPanes();
            for(int x = 0; x <5 ; x++){
                for(int y = 0; y<5; y++){
                    Point position = new Point(x,y);
                    StackPane stack = ((StackPane)imageGrid.getChildren().get(x*5+y));
                    //set the blocks
                    if(board.getCurrentLevel(position).ordinal() >= 1) {
                        ImageView baseBlock = new ImageView();
                        baseBlock.setFitWidth(stack.getWidth()*0.9);
                        baseBlock.setFitHeight(stack.getHeight()*0.9);
                        baseBlock.setPreserveRatio(true);
                        baseBlock.setImage(new Image(getClass().getResource("/images/blocks/base_block.png").toString()));

                        stack.getChildren().add(baseBlock);
                        if (board.getCurrentLevel(position).ordinal() >= 2) {
                            ImageView middleBlock = new ImageView();
                            middleBlock.setFitWidth(stack.getWidth()*0.72);
                            middleBlock.setFitHeight(stack.getHeight()*0.72);
                            middleBlock.setStyle("-fx-effect: dropshadow( three-pass-box, black, 5, 0, 0, 0);");
                            middleBlock.setPreserveRatio(true);
                            middleBlock.setImage(new Image(getClass().getResource("/images/blocks/mid_block.png").toString()));
                            stack.getChildren().add(middleBlock);
                            if (board.getCurrentLevel(position).ordinal() == 3) {
                                ImageView topBlock = new ImageView();
                                topBlock.setFitWidth(stack.getWidth()*0.67);
                                topBlock.setFitHeight(stack.getHeight() *0.67);
                                topBlock.setStyle("-fx-effect: dropshadow(three-pass-box, black, 3, 0, 0, 0);");
                                topBlock.setPreserveRatio(true);
                                topBlock.setImage(new Image(getClass().getResource("/images/blocks/top_block.png").toString()));
                                stack.getChildren().add(topBlock);
                            }
                        }
                    }





                    if(board.hasWorkerOnTop(position)){
                        ImageView workerImage = new ImageView();
                        workerImage.setFitWidth(stack.getWidth()*0.9);
                        workerImage.setFitHeight(stack.getHeight()*0.9);
                        workerImage.setSmooth(true);
                        workerImage.setPreserveRatio(true);
                        workerImage.setImage(new Image(getClass().getResource(chooseWorker(board.getWorker(position).getColor())).toString()));
                        stack.getChildren().add(workerImage);
                    }
                    else if(board.hasDomeOnTop(position)){
                        ImageView dome = new ImageView();
                        dome.setFitWidth(stack.getWidth()*0.60);
                        dome.setFitHeight(stack.getHeight()*0.60);
                        dome.setPreserveRatio(true);
                        dome.setImage(new Image(getClass().getResource("/images/blocks/dome.png").toString()));
                        stack.getChildren().add(dome);
                    }

                }
            }
        });
    }



    public String formatString(String message){
        return message.replaceAll("\n", "").replaceAll("\\[31m","").replaceAll("\\[32m","").replaceAll("\\[33m","").replaceAll("\\[34m","").replaceAll("\\[35m","").replaceAll("\\[0m","").replaceAll(">>>","").replaceAll("y / n","");
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
            updateBoardView(((BoardUpdate) message).getBoard());
        }
        else if(message instanceof EndTurnMessage){
            //TODO view per End Turn
        }
        else if (message instanceof SelectWorkerRequest){
            selectWorkerView(((SelectWorkerRequest) message).getAvailableWorkers());
        }
        else if (message instanceof MoveRequest){
            moveView(((MoveRequest) message).getPossibleMoves());
        }
        else if(message instanceof BuildRequest){
            buildView(((BuildRequest) message).getPossibleBuilds());
        }
        else if(message instanceof BuildAgainRequest || message instanceof BuildBeforeMoveRequest || message instanceof MoveAgainRequest|| message instanceof BuildDomeRequest){
            heroRequestView(formatString(message.getMessage()));
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
