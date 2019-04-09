package students;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.DBConnect;
import sample.Messages;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControlStudent implements Initializable {
    Connection conn = null;
    PreparedStatement pst = null;

    @FXML
    private JFXRadioButton class_stu;

    @FXML
    private ToggleGroup userg;

    @FXML
    private JFXRadioButton name_stu;

    @FXML
    private JFXRadioButton id_stu;

    @FXML
    private JFXTextField search_field;

    @FXML
    private ImageView btn_search;

    @FXML
    private TableView<Student> students_table;

    @FXML
    private JFXTextField name_students;

    private  Image image;


    @FXML
    private ImageView imageStudent;

    private FilteredList<Student> filteredData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setUpTable();

        students_table.setOnMouseClicked(event -> {
            if (students_table.getSelectionModel().getSelectedIndex() == -1) {
                Messages.viewWaringMessage("يجب عليك تحديد المستخدم اولآ !");
            } else {
                students_table.getSelectionModel().getSelectedItem();


            }
        });

        students_table.setOnMouseClicked(event -> {
            try {
                getImage();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("image" );

        });

        search_field.setOnKeyReleased(event -> search_Text());


    }

    private void getImage() throws SQLException, IOException {

        ResultSet rs;

        Student student = (Student) students_table.getSelectionModel().getSelectedItem();

        System.out.println("image" + student.getIdStudent());


        String query = "select photo from students where id_stu = ?";
        pst = conn.prepareStatement(query);
        pst.setInt(1, student.getIdStudent());
        rs = pst.executeQuery();

        System.out.println("image");

        if (rs.next()) {

            //  id_std.setText(rs.getString("student_id"));

            InputStream is = rs.getBinaryStream("photo");
            BufferedInputStream bis = new BufferedInputStream(is);

            OutputStream os = new FileOutputStream(new File("photo.jpg"));
            BufferedOutputStream bos = new BufferedOutputStream(os);

            int d;

            while ((d = bis.read()) != -1) {
                bos.write(d);
            }

            is.close();
            os.close();


            System.out.println("hfghdh");
            image = new Image("file:photo.jpg", 250, 250, true, true);
            imageStudent.setImage(image);

        }else {
            Messages.viewErrorMessage("لم يتم ادخال صورة للطالب");
        }

    }


    private void search_Text() {

    //    students_table.refresh();
        String stSearch = "select * from students where state = 1 and";
        if (name_stu.isSelected()) {
            stSearch += " name_stu ";
        } else if(id_stu.isSelected()){
            stSearch += " id_stu ";
        }else if(class_stu.isSelected()){
            stSearch += " stu_class ";
        }

        stSearch += " like '%" + search_field.getText() + "%'";

        students_table.setItems(getStudentsList(stSearch));


    }

    private void setUpTable(){
        TableColumn<Student, String> idCol = new TableColumn<>("رقم الطالب");
        TableColumn<Student, String> nameCol = new TableColumn<>("اسم الطالب رباعي");
        TableColumn<Student, String> natCol = new TableColumn<>("الرقم الوطني");
        TableColumn<Student, String> birthPlaceCol = new TableColumn<>("مكان الميلاد");
        TableColumn<Student, String> birthDayCol = new TableColumn<>("تاريخ الميلاد");
        TableColumn<Student, String> addressCol = new TableColumn<>("عنوان السكن");
        TableColumn<Student, String> admissionDateCol = new TableColumn<>("تاريخ الالتحاق بالمدرسة");
        TableColumn<Student, String> motherCol = new TableColumn<>("اسم الام");
        TableColumn<Student, String> fromCol = new TableColumn<>("الجنسية");
        TableColumn<Student, String> educationStageCol = new TableColumn<>("المرحلة التعليمية");
        TableColumn<Student, String> phoneCol = new TableColumn<>("هاتف ولي الامر");
        TableColumn<Student, String> genderCol = new TableColumn<>(" الجنس");
        TableColumn<Student, String> statusCol = new TableColumn<>("الحالة");
        TableColumn<Student, String> healthStatusCol = new TableColumn<>("الحالة الصحية للطالب");
        TableColumn<Student, String> moneyCol = new TableColumn<>("قيمة السداد");
        TableColumn<Student, String> classroomCol = new TableColumn<>("الفصل");


        idCol.setCellValueFactory(new PropertyValueFactory<>("idStudent"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameStu"));
        natCol.setCellValueFactory(new PropertyValueFactory<>("natNumber"));
        birthPlaceCol.setCellValueFactory(new PropertyValueFactory<>("placeBirth"));
        birthDayCol.setCellValueFactory(new PropertyValueFactory<>("DateBirth"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        admissionDateCol.setCellValueFactory(new PropertyValueFactory<>("dateBirth"));
        motherCol.setCellValueFactory(new PropertyValueFactory<>("nameMam"));
        fromCol.setCellValueFactory(new PropertyValueFactory<>("sexual"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneFamily"));
        educationStageCol.setCellValueFactory(new PropertyValueFactory<>("educationStage"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        healthStatusCol.setCellValueFactory(new PropertyValueFactory<>("healthStatus"));
        moneyCol.setCellValueFactory(new PropertyValueFactory<>("money"));
        classroomCol.setCellValueFactory(new PropertyValueFactory<>("studentClassRoom"));

        students_table.columnResizePolicyProperty();


        students_table.setItems(getStudentsList("select * from students where state = 1 "));

        students_table.getColumns().addAll(idCol, nameCol, natCol, birthPlaceCol, birthDayCol , addressCol,
                admissionDateCol, motherCol, fromCol, educationStageCol, phoneCol, classroomCol, genderCol,
                statusCol, healthStatusCol,moneyCol);
    }



    private ObservableList<Student> getStudentsList(String query) {
        ObservableList<Student> list = FXCollections.observableArrayList();
        Connection con = DBConnect.setConnection();
        try {
            Statement stmt = con.createStatement();

            ResultSet result = stmt.executeQuery(query);
           while (result.next()) {
               Student student = new Student();

                student.setIdStudent(result.getInt("id_stu"));
                student.setNameStu(result.getString("name_stu"));
                student.setNatNumber(result.getInt("nat_number"));
                student.setDateBirth(result.getString("date_birth"));
                student.setPlaceBirth(result.getString("place_birth"));
                student.setAddress(result.getString("address"));
                student.setDateJoin(result.getString("date_join"));
                student.setNameMam(result.getString("name_mam"));
                student.setSexual(result.getString("sexual"));
                student.setEducationStage(result.getString("education_stage"));
                student.setPhoneFamily(result.getInt("phone_family"));
                student.setStudentClassRoom(result.getString("stu_class"));
                student.setGender(result.getString("gender"));
                student.setStatus(result.getString("status"));
                student.setHealthStatus(result.getString("health_status"));
                student.setMoney(result.getInt("money"));

               list.add(student);
            }


            filteredData = new FilteredList<>(list, p -> true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }





}




