package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
       //BorderPane root = FXMLLoader.load(getClass().getResource("../TeacherStage/main.fxml"));
      //  Parent root = FXMLLoader.load(getClass().getResource("../homeChart/Home.fxml"));

      // Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
       // VBox root = FXMLLoader.load(getClass().getResource("../Student/StudentMain.fxml"));
        AnchorPane root = FXMLLoader.load(getClass().getResource("../login/Login.fxml"));


        primaryStage.setTitle("منظومة إدارة مدرسة");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
