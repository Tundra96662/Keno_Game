import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showWelcomeScene();
    }

    private void showWelcomeScene() {
        WelcomeScene welcomeScene = new WelcomeScene(primaryStage);
        welcomeScene.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
