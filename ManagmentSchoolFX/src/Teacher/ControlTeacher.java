package Teacher;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import sample.DBConnect;
import sample.Messages;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlTeacher implements Initializable {
    PreparedStatement pst = null;
    ResultSet rs = null;

    @FXML
    private JFXRadioButton sp_teacherR;

    @FXML
    private ToggleGroup userg;

    @FXML
    private JFXRadioButton name_teacherR;

    @FXML
    private JFXRadioButton id_teacherR;

    @FXML
    private JFXTextField search_Field;

    @FXML
    private ImageView search_btn;

    @FXML
    private TableView<Teacher> teacher_table;

    @FXML
    private JFXTextField name_field;

    @FXML
    private DatePicker birthDate_field;

    @FXML
    private JFXTextField natnumber_field;

    @FXML
    private JFXTextField spiacly_field;

    @FXML
    private JFXTextField address_field;

    @FXML
    private DatePicker dateStart_field;

    @FXML
    private JFXTextField allot_field;

    @FXML
    private JFXTextField phone_field;

    @FXML
    private JFXButton delete_btn;

    @FXML
    private JFXButton save_btn;

    @FXML
    private JFXButton add_btn;

    @FXML
    private ImageView clear_field1;
    @FXML
    private JFXTextField id_field;

    /**
     * Initializes the controller class.
     */

    private static Teacher editable_Student;

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        Teacher teacher = new Teacher();
        setUpTable();
        AutoIncreament();



        teacher_table.setOnMouseClicked(event ->{
                Teacher s = teacher_table.getSelectionModel().getSelectedItem();
                setUpFields(s.getId());
                System.out.println("id == "+s.getId());


        });

        search_Field.setOnKeyReleased(event -> search_Text());
        clear_field1.setOnMouseClicked(event -> {clearForm();
        id_field.setText(null); AutoIncreament();});

        add_btn.setOnAction(e -> {
            if(id_field.getText().isEmpty() || name_field.getText().isEmpty() || birthDate_field.getEditor().getText().isEmpty()
            || natnumber_field.getText().isEmpty() || spiacly_field.getText().isEmpty() || address_field.getText().isEmpty()||
            dateStart_field.getEditor().getText().isEmpty() || allot_field.getText().isEmpty() || phone_field.getText().isEmpty() ){

                Messages.viewWaringMessage(" يجب عدم ترك الحقول فارغة !");
                if(!phone_field.getText().matches("[0-9]{7}+")){
                    Messages.viewWaringMessage ("يجب ان يحتوي رقم الهاتف علي 7 ارقام.") ;
                }
            }else {
                InsertUsers();
                teacher_table.setItems(getTeachersList("select * from teachars where state = 1 "));
                AutoIncreament();
                Messages.Notification("اضافة معلم","تمت عمليةالاضافة بنجاح");
                clearForm();
            }



        });

        save_btn.setOnAction(e -> {
            if(teacher_table.getSelectionModel().getSelectedIndex() == -1){
                Messages.viewWaringMessage("يجب عليك تحديد المعلم اولآ !");
                return;
            }else {
                updateUser();
                teacher_table.setItems(getTeachersList("select * from teachars where state = 1 "));
                clearForm();
                AutoIncreament();
                Messages.Notification("تعديل بيانات معلم","تمت عملية التعديل بنجاح");
            }
        });

        delete_btn.setOnAction(e -> {
            if(teacher_table.getSelectionModel().getSelectedIndex() == -1){
                Messages.viewWaringMessage("يجب عليك تحديد المستخدم اولآ !");
                return;
            }
            if(Messages.viewConfirmDialog("حذف المعلم","هل تريد حذف هذا المستخدم ؟")){
                 teacher_table.getSelectionModel().getSelectedItem();
                deleteUsers(teacher.getId());

                Messages.Notification("حذف  معلم","تمت عملية الحذف بنجاح");

            }
        });



    }

    private void search_Text() {
        ToggleGroup TBtn = new ToggleGroup();
        id_teacherR.setToggleGroup(TBtn);
        name_teacherR.setToggleGroup(TBtn);
        sp_teacherR.setToggleGroup(TBtn);

        String stSearch = "select * from teachars where state = 1 and ";
        if (id_teacherR.isSelected()) {
            stSearch += " id_teachar ";
        } else if(name_teacherR.isSelected()){
            stSearch += " teachar_name ";
        }else {
            sp_teacherR.isSelected();
            stSearch += " work_Now ";
        }


        stSearch += " like '%" + search_Field.getText() + "%'";

        teacher_table.setItems(getTeachersList(stSearch));


    }

    private void AutoIncreament(){
        Connection conn = DBConnect.setConnection();

        String query ="select max(id_teachar)+1 as 'columnName' from teachars ";
        try {
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                String id = rs.getString("columnName");
                System.out.println("teacher id"+id);
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
                String query = "INSERT INTO `teachars`(`id_teachar`, `teachar_name`, `nat_number`, `brithdate`, `address`" +
                        ", `date_SWork`, `work_Now`, `specialy`, `phone`, `state`)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,'1')";

                pst = conn.prepareStatement(query);
                pst.setInt(1, Integer.parseInt(id_field.getText()));
                pst.setString(2, name_field.getText());
                pst.setInt(3, Integer.parseInt(natnumber_field.getText()));
                pst.setString(4, birthDate_field.getEditor().getText());
                pst.setString(5, address_field.getText());
                pst.setString(6, dateStart_field.getEditor().getText());
                pst.setString(7, allot_field.getText());
                pst.setString(8, spiacly_field.getText());
                pst.setString(9, phone_field.getText());
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
            String query = "Update `teachars`  SET state =0 WHERE id_teachar = ? ;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            ps.execute();

            ps.close();
            conn.close();
            String sql = "select * from teachars where state = 1 ";
            teacher_table.setItems(getTeachersList(sql));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void setUpTable() {

        TableColumn<Teacher, String> idCol = new TableColumn<>("رقم المعلم");
        TableColumn<Teacher, String> nameCol = new TableColumn<>("اسم المعلم");
        TableColumn<Teacher, String> dateBirthCol = new TableColumn<>("تاريخ الميلاد");
        TableColumn<Teacher, String> natNumberCol = new TableColumn<>("الرقم الوطني");
        TableColumn<Teacher, String> nationalCol = new TableColumn<>("الجنسية");
        TableColumn<Teacher, String> dateStartCol = new TableColumn<>("تاريخ التعيين");
        TableColumn<Teacher, String> addressCol = new TableColumn<>("العنوان");
        TableColumn<Teacher, String> allotCol = new TableColumn<>("التخصص ");
        TableColumn<Teacher, String> phoneCol = new TableColumn<>("رقم الهاتف");

       idCol.isResizable();
        nameCol.isResizable();
        dateStartCol.isResizable();
        addressCol.isResizable();
        phoneCol.isResizable();
        allotCol.isResizable();
        natNumberCol.isResizable();
        dateBirthCol.isResizable();
        nationalCol.isResizable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        natNumberCol.setCellValueFactory(new PropertyValueFactory<>("natNumber"));
        dateBirthCol.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        nationalCol.setCellValueFactory(new PropertyValueFactory<>("specialy"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        dateStartCol.setCellValueFactory(new PropertyValueFactory<>("dateStartWork"));
        allotCol.setCellValueFactory(new PropertyValueFactory<>("NawWork"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));


        teacher_table.setItems(getTeachersList("select * from teachars where state = 1 "));

        teacher_table.getColumns().addAll(idCol, nameCol
                , dateBirthCol,natNumberCol, nationalCol
                , dateStartCol, addressCol, allotCol,phoneCol);
    }

    private ObservableList<Teacher> getTeachersList(String query) {
        ObservableList<Teacher> list = FXCollections.observableArrayList();
        Connection con = DBConnect.setConnection();
        try {
            Statement stmt = con.createStatement();

            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(result.getInt("id_teachar"));
                teacher.setFullName(result.getString("teachar_name"));
                teacher.setNatNumber(result.getInt("nat_number"));
                teacher.setBirthDay(result.getString("brithdate"));
                teacher.setAddress(result.getString("address"));
                teacher.setDateStartWork(result.getString("date_SWork"));
                teacher.setNawWork(result.getString("work_Now"));
                teacher.setSpecialy(result.getString("specialy"));
                teacher.setPhone(result.getInt("phone"));

                list.add(teacher);
            }


            result.close();
            stmt.close();
            con.close();

            FilteredList filteredDate = new FilteredList<>(list, p -> true);
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

                pst = conn.prepareStatement(sql);

                //pst.setInt(1,Integer.parseInt( id_field.getText()));
                pst.setString(1,  name_field.getText());
                pst.setInt(2,Integer.parseInt(natnumber_field.getText()));
                pst.setString(3, birthDate_field.getEditor().getText());
                pst.setString(4, address_field.getText());
                pst.setString(5,  dateStart_field.getEditor().getText());
                pst.setString(6,allot_field.getText());
                pst.setString(7,spiacly_field.getText());
                pst.setInt(8,  Integer.parseInt(phone_field.getText()));

                pst.executeUpdate();
                teacher_table.setItems(getTeachersList("select * from teachars where state = 1 "));
                clearForm();
                pst.close();
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(ControlTeacher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    private void setUpFields(int id){
        Connection conn = DBConnect.setConnection();
        try {
            String query = "select * from teachars where state = 1 and id_teachar=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                id_field.setText(String.valueOf(Integer.parseInt(String.valueOf(id))));
                name_field.setText(rs.getString("teachar_name"));
                birthDate_field.getEditor().setText(rs.getString("brithdate"));
                natnumber_field.setText(rs.getString("nat_number"));
                spiacly_field.setText(rs.getString("specialy"));
                dateStart_field.getEditor().setText(rs.getString("date_SWork"));
                address_field.setText(rs.getString("address"));
                allot_field.setText(rs.getString("work_Now"));
                phone_field.setText(rs.getString("phone"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void clearForm(){
        address_field.setText(null);
        name_field.setText(null);
        natnumber_field.setText(null);
        birthDate_field.getEditor().setText(null);
        allot_field.setText(null);
        dateStart_field.getEditor().setText(null);
        spiacly_field.setText(null);
        phone_field.setText(null);
    }

}
