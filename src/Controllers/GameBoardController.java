package Controllers;

import GameFiles.Board;
import GameFiles.Cell;
import Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;


public class GameBoardController {

    // DECLARATIONS //

    private boolean running = false;
    private Board enemyBoard, playerBoard;

    private int shipsPlaced = 0;

    private boolean enemyTurn = true;

    private Random random = new Random();

    private int[] length = new int[5];
    private int[] hitScores = new int[5];
    private int[] sinkScores = new int[5];
    private String[] names = new String[5];



    // FIRST TURN BOOLEAN  //

    private boolean enemyTurn() {

        Random randomSTART = new Random();
        return randomSTART.nextBoolean();
    }


    // GAME //

    private Parent createContent() {

        // SHIP VARIABLES //

        length[0] = 5;
        length[1] = 4;
        length[2] = 3;
        length[3] = 3;
        length[4] = 2;

        hitScores[0] = 350;
        hitScores[1] = 250;
        hitScores[2] = 100;
        hitScores[3] = 100;
        hitScores[4] = 50;

        sinkScores[0] = 1000;
        sinkScores[1] = 500;
        sinkScores[2] = 250;
        sinkScores[3] = 0;
        sinkScores[4] = 0;

        names[0] = "Carrier";
        names[1] = "Battleship";
        names[2] = "Cruiser";
        names[3] = "Submarine";
        names[4] = "Destroyer";

        // MAIN WINDOW //

        BorderPane root = new BorderPane();
        root.setPrefSize(1480, 920);

        // TOP TOOLBAR //

        ToolBar toolbar = new ToolBar();
        root.setTop(toolbar);

        // SET TOOLBAR FIELDS //

        Text txt1 = new Text();
        TextField txt2 = new TextField();
        Text txt3 = new Text();
        TextField txt4 = new TextField();
        Text txt5 = new Text();
        TextField txt6 = new TextField();
        Text txt7 = new Text();
        TextField txt8 = new TextField();
        Text txt9 = new Text();
        TextField txt10 = new TextField();
        Text txt11 = new Text();
        TextField txt12 = new TextField();

        txt1.setText("Player Ships");
        txt2.setText("N/A");
        txt2.setDisable(true);
        txt2.setStyle("-fx-opacity: 1;");
        txt3.setText("Enemy Ships");
        txt4.setText("N/A");
        txt4.setDisable(true);
        txt4.setStyle("-fx-opacity: 1;");
        txt5.setText("Player Points");
        txt6.setText("N/A");
        txt6.setDisable(true);
        txt6.setStyle("-fx-opacity: 1;");
        txt7.setText("Enemy Points");
        txt8.setText("N/A");
        txt8.setDisable(true);
        txt8.setStyle("-fx-opacity: 1;");
        txt9.setText("Player Percentage");
        txt10.setText("N/A");
        txt10.setDisable(true);
        txt10.setStyle("-fx-opacity: 1;");
        txt11.setText("Enemy Percentage");
        txt12.setText("N/A");
        txt12.setDisable(true);
        txt12.setStyle("-fx-opacity: 1;");

        Separator separator = new Separator();
        Separator separator2 = new Separator();

        // POPULATE TOOLBAR //

        toolbar.getItems().addAll(txt1,txt2,txt3,txt4,separator,txt5,txt6,txt7,txt8,separator2,txt9,txt10,txt11,txt12);

        // RIGHT SIDE //

        Text TotalShips = new Text("Your fleet:");
        TotalShips.setStyle("-fx-font-weight: bold");

        Text carrierTotal = new Text("Carriers available:");
        Text battleshipTotal = new Text("Battleships available:");
        Text cruiserTotal = new Text("Cruisers available:");
        Text submarineTotal = new Text("Submarines available:");
        Text destroyerTotal = new Text("Destroyers available:");
        TextField carrier = new TextField("1");
        carrier.setDisable(true);
        carrier.setStyle("-fx-opacity: 1;");
        TextField battleship = new TextField("1");
        battleship.setDisable(true);
        battleship.setStyle("-fx-opacity: 1;");
        TextField cruiser = new TextField("1");
        cruiser.setDisable(true);
        cruiser.setStyle("-fx-opacity: 1;");
        TextField submarine = new TextField("1");
        submarine.setDisable(true);
        submarine.setStyle("-fx-opacity: 1;");
        TextField destroyer = new TextField("1");
        destroyer.setDisable(true);
        destroyer.setStyle("-fx-opacity: 1;");
        Text play = new Text("");

        VBox rightvbox = new VBox(10,TotalShips,carrierTotal, carrier, battleshipTotal, battleship, cruiserTotal, cruiser, submarineTotal, submarine, destroyerTotal, destroyer, play);
        rightvbox.setAlignment(Pos.BASELINE_LEFT);
        rightvbox.setStyle("-fx-padding: 16;" + "-fx-border-color: black;");
        root.setRight(rightvbox);

        // LEFT SIDE //



        // ENEMY BOARD //

        enemyBoard = new Board(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !cell.shoot();

            if (enemyBoard.ships == 0) {
                System.out.println("YOU WIN");
                infoBox("You won!", null, "What a victory!");
                running = false;
            }

            if (enemyTurn)
                enemyMove();
        });

        // PLAYER BOARD //

        playerBoard = new Board(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            if(shipsPlaced==0) {
                if (playerBoard.placeShip(new Ship(length[shipsPlaced], event.getButton() == MouseButton.PRIMARY, hitScores[shipsPlaced], sinkScores[shipsPlaced] , names[shipsPlaced]), cell.x, cell.y)) {
                    ++shipsPlaced;
                    carrier.setText("0");
                }
            }
                if(shipsPlaced==1){
                    if(playerBoard.placeShip(new Ship(length[shipsPlaced], event.getButton() == MouseButton.PRIMARY, hitScores[shipsPlaced-1], sinkScores[shipsPlaced] , names[shipsPlaced]), cell.x, cell.y)) {
                        battleship.setText("0");
                        ++shipsPlaced;
                    }
                }
                if(shipsPlaced==2) {
                    if (playerBoard.placeShip(new Ship(length[shipsPlaced], event.getButton() == MouseButton.PRIMARY, hitScores[shipsPlaced], sinkScores[shipsPlaced] , names[shipsPlaced]), cell.x, cell.y)) {
                        cruiser.setText("0");
                        ++shipsPlaced;
                    }
                }
                if(shipsPlaced==3) {
                    if (playerBoard.placeShip(new Ship(length[shipsPlaced], event.getButton() == MouseButton.PRIMARY, hitScores[shipsPlaced], sinkScores[shipsPlaced] , names[shipsPlaced]), cell.x, cell.y)) {
                        submarine.setText("0");
                        ++shipsPlaced;
                    }
                }
                if(shipsPlaced==4) {
                    if (playerBoard.placeShip(new Ship(length[shipsPlaced], event.getButton() == MouseButton.PRIMARY, hitScores[shipsPlaced], sinkScores[shipsPlaced] , names[shipsPlaced]), cell.x, cell.y)) {
                        destroyer.setText("0");
                        ++shipsPlaced;
                    }
                }
                if (shipsPlaced == 5) {
                    play.setText("All set! Fire at Will!");
                    startGame();
                }

        });

        // MAIN BOARD //

        Text txtME= new Text();
        Text txtENEMY = new Text();
        txtME.setText("My Board");
        txtENEMY.setText("Enemy Board");

        VBox vbox = new VBox(40,txtENEMY, enemyBoard, txtME ,playerBoard);
        vbox.setAlignment(Pos.CENTER);

        root.setCenter(vbox);

        return root;
    }

    // ENEMY PLAYSTYLE //

    private void enemyMove() {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot)
                continue;

            enemyTurn = cell.shoot();

            if (playerBoard.ships == 0) {
                System.out.println("YOU LOSE");
                infoBox("You lost!", null, "Bad Luck!");
                running = false;
            }
        }
    }

    // PLACE ENEMY SHIPS. THEN GAME STARTS //

    private void startGame() {
        // place enemy ships
        int count = 0;

        while (count <= 4) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.placeShip(new Ship(length[count], Math.random() < 0.5,hitScores[count], sinkScores[count], names[count]), x, y)) {
                count++;
            }
        }

        if (enemyTurn()) {
            infoBox("You go second!", null, "Unlucky, sir!");
            enemyMove();
        }
        else {
            infoBox("You go first!", null, "We got the upper hand!");
        }

        running = true;
    }

    // PLAY BUTTON //

    @FXML
    private void GameScene(ActionEvent event) throws IOException {
        Scene scene2 = new Scene(createContent());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }

    // INFO BOX POPUP //

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

}