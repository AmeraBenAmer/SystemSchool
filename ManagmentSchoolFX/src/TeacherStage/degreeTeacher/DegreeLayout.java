package TeacherStage.degreeTeacher;

import Degree.Degree;
import Teacher.Teacher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import sample.DBConnect;
import sample.Messages;
import users.ControlUser;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DegreeLayout implements Initializable {


    @FXML
    private JFXRadioButton subjectR;

    @FXML
    private ToggleGroup userg;

    @FXML
    private JFXRadioButton classR;

    @FXML
    private JFXRadioButton names;

    @FXML
    private JFXTextField searchField;

    @FXML
    private ImageView search_field;

    @FXML
    private TableView<Degree> degree_table;

    @FXML
    private JFXTextField id_field;

    @FXML
    private JFXTextField address_field;

    @FXML
    private JFXTextField class_field;

    @FXML
    private JFXTextField subject_field;

    @FXML
    private JFXTextField techer_Field;

    @FXML
    private JFXTextField deggre1;

    @FXML
    private JFXTextField DFinal;

    @FXML
    private JFXButton deletBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton addBtn;

    @FXML
    private ImageView clear_field1;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        clear_field1.setOnMouseClicked(event -> clearForm());


            setUpTable();
        searchField.setOnKeyReleased(event -> search_Text());

        degree_table.setOnMouseClicked(event ->{
           Degree s = degree_table.getSelectionModel().getSelectedItem();
           setUpFields(s.getId());
           System.out.println("id == "+s.getId());


        });

        subject_field.setEditable(false);
        class_field.setEditable(false);
        id_field.setEditable(false);
        address_field.setEditable(false);
        techer_Field.setEditable(false);

        saveBtn.setOnMouseClicked(event -> {
            updateUser();
            Messages.Notification("اضافة درجات طالب","تمت عملية اضافة الدرجة بنجاح ");

        });

    }

    private void updateUser() {
        Connection conn = DBConnect.setConnection();
        int id = Integer.parseInt(id_field.getText());
        System.out.println("id select =="+id);

        try {


            String sql = "UPDATE `result` SET "
                    + "`finaldegree `=?,"
                    + "`workdegree`=?,"
                    + " WHERE idusers=" + id;

            PreparedStatement pst  = conn.prepareStatement(sql);

            //pst.setInt(1,Integer.parseInt( id_field.getText()));

            pst.setString(1,  DFinal.getText());
            pst.setString(2, deggre1.getText());
            pst.executeUpdate();

            setUpTable();
            clearForm();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(ControlUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearForm(){
        address_field.setText(null);
        DFinal.setText(null);
        class_field.setText(null);
        techer_Field.setText(null);
        id_field.setText(null);
        subject_field.setText(null);
        deggre1.setText(null);
    }



    private void setUpFields(int id){
        Connection conn = DBConnect.setConnection();
        try {
            String query = "Select idresult , name_stu , class_Name ,nameSub, teachar_name , workdegree , finaldegree from result " +
                    "INNER JOIN class USING (id_class) " +
                    "INNER JOIN students USING (id_stu)INNER JOIN teachars USING (id_teachar)" +
                    " INNER JOIN Subjects USING (codeSubject) where idresult = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs;
            rs = pst.executeQuery();
            while (rs.next()) {
                id_field.setText(String.valueOf(Integer.parseInt(String.valueOf(id))));
                class_field.setText(rs.getString("class_Name"));
                subject_field.setText(rs.getString("nameSub"));
                address_field.setText(rs.getString("name_stu"));
                techer_Field.setText(rs.getString("teachar_name"));
                deggre1.setText(rs.getString("workdegree"));
                DFinal.setText(rs.getString("finaldegree"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void search_Text() {

        String stSearch = "Select idresult , name_stu  , class_Name , nameSub , teachar_name , workdegree , finaldegree\n" +
                "from result INNER JOIN class USING (id_class) INNER JOIN students USING (id_stu)\n" +
                "INNER JOIN teachars USING (id_teachar)  INNER JOIN ";
        if (subjectR.isSelected()) {
            stSearch = "Select idresult , name_stu  , class_Name , nameSub , " +
                    "teachar_name , workdegree , finaldegree" +
                    " from result INNER JOIN class USING (id_class)" +
                    " INNER JOIN students USING (id_stu)" +
                    "INNER JOIN teachars USING (id_teachar)  " +
                    "INNER JOIN subjects USING (codeSubject) where id_teachar = 1 and  nameSub  ";
        } else if(classR.isSelected()){
            stSearch = "Select idresult , name_stu  , class_Name , nameSub ," +
                    "teachar_name , workdegree , finaldegree " +
                    " from result INNER JOIN class USING (id_class) " +
                    "INNER JOIN students USING (id_stu) " +
                    " INNER JOIN teachars USING (id_teachar) " +
                    " INNER JOIN subjects USING (codeSubject)  where id_teachar = 1 and class_Name ";
        }else if(names.isSelected()){
            stSearch ="Select idresult , name_stu  , class_Name , nameSub ," +
                    "teachar_name , workdegree , finaldegree " +
                    " from result INNER JOIN class USING (id_class) " +
                    "INNER JOIN students USING (id_stu) " +
                    " INNER JOIN teachars USING (id_teachar) " +
                    " INNER JOIN subjects USING (codeSubject)  where id_teachar = 1 and name_stu ";
        }

        stSearch += " like '%" + searchField.getText() + "%'";

        degree_table.setItems(getStudentsList(stSearch));


    }



    private void setUpTable(){
        TableColumn<Degree, String> idCol = new TableColumn<>("رقم النتيجة");
        TableColumn<Degree, String> nameCol = new TableColumn<>("اسم الطالب رباعي");
        TableColumn<Degree, String> classCol = new TableColumn<>("الفصل");
        TableColumn<Degree, String> subjectCol = new TableColumn<>("المادة");
        TableColumn<Degree, String> teacherCol = new TableColumn<>("استاذ المادة");
        TableColumn<Degree, String> workCol = new TableColumn<>("اعمال السنة");
        TableColumn<Degree, String> finalCol = new TableColumn<>("درجة المتحان النهائي");
        TableColumn<Degree, String> sumCol = new TableColumn<>("المجموع");


        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameStudent"));
        classCol.setCellValueFactory(new PropertyValueFactory<>("classRoom"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subjectNmae"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<>("nameTeachar"));
        workCol.setCellValueFactory(new PropertyValueFactory<>("workDegree"));
        finalCol.setCellValueFactory(new PropertyValueFactory<>("finalDegree"));
        sumCol.setCellValueFactory(new PropertyValueFactory<>("sumDegree"));


        degree_table.columnResizePolicyProperty();

        String query = "Select idresult , name_stu , class_Name , nameSub" +
                " , teachar_name , workdegree , finaldegree from result" +
                " INNER JOIN class USING (id_class) INNER " +
                "JOIN students USING (id_stu) " +
                "INNER JOIN teachars USING (id_teachar) " +
                "INNER JOIN Subjects USING (codeSubject) where id_teachar = 1 ";
        degree_table.setItems(getStudentsList(query));

        degree_table.getColumns().addAll(idCol, nameCol, classCol, subjectCol, teacherCol , workCol,
                finalCol, sumCol);
    }



    private ObservableList<Degree> getStudentsList(String query) {
        ObservableList<Degree> list = FXCollections.observableArrayList();
        Connection con = DBConnect.setConnection();
        try {
            Statement stmt = con.createStatement();

            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Degree degree = new Degree();


                degree.setId(result.getInt("idresult"));
                degree.setNameStudent(result.getString("name_stu"));
                degree.setClassRoom(result.getString("class_Name"));
                degree.setSubjectNmae(result.getString("nameSub"));
                degree.setNameTeachar(result.getString("teachar_name"));
                Double sum = result.getDouble("workdegree" )+(result.getDouble("finaldegree"));
                degree.setWorkDegree(result.getDouble("workdegree"));
                degree.setFinalDegree(result.getDouble("finaldegree"));
                degree.setSumDegree(sum);

                list.add(degree);
            }


            FilteredList filteredData = new FilteredList<>(list, p -> true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
