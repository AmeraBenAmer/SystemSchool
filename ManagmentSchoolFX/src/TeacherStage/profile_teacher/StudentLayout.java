package TeacherStage.profile_teacher;

import Teacher.ControlTeacher;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sample.DBConnect;
import sample.Messages;
import users.Users;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.geometry.Side.LEFT;

public class StudentLayout implements Initializable {
    @FXML
    private ImageView student_photo;

    @FXML
    private JFXButton delete_photo;

    @FXML
    private JFXButton add_photo;

    @FXML
    private TextField full_name;

    @FXML
    private DatePicker birthday;

    @FXML
    private TextField from;

    @FXML
    private TextField nat_num;

    @FXML
    private DatePicker add_date;

    @FXML
    private TextField address;

    @FXML
    private TextField speacly;

    @FXML
    private TextField phone;

    @FXML
    private JFXButton save;
    @FXML
    private TextField id_field;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setUpField();
        id_field.setDisable(false);

        save.setOnMouseClicked(event ->{
            updateUser();
            Messages.Notification("تعديل البيانات","تمت عملية التعديل بنجاح");

        });





    }

    public void setUpField(){

        String nameOfUser = login.Session.getUsername();
        System.out.println(nameOfUser);

        Connection conn = DBConnect.setConnection();
        try {
            String query = "select * from teachars where state = 1 and username=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, nameOfUser);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id_field.setText(String.valueOf(rs.getInt("id_teachar")));
                full_name.setText(rs.getString("teachar_name"));
                birthday.getEditor().setText(rs.getString("brithdate"));
                nat_num.setText(rs.getString("nat_number"));
                from.setText(rs.getString("specialy"));
                address.setText(rs.getString("address"));
                add_date.getEditor().setText(rs.getString("date_SWork"));
                speacly.setText(rs.getString("work_Now"));
                phone.setText(rs.getString("phone"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateUser() {
        Connection conn = DBConnect.setConnection();
        int id = Integer.parseInt(id_field.getText());
        id_field.setVisible(false);
        System.out.println("id select =="+id);


        try {


            String sql = "UPDATE `teachars` SET "
                    + "teachar_name=?,"
                    + "`nat_number`=?,"
                    + "`brithdate`=? ,"
                    + "`address`=?, "
                    + "`date_SWork`=? ,"
                    + "`work_Now`=?,"
                    + "`specialy`=?,"
                    + "`phone`=?,"
                    + "`state`= 1"
                    + " WHERE id_teachar=" + id;

            PreparedStatement pst = conn.prepareStatement(sql);

            //pst.setInt(1,Integer.parseInt( id_field.getText()));
            pst.setString(1,  full_name.getText());
            pst.setString(2, nat_num.getText());
            pst.setString(3, birthday.getEditor().getText());
            pst.setString(4,address.getText());
            pst.setString(5,  add_date.getEditor().getText());
            pst.setString(6,speacly.getText());
            pst.setString(7,from.getText());
            pst.setInt(8,  Integer.parseInt(phone.getText()));
            pst.executeUpdate();
            setUpField();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(ControlTeacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
