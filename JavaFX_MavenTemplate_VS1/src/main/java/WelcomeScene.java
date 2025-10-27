import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;

public class WelcomeScene {
    private Stage primaryStage;

    public WelcomeScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void display() {
        primaryStage.setTitle("Keno Lottery Game - Welcome");

        // Create menu bar
        MenuBar menuBar = createMenuBar();

        // Create welcome content
        VBox welcomeContent = createWelcomeContent();

        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(welcomeContent);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        Menu menu = new Menu("Menu");

        // Menu items
        MenuItem rulesItem = new MenuItem("Rules of the Game");
        MenuItem oddsItem = new MenuItem("Odds of Winning");
        MenuItem exitItem = new MenuItem("Exit Game");

        // Set actions for menu items
        rulesItem.setOnAction(e -> showRulesDialog());
        oddsItem.setOnAction(e -> showOddsDialog());
        exitItem.setOnAction(e -> primaryStage.close());

        menu.getItems().addAll(rulesItem, oddsItem, exitItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        return menuBar;
    }

    private VBox createWelcomeContent() {
        Text welcomeTitle = new Text("Welcome to Keno!");
        welcomeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        welcomeTitle.setFill(Color.DARKBLUE);

        Text instructions = new Text("Click the button below to start playing Keno!\n\n" +
                "In this game, you'll choose numbers and try to match them\n" +
                "with randomly drawn numbers to win prizes!");
        instructions.setFont(Font.font("Arial", 16));
        instructions.setTextAlignment(TextAlignment.CENTER);

        Button startButton = new Button("Start Playing Keno");
        startButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");
        startButton.setOnAction(e -> showGamePlayScene());

        // Animation element for visual appeal
        Rectangle animatedRect = new Rectangle(80, 80);
        animatedRect.setArcHeight(20);
        animatedRect.setArcWidth(20);
        animatedRect.setFill(Color.rgb(70, 130, 180));

        RotateTransition rt = new RotateTransition(Duration.millis(3000), animatedRect);
        rt.setByAngle(360);
        rt.setCycleCount(RotateTransition.INDEFINITE);
        rt.setAutoReverse(false);
        rt.play();

        FadeTransition ft = new FadeTransition(Duration.millis(2000), animatedRect);
        ft.setFromValue(0.3);
        ft.setToValue(1.0);
        ft.setCycleCount(FadeTransition.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        VBox animationBox = new VBox(animatedRect);
        animationBox.setAlignment(Pos.CENTER);
        animationBox.setPadding(new Insets(20));

        VBox content = new VBox(20, welcomeTitle, instructions, animationBox, startButton);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));
        content.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f3ff, #ffffff);");

        return content;
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


    private void showGamePlayScene() {
        GameScene gamePlayScene = new GameScene(primaryStage);
        gamePlayScene.display(primaryStage);
    }
}
