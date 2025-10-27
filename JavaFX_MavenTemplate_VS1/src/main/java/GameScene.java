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

public class GameScene {
    Scene scene;

    // Game state
    private BetCard BETCARD;
    private ToggleButton[][] betCardButtons;
    private Text statText;
    private StackPane bottomStackPane;
    private BorderPane root;

    // Enhanced visual styles with smaller buttons
    private final String DEFAULT_BUTTON_STYLE =
            "-fx-font-size: 12; -fx-font-weight: bold; -fx-background-radius: 3; " +
                    "-fx-border-radius: 3; -fx-border-width: 1; -fx-border-color: #cccccc; " +
                    "-fx-background-color: #f0f0f0; -fx-text-fill: #333333;";

    private final String SELECTED_BUTTON_STYLE =
            "-fx-font-size: 12; -fx-font-weight: bold; -fx-background-radius: 3; " +
                    "-fx-border-radius: 3; -fx-border-width: 2; -fx-border-color: #0066cc; " +
                    "-fx-background-color: #4d94ff; -fx-text-fill: white;";

    private final String ACTION_BUTTON_STYLE =
            "-fx-font-size: 12; -fx-font-weight: bold; -fx-background-radius: 8; " +
                    "-fx-background-color: #3498db; -fx-text-fill: white; -fx-pref-width: 100;";

    private final String SUCCESS_BUTTON_STYLE =
            "-fx-font-size: 12; -fx-font-weight: bold; -fx-background-radius: 8; " +
                    "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-pref-width: 100;";

    // Arrays to store buttons for theme switching
    private Button[] SOBUTTONSARRAY = new Button[4];
    private Button[] STBUTTONSARRAY = new Button[4];
    private boolean darkMode = false;

    public GameScene(Stage primaryStage) {
        initializeScene(primaryStage);
    }

    private void initializeScene(Stage primaryStage) {
        BETCARD = new BetCard();
        root = new BorderPane();
        scene = new Scene(root, 900, 650); // Smaller window
        applyDefaultStyle();

        // Create enhanced menu bar
        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        // Enhanced Status Panel - smaller
        Rectangle statsPane = new Rectangle(140, 140);
        statsPane.setFill(Color.LIGHTGRAY);
        statsPane.setStroke(Color.DARKGRAY);
        statsPane.setStrokeWidth(2);
        statsPane.setArcWidth(15);
        statsPane.setArcHeight(15);

        statText = new Text(BETCARD.displayClear());
        statText.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-text-alignment: center;");
        StackPane statsHolder = new StackPane();
        statsHolder.getChildren().addAll(statsPane, statText);
        statsHolder.setPadding(new Insets(15));

        // Enhanced Bet Card Grid - smaller and tighter
        betCardButtons = new ToggleButton[8][10];
        GridPane betCard = createBetCardGrid();

        // Enhanced Bottom Section - smaller
        bottomStackPane = createBottomSection();

        // Enhanced Center Layout - tighter spacing
        HBox centerSection = new HBox(20, statsHolder, betCard);
        centerSection.setAlignment(Pos.CENTER);
        centerSection.setPadding(new Insets(15));

        VBox mainContent = new VBox(20, centerSection, bottomStackPane);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setPadding(new Insets(15));

        root.setCenter(mainContent);
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
        exitItem.setOnAction(e -> System.exit(0));

        menu.getItems().addAll(rulesItem, oddsItem, newLookItem, exitItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    private GridPane createBetCardGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(4);  // Tighter horizontal gap
        grid.setVgap(4);  // Tighter vertical gap
        grid.setPadding(new Insets(10));
        grid.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-border-radius: 8;");

        int number = 1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                ToggleButton button = new ToggleButton(String.valueOf(number));
                button.setMinSize(45, 40);  // Smaller buttons
                button.setStyle(DEFAULT_BUTTON_STYLE);
                button.setDisable(true);
                betCardButtons[row][col] = button;

                final int currentNumber = number;
                button.setOnAction(e -> {
                    BETCARD.selectNumber(currentNumber);
                    boolean isSelected = BETCARD.getSelectedSpots().contains(currentNumber);
                    button.setSelected(isSelected);

                    // Enhanced visual feedback
                    if (isSelected) {
                        button.setStyle(SELECTED_BUTTON_STYLE);
                    } else {
                        button.setStyle(DEFAULT_BUTTON_STYLE);
                    }

                    statText.setText(BETCARD.displayStats());
                    updateBeginButtonState();
                });

                grid.add(button, col, row);
                number++;
            }
        }
        return grid;
    }

    private void updateBeginButtonState() {
        // Find and update the begin button state
        if (bottomStackPane.getChildren().size() > 1) {
            VBox currentSetup = (VBox) bottomStackPane.getChildren().get(1);
            for (var node : currentSetup.getChildren()) {
                if (node instanceof Button) {
                    Button btn = (Button) node;
                    if (btn.getText().equals("BEGIN DRAWING")) {
                        boolean canBegin = BETCARD.ableToBegin();
                        btn.setDisable(!canBegin);
                        if (canBegin) {
                            btn.setStyle(SUCCESS_BUTTON_STYLE);
                        } else {
                            btn.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-background-color: #cccccc; -fx-text-fill: #666666; -fx-pref-width: 100;");
                        }
                    }
                }
            }
        }
    }

    private StackPane createBottomSection() {
        Rectangle bottomPane = new Rectangle(600, 150);  // Smaller bottom section
        bottomPane.setFill(Color.LIGHTGRAY);
        bottomPane.setStroke(Color.DARKGRAY);
        bottomPane.setStrokeWidth(1);
        bottomPane.setArcWidth(15);
        bottomPane.setArcHeight(15);

        StackPane stackPane = new StackPane();

        // Enhanced Setup One
        VBox setupOne = createSetupOne();

        stackPane.getChildren().addAll(bottomPane, setupOne);
        stackPane.setPadding(new Insets(15));

        return stackPane;
    }

    private VBox createSetupOne() {
        Text SOText = new Text("HOW MANY SPOTS WILL YOU PLAY?");
        SOText.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-fill: #2c3e50;");

        HBox SOBUTTONS = new HBox(15);  // Tighter spacing
        SOBUTTONS.setAlignment(Pos.CENTER);

        int[] SOVALS = {1, 4, 8, 10};

        for (int i = 0; i < 4; i++) {
            final int actVal = SOVALS[i];
            Button button = createStyledButton(String.valueOf(actVal), "#3498db");

            button.setOnAction(e -> {
                BETCARD.setMaxSpots(actVal);
                showSetupTwo();
            });

            SOBUTTONSARRAY[i] = button;
            SOBUTTONS.getChildren().add(button);
        }

        VBox setupOne = new VBox(15, SOText, SOBUTTONS);  // Tighter spacing
        setupOne.setAlignment(Pos.CENTER);
        setupOne.setPadding(new Insets(20));

        return setupOne;
    }

    private VBox createSetupTwo() {
        Text STText = new Text("HOW MANY DRAWINGS WILL YOU PLAY?");
        STText.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-fill: #2c3e50;");

        HBox STBUTTONS = new HBox(15);  // Tighter spacing
        STBUTTONS.setAlignment(Pos.CENTER);

        for (int i = 1; i < 5; i++) {
            final int actVal = i;
            Button button = createStyledButton(String.valueOf(actVal), "#9b59b6");

            button.setOnAction(e -> {
                BETCARD.setMaxDrawing(actVal);
                statText.setText(BETCARD.displayStats());
                showSetupThree();

                // Enable bet card buttons with enhanced visuals
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 10; x++) {
                        betCardButtons[y][x].setDisable(false);
                        betCardButtons[y][x].setStyle(DEFAULT_BUTTON_STYLE);
                    }
                }
            });

            STBUTTONSARRAY[i-1] = button;
            STBUTTONS.getChildren().add(button);
        }

        VBox setupTwo = new VBox(15, STText, STBUTTONS);  // Tighter spacing
        setupTwo.setAlignment(Pos.CENTER);
        setupTwo.setPadding(new Insets(20));

        return setupTwo;
    }

    private VBox createSetupThree() {
        Text SHText = new Text("SELECT YOUR NUMBERS");
        SHText.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-fill: #2c3e50;");

        Text selectionHelp = new Text("Selected numbers will appear in BLUE");
        selectionHelp.setStyle("-fx-font-size: 10; -fx-fill: #3498db; -fx-font-weight: bold;");

        Button quickPickButton = createStyledButton("QUICK PICK", "#e67e22");
        Button beginButton = createStyledButton("BEGIN DRAWING", "#cccccc");
        beginButton.setDisable(true);

        quickPickButton.setOnAction(e -> {
            BETCARD.quickPick();
            updateBetCardVisuals();
            statText.setText(BETCARD.displayStats());
            updateBeginButtonState();
        });

        beginButton.setOnAction(e -> {
            // Disable all bet card buttons during drawing
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 10; x++) {
                    betCardButtons[y][x].setDisable(true);
                }
            }
            startDrawing();
        });

        VBox setupThree = new VBox(10, SHText, selectionHelp, quickPickButton, beginButton);  // Tighter spacing
        setupThree.setAlignment(Pos.CENTER);
        setupThree.setPadding(new Insets(20));

        return setupThree;
    }

    private void updateBetCardVisuals() {
        int number = 1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                boolean isSelected = BETCARD.getSelectedSpots().contains(number);
                betCardButtons[row][col].setSelected(isSelected);
                if (isSelected) {
                    betCardButtons[row][col].setStyle(SELECTED_BUTTON_STYLE);
                } else {
                    betCardButtons[row][col].setStyle(DEFAULT_BUTTON_STYLE);
                }
                number++;
            }
        }
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-pref-width: 80; -fx-pref-height: 35; " +
                "-fx-background-color: " + color + "; -fx-text-fill: white; -fx-background-radius: 8;");

        // Hover effects
        button.setOnMouseEntered(e -> {
            if (!button.isDisabled()) {
                String darkerColor = getDarkerColor(color);
                button.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-pref-width: 80; -fx-pref-height: 35; " +
                        "-fx-background-color: " + darkerColor + "; -fx-text-fill: white; -fx-background-radius: 8;");
            }
        });

        button.setOnMouseExited(e -> {
            if (!button.isDisabled()) {
                button.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-pref-width: 80; -fx-pref-height: 35; " +
                        "-fx-background-color: " + color + "; -fx-text-fill: white; -fx-background-radius: 8;");
            }
        });

        return button;
    }

    private String getDarkerColor(String color) {
        switch(color) {
            case "#3498db": return "#2980b9";
            case "#9b59b6": return "#8e44ad";
            case "#e67e22": return "#d35400";
            case "#2ecc71": return "#27ae60";
            case "#cccccc": return "#aaaaaa";
            default: return color;
        }
    }

    private void showSetupTwo() {
        VBox setupTwo = createSetupTwo();
        bottomStackPane.getChildren().set(1, setupTwo);
    }

    private void showSetupThree() {
        VBox setupThree = createSetupThree();
        bottomStackPane.getChildren().set(1, setupThree);
    }

    private void startDrawing() {
        // Create drawing results display
        Text DT = new Text(BETCARD.displayDrawingStats());
        DT.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-fill: #2c3e50;");

        Button nextDrawingButton = createStyledButton("NEXT DRAWING", "#3498db");
        nextDrawingButton.setDisable(true);

        Button playAgainButton = createStyledButton("PLAY AGAIN", "#2ecc71");
        playAgainButton.setDisable(true);

        Button exitButton = createStyledButton("EXIT TO WELCOME", "#e74c3c");

        VBox drawingControls = new VBox(10, DT);  // Tighter spacing
        drawingControls.setAlignment(Pos.CENTER);

        // Start the drawing using partner's logic - this preserves total winnings
        if (BETCARD.maxDrawing > 1 && BETCARD.curDrawing < BETCARD.maxDrawing) {
            drawingControls.getChildren().add(nextDrawingButton);
            BETCARD.beginDrawing(betCardButtons, nextDrawingButton, statText);
        } else {
            drawingControls.getChildren().addAll(playAgainButton, exitButton);
            BETCARD.beginDrawing(betCardButtons, playAgainButton, exitButton, statText);
        }

        nextDrawingButton.setOnAction(e -> {
            BETCARD.curDrawing++;
            // Clear previous drawing highlights before starting new drawing
            clearDrawingHighlights();
            startDrawing();
        });

        playAgainButton.setOnAction(e -> {
            // Reset for new game but keep total winnings!
            resetGameForNewPlay();
        });

        exitButton.setOnAction(e -> {
            // Return to welcome scene
            WelcomeScene welcomeScene = new WelcomeScene(new Stage());
            welcomeScene.display();
            ((Stage) scene.getWindow()).close();
        });

        bottomStackPane.getChildren().set(1, drawingControls);
    }

    private void clearDrawingHighlights() {
        // Clear any drawing-related highlights while keeping user selections
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                ToggleButton button = betCardButtons[row][col];
                if (button.isSelected()) {
                    button.setStyle(SELECTED_BUTTON_STYLE);
                } else {
                    button.setStyle(DEFAULT_BUTTON_STYLE);
                }
            }
        }
    }

    private void resetGameForNewPlay() {
        // Clear the selected spots in BETCARD
        BETCARD.clearSelectedSpots();

        // Reset the game state but preserve total winnings
        BETCARD.resetGame();

        // Clear all button selections and styles
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                betCardButtons[row][col].setSelected(false);
                betCardButtons[row][col].setStyle(DEFAULT_BUTTON_STYLE);
                betCardButtons[row][col].setDisable(true);
            }
        }

        // Reset the stat text
        statText.setText(BETCARD.displayClear());

        // Show setup one again
        VBox setupOne = createSetupOne();
        bottomStackPane.getChildren().set(1, setupOne);
    }

    private void applyDefaultStyle() {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ecf0f1, #bdc3c7);");
        // Reset all text colors
        for (javafx.scene.Node node : root.getChildrenUnmodifiable()) {
            if (node instanceof Text) {
                ((Text) node).setFill(Color.BLACK);
            }
        }
    }

    private void applyNewLookStyle() {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e);");
        // Update all text colors for dark mode
        for (javafx.scene.Node node : root.getChildrenUnmodifiable()) {
            if (node instanceof Text) {
                ((Text) node).setFill(Color.WHITE);
            }
        }

        // Update button styles for dark mode
        updateButtonStylesForDarkMode();
    }

    private void updateButtonStylesForDarkMode() {
        String darkButtonStyle = "-fx-font-size: 12; -fx-font-weight: bold; -fx-background-color: #8481AB; -fx-text-fill: white;";

        // Update setup one buttons
        for (Button button : SOBUTTONSARRAY) {
            if (button != null) {
                button.setStyle(darkButtonStyle + " -fx-pref-width: 80; -fx-pref-height: 35; -fx-background-radius: 8;");
            }
        }

        // Update setup two buttons
        for (Button button : STBUTTONSARRAY) {
            if (button != null) {
                button.setStyle(darkButtonStyle + " -fx-pref-width: 80; -fx-pref-height: 35; -fx-background-radius: 8;");
            }
        }
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

    public Scene getScene() {
        return scene;
    }

    public void display(Stage primaryStage) {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Keno - Game Play");
        primaryStage.show();
    }
}
