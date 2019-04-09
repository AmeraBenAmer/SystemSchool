package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.DBConnect;
import sample.Messages;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerLogin  implements Initializable {

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private Circle cir1;

    @FXML
    private Circle cir2;

    @FXML
    private Circle cir3;
    @FXML
    private JFXPasswordField passwordField;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;






    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loginBtn.setOnMouseClicked(event -> {
            animate();
            login();
        } );

    }

    public void login() {



        conn = DBConnect.setConnection();
        int type=1;
        boolean done = false;
        boolean log = false;


        String sql ="select * from users where type="+type+" and username=? and password=? ";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1,usernameField.getText() );
            pst.setString(2, passwordField.getText());
            String username = usernameField.getText();
            Session.setUsername(username);
            System.out.println(username);
            animate();
            rs = pst.executeQuery();
            if(rs.next())
            {
                Stage currentStage = (Stage)usernameField.getScene().getWindow();
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("../sample/main_page.fxml"));
                primaryStage.setTitle("منظومة إدارة مدرسة");
                primaryStage.setMaximized(true);
                primaryStage.setScene(new Scene(root));
                currentStage.close();
                primaryStage.show();
                log = true;
                done = true;

            }

            else
            {

                sql ="select * from users where type="+2+" and username=? and password=? ";

                pst = conn.prepareStatement(sql);
                pst.setString(1,usernameField.getText() );
                pst.setString(2, passwordField.getText());
                String username2 = usernameField.getText();
                Session.setUsername(username2);
                System.out.println(username2);
                animate();
                rs = pst.executeQuery();
                if(rs.next())
                {
                    Stage currentStage = (Stage)usernameField.getScene().getWindow();
                    Stage primaryStage = new Stage();


                    Parent root = FXMLLoader.load(getClass().getResource("../TeacherStage/main.fxml"));
                    primaryStage.setTitle("منظومة إدارة مدرسة");
                    primaryStage.setMaximized(true);
                    primaryStage.setScene(new Scene(root));
                    currentStage.close();
                    primaryStage.show();
                    log = true;
                    done = true;
                }
                else {
                    Messages.viewErrorMessage("يرجي تأكد من كلمة المرور او اسم المستخدم");
                }
            }




            pst.close();
            rs.close();

            conn.close();
        }
        catch (HeadlessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void animate() {
        Timeline t = new Timeline();

        KeyValue kv = new KeyValue(cir1.translateYProperty(),-30);

        KeyFrame kf1 = new KeyFrame(Duration.seconds(0.6),kv);

        KeyValue kv2 = new KeyValue(cir2.translateYProperty(),-30);

        KeyFrame kf2 = new KeyFrame(Duration.seconds(0.4),kv2);

        KeyValue kv3 = new KeyValue(cir3.translateYProperty(),-30);

        KeyFrame kf3 = new KeyFrame(Duration.seconds(0.2),kv3);
        t.getKeyFrames().addAll(kf1,kf2,kf3);

        t.setCycleCount(10);
        t.isAutoReverse();

        t.play();
    }


}

