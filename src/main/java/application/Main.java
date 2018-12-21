package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main extends Application {
    public static Stage pStage;

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        pStage = primaryStage;
        pStage.setTitle("DKP ヾﾉ≧∀≦ﾉヾ by 花花");
        pStage.getIcons().add(new Image(String.valueOf(Main.class.getResource("img/frog.png").toURI())));
        initHomePage();
    }

    /**
     * Initializes the home page
     */
    private void initHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainWindow.fxml"));

            AnchorPane test = loader.load();

            Scene scene = new Scene(test);
            pStage.setScene(scene);

            pStage.setResizable(false);
            pStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
