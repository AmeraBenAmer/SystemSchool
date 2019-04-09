package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import login.Session;
import sun.plugin.javascript.navig.Anchor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public BorderPane mainLayout;
    private static BorderPane root;

    @FXML
    private Label time_lb;

    @FXML
    private Label date_lb;


    @FXML
    private JFXTextField sissionUser;

    @FXML
    private JFXButton main_btn;

    @FXML
    private JFXButton users_btn;

    @FXML
    private JFXButton teacher_btn;

    @FXML
    private JFXButton student_btn;

    @FXML
    private JFXButton subject_btn;

    @FXML
    private JFXButton degree_btn;

    public static BorderPane getRoot(){
        return root;
    }
    @FXML
    private JFXButton logout_btn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root = mainLayout;
        AnchorPane("Char_main.fxml","الرئسية", main_btn);

        String nameOfUser = Session.getUsername();
        System.out.println(nameOfUser);
        sissionUser.setText(nameOfUser);
        sissionUser.setEditable(false);



        logout_btn.setOnAction(e -> {
            try {
                Stage currentStage = (Stage)main_btn.getScene().getWindow();
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("../login/Login.fxml"));
                primaryStage.setTitle("منظومة إدارة مدرسة");
                primaryStage.setScene(new Scene(root));
                currentStage.close();
                primaryStage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        main_btn.setOnAction(e -> AnchorPane("Char_main.fxml","الرئسية",main_btn));
      //  main_btn.setOnAction(e -> AnchorPane("../sample/Char_m.fxml","الرئسية",main_btn));


        users_btn.setOnAction(e -> AnchorPane("../users/users.fxml","إدارة المستخدمين", users_btn));

        teacher_btn.setOnAction(e -> borderLayot("../Teacher/AddTeacher.fxml","إدارة الفصول", teacher_btn));
        student_btn.setOnAction(e -> vIntent("../students/StudentMain.fxml","إدارة المواد", student_btn));

        degree_btn.setOnAction(e -> vIntent("../Degree/searchDegree.fxml","الاختبارات", degree_btn));


    }
    private void AnchorPane(String path, String title, JFXButton thisButton){
        try {
            AnchorPane box = FXMLLoader.load(getClass().getResource(path));
            mainLayout.setCenter(box);
          activeButton(thisButton);
          //  title_label.setText(title);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void borderLayot(String path, String title, JFXButton thisButton){
        try {
            BorderPane box = FXMLLoader.load(getClass().getResource(path));
            mainLayout.setCenter(box);
            activeButton(thisButton);
            //  title_label.setText(title);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // this is for vBox
    private void vIntent(String path, String title, JFXButton thisButton){
        try {
            VBox box = FXMLLoader.load(getClass().getResource(path));
            mainLayout.setCenter(box);
            activeButton(thisButton);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void activeButton(JFXButton thisButton){

        main_btn.setStyle("-fx-background-color: transparent");
        student_btn.setStyle("-fx-background-color: transparent");
        degree_btn.setStyle("-fx-background-color: transparent");
        teacher_btn.setStyle("-fx-background-color: transparent");
        users_btn.setStyle("-fx-background-color: transparent");
        thisButton.setStyle("-fx-background-color: #7791ab");
    }

}

