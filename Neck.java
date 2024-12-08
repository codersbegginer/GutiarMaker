import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Neck extends Application {
    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.getChildren().add(new javafx.scene.control.Label("Neck Customization"));

        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Neck Customizer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
