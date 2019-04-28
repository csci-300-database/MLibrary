package csci.database.mlibrary;

import csci.database.mlibrary.controllers.MainController;
import csci.database.mlibrary.database.SQLManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainView.class.getResource("/layouts/main_view.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.show();

        SQLManager manager = new SQLManager();

    }


}
