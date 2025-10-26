import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class GamePlayScene {
    private Scene scene;
    private Stage primaryStage;
    private BetCard betCard;
    private boolean darkMode = false;
    private BorderPane root;
    private StackPane bottomStackPane;
    private ToggleButton[][] betCardButtons;
    private Text statText;

    // Styles for better visual feedback
    private final String DEFAULT_BUTTON_STYLE =
            "-fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 5; " +
                    "-fx-border-radius: 5; -fx-border-width: 2; -fx-border-color: #cccccc; " +
                    "-fx-background-color: #f0f0f0; -fx-text-fill: #333333;";

    private final String SELECTED_BUTTON_STYLE =
            "-fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 5; " +
                    "-fx-border-radius: 5; -fx-border-width: 3; -fx-border-color: #0066cc; " +
                    "-fx-background-color: #4d94ff; -fx-text-fill: white;";

    private final String DISABLED_BUTTON_STYLE =
            "-fx-font-size: 14; -fx-font-weight: bold; -fx-background-radius: 5; " +
                    "-fx-border-radius: 5; -fx-border-width: 2; -fx-border-color: #dddddd; " +
                    "-fx-background-color: #e8e8e8; -fx-text-fill: #999999;";

    public GamePlayScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.betCard = new BetCard();
        initializeScene();
    }

    private void initializeScene() {
        root = new BorderPane();
        scene = new Scene(root, 1000, 800); // Slightly larger for better visibility
        applyDefaultStyle();

        // Create menu bar
        MenuBar menuBar = createMenuBar();

        // Create game content
        setupGameContent();

        root.setTop(menuBar);
    }

    private MenuBar createMenuBar() {
        Menu menu = new Menu("Menu");

        MenuItem rulesItem = new MenuItem("Rules of the Game");
        MenuItem oddsItem = new MenuItem("Odds of Winning");
        MenuItem newLookItem = new MenuItem("New Look");
        MenuItem exitItem = new MenuItem("Exit Game");

        rulesItem.setOnAction(e -> showRulesDialog());
        oddsItem.setOnAction(e -> showOddsDialog());
        newLookItem.setOnAction(e -> toggleNewLook());
        exitItem.setOnAction(e -> primaryStage.close());

        menu.getItems().addAll(rulesItem, oddsItem, newLookItem, exitItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    private void setupGameContent() {
        // Reset bet card for new game by clearing selections manually
        betCard.clearSelectedSpots();
        betCard.totalReward = 0;
        betCard.totalDrawsPlayed = 0;
        betCard.curDrawing = 1;
        betCard.maxDrawing = -1;
        betCard.maxSelectedSpots = -1;
        betCard.correctSpots = 0;
        betCard.winningSpots.clear();

        // Status panel with better styling
        Rectangle statsPane = new Rectangle(180, 180);
        statsPane.setFill(Color.LIGHTGRAY);
        statsPane.setStroke(Color.DARKGRAY);
        statsPane.setStrokeWidth(2);
        statsPane.setArcWidth(20);
        statsPane.setArcHeight(20);

        statText = new Text(betCard.displayClear());
        statText.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-alignment: center;");
        StackPane statsHolder = new StackPane();
        statsHolder.getChildren().addAll(statsPane, statText);
        statsHolder.setPadding(new Insets(25));

        // Bet card grid
        betCardButtons = new ToggleButton[8][10];
        GridPane betCardGrid = createBetCardGrid(betCardButtons, statText);

        // Bottom section
        bottomStackPane = createBottomSection(betCardButtons, statText);

        HBox centerSection = new HBox(40, statsHolder, betCardGrid);
        centerSection.setAlignment(Pos.CENTER);
        centerSection.setPadding(new Insets(25));

        VBox mainContent = new VBox(40, centerSection, bottomStackPane);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setPadding(new Insets(25));

        root.setCenter(mainContent);
    }

    private GridPane createBetCardGrid(ToggleButton[][] buttons, Text statText) {
        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(15));
        grid.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-width: 2; -fx-border-radius: 10;");

        int number = 1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                ToggleButton button = new ToggleButton(String.valueOf(number));
                button.setMinSize(70, 60);
                button.setStyle(DEFAULT_BUTTON_STYLE);
                button.setDisable(true); // Initially disabled until spots are chosen
                buttons[row][col] = button;

                final int currentNumber = number;
                button.setOnAction(e -> {
                    betCard.selectNumber(currentNumber);
                    boolean isSelected = betCard.getSelectedSpots().contains(currentNumber);
                    button.setSelected(isSelected);

                    // Update button appearance based on selection state
                    if (isSelected) {
                        button.setStyle(SELECTED_BUTTON_STYLE);
                    } else {
                        button.setStyle(DEFAULT_BUTTON_STYLE);
                    }

                    statText.setText(betCard.displayStats());

                    // Update selection counter in stats
                    updateSelectionCounter();

                    // Enable begin button if enough spots are selected
                    if (bottomStackPane.getChildren().size() > 1) {
                        VBox currentSetup = (VBox) bottomStackPane.getChildren().get(1);
                        for (var node : currentSetup.getChildren()) {
                            if (node instanceof Button) {
                                Button btn = (Button) node;
                                if (btn.getText().equals("Begin Drawing")) {
                                    btn.setDisable(!betCard.ableToBegin());
                                    if (betCard.ableToBegin()) {
                                        btn.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-pref-width: 150;");
                                    } else {
                                        btn.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-background-color: #cccccc; -fx-text-fill: #666666; -fx-pref-width: 150;");
                                    }
                                }
                            }
                        }
                    }
                });

                grid.add(button, col, row);
                number++;
            }
        }
        return grid;
    }

    private void updateSelectionCounter() {
        String currentStats = betCard.displayStats();
        // Add visual indicator of selection progress
        String enhancedStats = currentStats + "\nSelected: " + betCard.getSelectedSpots().size() + "/" + betCard.maxSelectedSpots;
        statText.setText(enhancedStats);
    }

    private StackPane createBottomSection(ToggleButton[][] buttons, Text statText) {
        Rectangle bottomPane = new Rectangle(800, 220);
        bottomPane.setFill(Color.LIGHTGRAY);
        bottomPane.setStroke(Color.DARKGRAY);
        bottomPane.setStrokeWidth(2);
        bottomPane.setArcWidth(25);
        bottomPane.setArcHeight(25);

        // Setup screens
        VBox setupOne = createSetupOne(buttons);
        VBox setupTwo = createSetupTwo(buttons, statText);
        VBox setupThree = createSetupThree(buttons, statText);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(bottomPane, setupOne);
        stackPane.setPadding(new Insets(25));

        return stackPane;
    }

    private VBox createSetupOne(ToggleButton[][] buttons) {
        Text text = new Text("HOW MANY SPOTS WILL YOU PLAY?");
        text.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #2c3e50;");

        Text subText = new Text("Choose how many numbers you want to play");
        subText.setStyle("-fx-font-size: 14; -fx-fill: #7f8c8d;");

        HBox buttonBox = new HBox(25);
        buttonBox.setAlignment(Pos.CENTER);

        int[] spotValues = {1, 4, 8, 10};
        for (int spots : spotValues) {
            Button button = new Button(String.valueOf(spots));
            button.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-pref-width: 80; -fx-pref-height: 50; " +
                    "-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10;");
            button.setOnAction(e -> {
                betCard.setMaxSpots(spots);
                showSetupTwo(buttons);
            });

            // Add hover effects
            button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-pref-width: 80; -fx-pref-height: 50; " +
                    "-fx-background-color: #2980b9; -fx-text-fill: white; -fx-background-radius: 10;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-pref-width: 80; -fx-pref-height: 50; " +
                    "-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10;"));

            buttonBox.getChildren().add(button);
        }

        VBox setup = new VBox(20, text, subText, buttonBox);
        setup.setAlignment(Pos.CENTER);
        setup.setPadding(new Insets(30));
        return setup;
    }

    private VBox createSetupTwo(ToggleButton[][] buttons, Text statText) {
        Text text = new Text("HOW MANY DRAWINGS WILL YOU PLAY?");
        text.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #2c3e50;");

        Text subText = new Text("Choose how many rounds to play with your selected numbers");
        subText.setStyle("-fx-font-size: 14; -fx-fill: #7f8c8d;");

        HBox buttonBox = new HBox(25);
        buttonBox.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 4; i++) {
            Button button = new Button(String.valueOf(i));
            button.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-pref-width: 80; -fx-pref-height: 50; " +
                    "-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-background-radius: 10;");
            final int drawings = i;
            button.setOnAction(e -> {
                betCard.setMaxDrawing(drawings);
                statText.setText(betCard.displayStats());
                showSetupThree(buttons);

                // Enable all bet card buttons with updated style
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 10; col++) {
                        buttons[row][col].setDisable(false);
                        buttons[row][col].setStyle(DEFAULT_BUTTON_STYLE);
                    }
                }

                // Update selection counter
                updateSelectionCounter();
            });

            // Add hover effects
            button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-pref-width: 80; -fx-pref-height: 50; " +
                    "-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-background-radius: 10;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-pref-width: 80; -fx-pref-height: 50; " +
                    "-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-background-radius: 10;"));

            buttonBox.getChildren().add(button);
        }

        VBox setup = new VBox(20, text, subText, buttonBox);
        setup.setAlignment(Pos.CENTER);
        setup.setPadding(new Insets(30));
        return setup;
    }

    private VBox createSetupThree(ToggleButton[][] buttons, Text statText) {
        Text text = new Text("SELECT YOUR NUMBERS");
        text.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #2c3e50;");

        Text subText = new Text("Click numbers on the grid or use Quick Pick");
        subText.setStyle("-fx-font-size: 14; -fx-fill: #7f8c8d;");

        Text selectionHelp = new Text("Selected numbers will appear in BLUE");
        selectionHelp.setStyle("-fx-font-size: 12; -fx-fill: #3498db; -fx-font-weight: bold;");

        Button quickPickButton = new Button("QUICK PICK");
        quickPickButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-pref-width: 150; -fx-pref-height: 45; " +
                "-fx-background-color: #e67e22; -fx-text-fill: white; -fx-background-radius: 10;");

        Button beginButton = new Button("BEGIN DRAWING");
        beginButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-pref-width: 150; -fx-pref-height: 45; " +
                "-fx-background-color: #cccccc; -fx-text-fill: #666666; -fx-background-radius: 10;");
        beginButton.setDisable(true); // Initially disabled until numbers are selected

        quickPickButton.setOnAction(e -> {
            betCard.quickPick();

            // Update button states on grid with visual feedback
            int number = 1;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 10; col++) {
                    boolean isSelected = betCard.getSelectedSpots().contains(number);
                    buttons[row][col].setSelected(isSelected);
                    if (isSelected) {
                        buttons[row][col].setStyle(SELECTED_BUTTON_STYLE);
                    } else {
                        buttons[row][col].setStyle(DEFAULT_BUTTON_STYLE);
                    }
                    number++;
                }
            }
            statText.setText(betCard.displayStats());
            updateSelectionCounter();
            beginButton.setDisable(!betCard.ableToBegin());
            if (betCard.ableToBegin()) {
                beginButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-pref-width: 150; -fx-pref-height: 45; -fx-background-radius: 10;");
            }
        });

        // Add hover effects for Quick Pick button
        quickPickButton.setOnMouseEntered(e -> quickPickButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-pref-width: 150; -fx-pref-height: 45; " +
                "-fx-background-color: #d35400; -fx-text-fill: white; -fx-background-radius: 10;"));
        quickPickButton.setOnMouseExited(e -> quickPickButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-pref-width: 150; -fx-pref-height: 45; " +
                "-fx-background-color: #e67e22; -fx-text-fill: white; -fx-background-radius: 10;"));

        beginButton.setOnAction(e -> {
            startDrawing(buttons, statText);
        });

        // Add hover effects for Begin button
        beginButton.setOnMouseEntered(e -> {
            if (!beginButton.isDisabled()) {
                beginButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-pref-width: 150; -fx-pref-height: 45; " +
                        "-fx-background-color: #45a049; -fx-text-fill: white; -fx-background-radius: 10;");
            }
        });
        beginButton.setOnMouseExited(e -> {
            if (!beginButton.isDisabled()) {
                beginButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-pref-width: 150; -fx-pref-height: 45; " +
                        "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 10;");
            }
        });

        HBox buttonBox = new HBox(30, quickPickButton, beginButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox setup = new VBox(15, text, subText, selectionHelp, buttonBox);
        setup.setAlignment(Pos.CENTER);
        setup.setPadding(new Insets(30));
        return setup;
    }

    private void showSetupTwo(ToggleButton[][] buttons) {
        VBox setupTwo = createSetupTwo(buttons, statText);
        bottomStackPane.getChildren().set(1, setupTwo);
    }

    private void showSetupThree(ToggleButton[][] buttons) {
        VBox setupThree = createSetupThree(buttons, statText);
        bottomStackPane.getChildren().set(1, setupThree);
    }

    private void startDrawing(ToggleButton[][] buttons, Text statText) {
        // Disable all bet card buttons during drawing
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                buttons[row][col].setDisable(true);
                buttons[row][col].setStyle(DISABLED_BUTTON_STYLE);
            }
        }

        // Create drawing results display
        Text drawingText = new Text("DRAWING IN PROGRESS...\n\nDrawing: " + betCard.curDrawing + " / " + betCard.maxDrawing);
        drawingText.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-fill: #2c3e50; -fx-text-alignment: center;");

        Button nextDrawingButton = new Button("NEXT DRAWING");
        nextDrawingButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-pref-width: 150; -fx-pref-height: 45; " +
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10;");
        nextDrawingButton.setDisable(true);

        Button playAgainButton = new Button("PLAY AGAIN");
        playAgainButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-pref-width: 150; -fx-pref-height: 45; " +
                "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-background-radius: 10;");
        playAgainButton.setDisable(true);

        Button exitButton = new Button("EXIT TO WELCOME");
        exitButton.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-pref-width: 150; -fx-pref-height: 45; " +
                "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 10;");

        VBox drawingControls = new VBox(20, drawingText);
        drawingControls.setAlignment(Pos.CENTER);

        // Start the drawing
        if (betCard.maxDrawing > 1 && betCard.curDrawing < betCard.maxDrawing) {
            drawingControls.getChildren().add(nextDrawingButton);
            betCard.beginDrawing(buttons, nextDrawingButton, statText);
        } else {
            drawingControls.getChildren().addAll(playAgainButton, exitButton);
            betCard.beginDrawing(buttons, playAgainButton, exitButton, statText);
        }

        nextDrawingButton.setOnAction(e -> {
            betCard.curDrawing++;
            startDrawing(buttons, statText);
        });

        playAgainButton.setOnAction(e -> {
            // Reset for new game
            setupGameContent();
        });

        exitButton.setOnAction(e -> {
            showWelcomeScene();
        });

        bottomStackPane.getChildren().set(1, drawingControls);
    }

    private void applyDefaultStyle() {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ecf0f1, #bdc3c7);");
    }

    private void applyNewLookStyle() {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e);");
    }

    private void toggleNewLook() {
        darkMode = !darkMode;
        if (darkMode) {
            applyNewLookStyle();
        } else {
            applyDefaultStyle();
        }
    }

    private void showRulesDialog() {
        String rules = "Keno Game Rules:\n\n" +
                "1. Choose how many numbers (spots) you want to play: 1, 4, 8, or 10\n" +
                "2. Select your numbers from 1 to 80 on the bet card\n" +
                "3. Choose how many drawings to play (1-4)\n" +
                "4. 20 numbers will be drawn randomly with no duplicates\n" +
                "5. Win prizes based on how many of your numbers match the drawn numbers\n" +
                "6. You can play multiple drawings with the same bet card\n\n" +
                "Good luck!";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Keno Rules");
        alert.setHeaderText("How to Play Keno");
        alert.setContentText(rules);
        alert.getDialogPane().setMinHeight(400);
        alert.showAndWait();
    }

    private void showOddsDialog() {
        String odds = "Keno Odds (Based on North Carolina Lottery):\n\n" +
                "SPOT 1:\n" +
                "• Match 1: Win $2 (1 in 4 odds)\n\n" +
                "SPOT 4:\n" +
                "• Match 2: Win $1 (1 in 4.5 odds)\n" +
                "• Match 3: Win $5 (1 in 9.5 odds)\n" +
                "• Match 4: Win $75 (1 in 327 odds)\n\n" +
                "SPOT 8:\n" +
                "• Match 4: Win $2 (1 in 5.1 odds)\n" +
                "• Match 5: Win $12 (1 in 16.6 odds)\n" +
                "• Match 6: Win $50 (1 in 87.5 odds)\n" +
                "• Match 7: Win $750 (1 in 1,221 odds)\n" +
                "• Match 8: Win $10,000 (1 in 23,474 odds)\n\n" +
                "SPOT 10:\n" +
                "• Match 0: Win $5 (1 in 22.1 odds)\n" +
                "• Match 5: Win $2 (1 in 4.7 odds)\n" +
                "• Match 6: Win $15 (1 in 15.3 odds)\n" +
                "• Match 7: Win $100 (1 in 81.7 odds)\n" +
                "• Match 8: Win $1,000 (1 in 867 odds)\n" +
                "• Match 9: Win $5,000 (1 in 21,383 odds)\n" +
                "• Match 10: Win $100,000 (1 in 8,911,711 odds)";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Keno Odds");
        alert.setHeaderText("Payouts and Odds");
        alert.setContentText(odds);
        alert.getDialogPane().setMinHeight(500);
        alert.showAndWait();
    }

    private void showWelcomeScene() {
        WelcomeScene welcomeScene = new WelcomeScene(primaryStage);
        welcomeScene.display();
    }

    public Scene getScene() {
        return scene;
    }

    public void display() {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Keno - Game Play");
        primaryStage.show();
    }
}
