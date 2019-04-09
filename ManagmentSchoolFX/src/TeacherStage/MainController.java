package TeacherStage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

        private static BorderPane root;


    @FXML
    private BorderPane mainLayout;

    @FXML
    private JFXButton logout_btn;

    @FXML
    private JFXTextField sissionUser;

    @FXML
    private JFXButton main_btn;

    @FXML
    private JFXButton degree_btn;

    @FXML
    private JFXButton profile_btn;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            String nameOfUser = login.Session.getUsername();
            System.out.println(nameOfUser);
            sissionUser.setText(nameOfUser);
            sissionUser.setEditable(false);

            AnchorPane("char_m.fxml","الرئسية",main_btn);

            root = mainLayout;
            degree_btn.setOnAction(e -> AnchorPane("degreeTeacher/degree.fxml","إدارة الفصول", degree_btn));
            profile_btn.setOnAction(e -> vIntent("profile_teacher/profile.fxml","إدارة المواد", profile_btn));

            main_btn.setOnAction(e -> AnchorPane("char_m.fxml","الرئسية",main_btn));


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

        // this is for vBox
        private void vIntent(String path, String title, JFXButton thisButton){
            try {
                VBox box = FXMLLoader.load(getClass().getResource(path));
                mainLayout.setCenter(box);
                activeButton(thisButton);
                // title_label.setText(title);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        private void activeButton(JFXButton thisButton){
            main_btn.setStyle("-fx-background-color: transparent");
            degree_btn.setStyle("-fx-background-color: transparent");
            thisButton.setStyle("-fx-background-color: #7791ab");
        }

    }



