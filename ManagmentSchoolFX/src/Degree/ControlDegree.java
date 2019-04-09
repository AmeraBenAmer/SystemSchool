package Degree;

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
import javafx.scene.layout.BorderPane;
import sample.Controller;
import sample.DBConnect;
import sample.Messages;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControlDegree implements Initializable {

    @FXML private ToggleGroup userg;
    @FXML private JFXTextField search_field;
    @FXML private ImageView search_btn;
    @FXML private TableView<Degree> degree_table;
    @FXML private JFXRadioButton class_R;
    @FXML private JFXRadioButton nameTeacher_R;
    @FXML private JFXRadioButton subject_R;
    @FXML private JFXRadioButton stuName_R1;
   // private FilteredList<Student> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setUpTable();

        degree_table.setOnMouseClicked(event -> {
            if (degree_table.getSelectionModel().getSelectedIndex() == -1) {
                Messages.viewWaringMessage("يجب عليك تحديد المستخدم اولآ !");
            } else {
                Degree s = degree_table.getSelectionModel().getSelectedItem();
              //  setUpFields(s.getId());
               // System.out.println("id == " + s.getId());

            }
        });

        search_field.setOnKeyReleased(event -> search_Text());

    }
    private void search_Text() {

        String stSearch = "Select idresult , name_stu  , class_Name , nameSub , teachar_name , workdegree , finaldegree\n" +
                "from result INNER JOIN class USING (id_class) INNER JOIN students USING (id_stu)\n" +
                "INNER JOIN teachars USING (id_teachar)  INNER JOIN ";
        if (subject_R.isSelected()) {
            stSearch = "Select idresult , name_stu  , class_Name , nameSub , " +
                    "teachar_name , workdegree , finaldegree" +
                    " from result INNER JOIN class USING (id_class)" +
                    " INNER JOIN students USING (id_stu)" +
                    "INNER JOIN teachars USING (id_teachar)  " +
                    "INNER JOIN subjects USING (codeSubject) WHERE nameSub  ";
        } else if(nameTeacher_R.isSelected()){
            stSearch = "Select idresult , name_stu  , class_Name , nameSub ," +
                    "teachar_name , workdegree , finaldegree " +
                    " from result INNER JOIN class USING (id_class) " +
                    "INNER JOIN students USING (id_stu) " +
                    " INNER JOIN teachars USING (id_teachar) " +
                    " INNER JOIN subjects USING (codeSubject) WHERE teachar_name ";
        }else if(class_R.isSelected()){
            stSearch = "Select idresult , name_stu  , class_Name , nameSub ," +
                    "teachar_name , workdegree , finaldegree " +
                    " from result INNER JOIN class USING (id_class) " +
                    "INNER JOIN students USING (id_stu) " +
                    " INNER JOIN teachars USING (id_teachar) " +
                    " INNER JOIN subjects USING (codeSubject) WHERE class_Name ";
        }else if(stuName_R1.isSelected()){
            stSearch ="Select idresult , name_stu  , class_Name , nameSub ," +
                    "teachar_name , workdegree , finaldegree " +
                    " from result INNER JOIN class USING (id_class) " +
                    "INNER JOIN students USING (id_stu) " +
                    " INNER JOIN teachars USING (id_teachar) " +
                    " INNER JOIN subjects USING (codeSubject) WHERE name_stu ";
        }

        stSearch += " like '%" + search_field.getText() + "%'";

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
                "INNER JOIN Subjects USING (codeSubject)";
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




