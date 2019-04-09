package users;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import sample.DBConnect;
import sample.Messages;
import students.Student;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlUser implements Initializable {
   private PreparedStatement pst = null;
    private  ResultSet rs = null;
   private FilteredList<Users> filteredData;


    @FXML private JFXRadioButton levelR;
    @FXML private JFXRadioButton userR;
    @FXML private JFXTextField searchField;
    @FXML private ImageView search_field;
    @FXML private TableView<Users> users_table;
    @FXML private JFXTextField id_field;
    @FXML private JFXTextField last_name_field;
    @FXML private DatePicker date_field;
    @FXML private JFXTextField address_field;
    @FXML private JFXTextField level_field;
    @FXML private JFXTextField phone_Field;
    @FXML private JFXTextField username_field;
    @FXML private JFXTextField password_field;
    @FXML private JFXButton deletBtn;
    @FXML private JFXButton addBtn;
    @FXML private JFXButton saveBtn;
    @FXML private ImageView clear_field1;

    /**
     * Initializes the controller class.
     */

    private static Users editable_Student;

  //  ObservableList<Users> list = FXCollections.observableArrayList();


    @Override

    public void initialize(URL url, ResourceBundle rb) {

        setUpTable();
        AutoIncreament();



        users_table.setOnMouseClicked(event ->{
            if(users_table.getSelectionModel().getSelectedIndex() == -1) {
                Messages.viewWaringMessage("يجب عليك تحديد المستخدم اولآ !");
            }else {
                Users s = users_table.getSelectionModel().getSelectedItem();
                setUpFields(s.getId());
                System.out.println("id == "+s.getId());

            }
        });

       searchField.setOnKeyReleased(event -> search_Text());


        clear_field1.setOnMouseClicked(event -> {clearForm();
        id_field.setText(null); AutoIncreament();});

        addBtn.setOnAction(e -> {
            if(id_field.getText().isEmpty() || username_field.getText().isEmpty() || last_name_field.getText().isEmpty()
            || address_field.getText().isEmpty() || date_field.getEditor().getText().isEmpty() || level_field.getText().isEmpty()||
            password_field.getText().isEmpty() || phone_Field.getText().isEmpty() ){

                Messages.viewWaringMessage(" يجب عدم ترك الحقول فارغة !");
                if(!phone_Field.getText().matches("[0-9]{7}+")){
                    Messages.viewWaringMessage ("يجب ان يحتوي رقم الهاتف علي 7 ارقام.") ;
                }
            }else {
                InsertUsers();
                users_table.setItems(getStudentsList("select * from users where state = 1 "));
                AutoIncreament();
                Messages.Notification("اضافة مستخدم","تمت عمليةالاضافة بنجاح");
                clearForm();
            }



        });

        saveBtn.setOnAction(e -> {
            if(users_table.getSelectionModel().getSelectedIndex() == -1){
                Messages.viewWaringMessage("يجب عليك تحديد المستخدم اولآ !");
                return;
            }else {
                updateUser();
                users_table.setItems(getStudentsList("select * from users where state = 1 "));
                clearForm();
                AutoIncreament();
                Messages.Notification("تعديل بيانات مستخدم","تمت عمليةالتعديل بنجاح");
            }
        });

        deletBtn.setOnAction(e -> {
            if(users_table.getSelectionModel().getSelectedIndex() == -1){
                Messages.viewWaringMessage("يجب عليك تحديد المستخدم اولآ !");
                return;
            }
            if(Messages.viewConfirmDialog("حذف المستخدم","هل تريد حذف هذا المستخدم ؟")){
                Users user = users_table.getSelectionModel().getSelectedItem();
                deleteUsers(user.getId());

                Messages.Notification("حذف مستخدم","تمت عمليةالحذف بنجاح");

            }
        });



    }

    private void search_Text() {
        ToggleGroup TBtn = new ToggleGroup();
        userR.setToggleGroup(TBtn);
        levelR.setToggleGroup(TBtn);
        String stSearch = "select * from users where state = 1 and ";
        if (userR.isSelected()) {
            stSearch += " username ";
        } else if(levelR.isSelected()){
            stSearch += " type ";
        }

        stSearch += " like '%" + searchField.getText() + "%'";


        users_table.setItems(getStudentsList(stSearch));


    }

    private void AutoIncreament(){
        Connection conn = DBConnect.setConnection();

        String query ="select max(idusers)+1 as 'columnName' from users ";
        try {
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                String id = rs.getString("columnName");
                id_field.setText(id);
                id_field.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void InsertUsers() {
        Connection conn = DBConnect.setConnection();

            try {
                String query = "INSERT INTO `users`(`idusers`, `username`, `password`, `type`," +
                        " `phone`, `full_name`, `birth_day`, `address`, `state`)" +
                        "VALUES (?,?,?,?,?,?,?,?,'1')";

                pst = conn.prepareStatement(query);
                pst.setInt(1, Integer.parseInt(id_field.getText()));
                pst.setString(2, username_field.getText());
                pst.setString(3, password_field.getText());
                pst.setString(4, level_field.getText());
                pst.setInt(5, Integer.parseInt(phone_Field.getText()));
                pst.setString(6, last_name_field.getText());
                pst.setString(7, date_field.getEditor().getText());
                pst.setString(8, address_field.getText());

                pst.executeUpdate();
                pst.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    private void deleteUsers(int id) {
        Connection conn = DBConnect.setConnection();

        try {
            String query = "Update users SET state =0 WHERE idusers = ? ;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            ps.execute();

            ps.close();
            conn.close();
            String sql = "select * from users where state = 1 ";
            users_table.setItems(getStudentsList(sql));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setEditableStudent(Users users) {
        ControlUser.editable_Student = users;
    }


    private void setUpTable() {

        TableColumn<Users, String> idCol = new TableColumn<>("رقم المستخدم");
        TableColumn<Users, String> usernameCol = new TableColumn<>("اسم المستخدم");
        TableColumn<Users, String> passwordCol = new TableColumn<>("كلمة المرور");
        TableColumn<Users, String> levelCol = new TableColumn<>("مستوي الوصول");
        TableColumn<Users, String> phoneCol = new TableColumn<>("رقم الهاتف");
        TableColumn<Users, String> nameCol = new TableColumn<>("اسم المستخدم الثلاثي");
        TableColumn<Users, String> birthDayCol = new TableColumn<>("يوم الميلاد");
        TableColumn<Users, String> addressCol = new TableColumn<>("العنوان");

        idCol.isResizable();
        nameCol.isResizable();
        birthDayCol.isResizable();
        addressCol.isResizable();
        levelCol.isResizable();
        phoneCol.isResizable();
        usernameCol.isResizable();
        passwordCol.isResizable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        birthDayCol.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));


        users_table.setItems(getStudentsList("select * from users where state = 1 "));

        users_table.getColumns().addAll(idCol, usernameCol, passwordCol, levelCol, phoneCol, nameCol
                , birthDayCol, addressCol);
    }

    private ObservableList<Users> getStudentsList(String query) {
        ObservableList<Users> list = FXCollections.observableArrayList();

        Connection con = DBConnect.setConnection();
        try {
            Statement stmt = con.createStatement();

            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Users users = new Users();
                users.setId(result.getInt("idusers"));
                users.setUsername(result.getString("username"));
                users.setPassword(result.getInt("password"));
                users.setLevel(result.getString("type"));
                users.setPhone(result.getInt("phone"));
                users.setFullName(result.getString("full_name"));
                users.setBirthDay(result.getString("birth_day"));
                users.setAddress(result.getString("address"));

                list.add(users);

            }
            filteredData = new FilteredList<>(list, p -> true);


            result.close();
            stmt.close();
            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }



    private void updateUser() {
        Connection conn = DBConnect.setConnection();
        int id = Integer.parseInt(id_field.getText());
        System.out.println("id select =="+id);
       /* if(!phone_Field.getText().matches("(09)[1245][0-9]{7}")){
            Messages.viewWaringMessage ("يجب ان يحتوي رقم الهاتف علي 7 ارقام.") ;
        }
       else  {*/

            try {


                String sql = "UPDATE `users` SET "
                        + "`username`=?,"
                        + "`password`=?,"
                        + "`type`=?,"
                        + "`phone`=? ,"
                        + "`full_name`=?, "
                        + "`birth_day`=? ,"
                        + "`address`=?,"
                        + "`state`= 1"
                        + " WHERE idusers=" + id;

                pst = conn.prepareStatement(sql);

                //pst.setInt(1,Integer.parseInt( id_field.getText()));
                pst.setString(1,  username_field.getText());
                pst.setInt(2,Integer.parseInt(password_field.getText()));
                pst.setString(3, level_field.getText());
                pst.setInt(4,  Integer.parseInt(phone_Field.getText()));
                pst.setString(5,  last_name_field.getText());
                pst.setString(6,date_field.getEditor().getText());
                pst.setString(7, address_field.getText());
                pst.executeUpdate();
                users_table.setItems(getStudentsList("select * from users where state = 1 "));
                clearForm();
                pst.close();
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(ControlUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    private void setUpFields(int id){
        Connection conn = DBConnect.setConnection();
        try {
            String query = "select * from users where state = 1 and idusers=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_field.setText(String.valueOf(Integer.parseInt(String.valueOf(id))));
                username_field.setText(rs.getString("username"));
                password_field.setText(rs.getString("password"));
                level_field.setText(rs.getString("type"));
                phone_Field.setText(rs.getString("phone"));
                last_name_field.setText(rs.getString("full_name"));
                date_field.getEditor().setText(rs.getString("birth_day"));
                address_field.setText(rs.getString("address"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearForm(){
        address_field.setText(null);
        username_field.setText(null);
        date_field.getEditor().setText(null);
        level_field.setText(null);
        last_name_field.setText(null);
        phone_Field.setText(null);
        password_field.setText(null);
    }

}
