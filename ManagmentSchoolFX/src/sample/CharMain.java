package sample;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class CharMain implements Initializable {

    @FXML
    private Rectangle f1;

    @FXML
    private Rectangle p1;

    @FXML
    private Rectangle b1;

    @FXML
    private Rectangle g1;

    @FXML
    private TextField num_pass;

    @FXML
    private TextField num_faild;

    @FXML
    private TextField num_boy;

    @FXML
    private TextField num_grail;

    @FXML
    private BarChart<?, ?> chart;



    @Override
    public void initialize(URL location, ResourceBundle resources) {


       animation();
       num_pass.setText("5888");
            chart.getData().clear();
            ObservableList<String> stagesList = getStages();
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setCategories(stagesList);
            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("العدد");

            XYChart.Series series = new XYChart.Series();
            series.setName("عدد الطلبة الابتدائي في كل مرحلة");
            XYChart.Series series2 = new XYChart.Series();
            series2.setName("عدد الطلبة التمهيدي في كل مرحلة");

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.7), (ActionEvent actionEvent) -> {
                    int cycle  =  timeline.getCycleCount();

                    if (cycle < 4){
                        series.getData().add(new XYChart.Data<>("الاول", 4));
                        series.getData().add(new XYChart.Data<>("الثاني", 3.5));
                        series.getData().add(new XYChart.Data<>("الثالت", 3));
                        series.getData().add(new XYChart.Data<>("الرابع", 2));
                        series.getData().add(new XYChart.Data<>("الخامس", 2.5));
                        series.getData().add(new XYChart.Data<>("السادس",1.5));
                        series2.getData().add(new XYChart.Data<>("كيجي1", 0.5));
                        series2.getData().add(new XYChart.Data<>("كيجي2", 1));
                    }else {

                        series.getData().add(new XYChart.Data<>("الاول", getNumberOfStudents("اول")));
                        series.getData().add(new XYChart.Data<>("الثاني", getNumberOfStudents("ثاني")));
                        series.getData().add(new XYChart.Data<>("الثالت", getNumberOfStudents("ثالت")));
                        series.getData().add(new XYChart.Data<>("الرابع", getNumberOfStudents("رابع")));
                        series.getData().add(new XYChart.Data<>("الخامس", getNumberOfStudents("خامس")));
                        series.getData().add(new XYChart.Data<>("السادس", getNumberOfStudents("سادس")));
                        series2.getData().add(new XYChart.Data<>("كيجي1", getNumberOfStudents("كيجي1")));
                        series2.getData().add(new XYChart.Data<>("كيجي2", getNumberOfStudents("كيجي2")));
                    }



                }));

        timeline.setCycleCount(4);
        timeline.setAutoReverse(true);  //!?
        timeline.play();

            chart.getData().addAll(series,series2);
        try {
            fullRec();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        private ObservableList getStages(){
            Connection conn = DBConnect.setConnection();

            conn = DBConnect.setConnection();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            try {
                Statement stmt = conn.createStatement();
                String query = "SELECT stage_name FROM `education_stage`";
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                    observableList.add(rs.getString("stage_name"));
                    System.out.println("stage name "+rs.getString("stage_name"));


                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return observableList;
        }



        private int getNumberOfStudents(String classto){
            Connection conn = DBConnect.setConnection();

            int counter = 0;
            try {
                Statement stmt = conn.createStatement();
                String query = "select COUNT(*) from students WHERE stu_class = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1,classto);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    counter = rs.getInt("COUNT(*)");
                    System.out.println("count name "+rs.getInt("COUNT(*)"));

                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return counter;
        }

        private void animation(){

            Timeline t = new Timeline();

            KeyValue kv = new KeyValue(g1.translateXProperty(),793);

            KeyFrame kf1 = new KeyFrame(Duration.seconds(4),kv);

            KeyValue kv2 = new KeyValue(b1.translateXProperty(),535);

            KeyFrame kf2 = new KeyFrame(Duration.seconds(5),kv2);

            KeyValue kv3 = new KeyValue(p1.translateXProperty(),273);

            KeyFrame kf3 = new KeyFrame(Duration.seconds(6),kv3);

            KeyValue kv4 = new KeyValue(num_grail.translateXProperty(),793);

            KeyFrame kf4 = new KeyFrame(Duration.seconds(4),kv4);

            KeyValue kv5 = new KeyValue(num_boy.translateXProperty(),535);

            KeyFrame kf5 = new KeyFrame(Duration.seconds(5),kv5);

            KeyValue kv6 = new KeyValue(num_pass.translateXProperty(),273);

            KeyFrame kf6 = new KeyFrame(Duration.seconds(6),kv6);
            t.getKeyFrames().addAll(kf1,kf2,kf3,kf4,kf5,kf6);


            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2));

            fadeTransition.setNode(g1);
            fadeTransition.setNode(p1);
            fadeTransition.setNode(f1);
            fadeTransition.setNode(b1);

            fadeTransition.setNode(num_boy);
            fadeTransition.setNode(num_faild);
            fadeTransition.setNode(num_grail);
            fadeTransition.setNode(num_pass);

            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1.0);

            fadeTransition.setCycleCount(7);
            fadeTransition.play();

            t.setCycleCount(1);
            t.play();


        }

        public void fullRec() throws SQLException {
            Connection conn = DBConnect.setConnection();

                Statement stmt = conn.createStatement();
                String query = "select COUNT(*) from students WHERE gender = 'انتى' ";

                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()) {
                    num_grail.setText(String.valueOf(rs.getInt("COUNT(*)")));
                    System.out.println("count name gril " + rs.getInt("COUNT(*)"));

                }
               //  stmt = conn.createStatement();
                 query = "select COUNT(*) from students WHERE gender = 'ذكر' ";
                 rs = stmt.executeQuery(query);

                if(rs.next()){
                    num_boy.setText(String.valueOf(rs.getInt("COUNT(*)")));
                    System.out.println("count name boy "+rs.getInt("COUNT(*)"));

                }



                // stmt = conn.createStatement();
                 query = "select COUNT(*) from result WHERE workdegree+finaldegree >= 50 ";
                 rs = stmt.executeQuery(query);

                if(rs.next()){
                    num_pass.setText(String.valueOf(rs.getInt("COUNT(*)")));
                    System.out.println("count name pass "+rs.getInt("COUNT(*)"));

                }




               // Statement stmt = conn.createStatement();
                 query = "select COUNT(*) as counter from result WHERE workdegree+finaldegree < 50 ";
                 rs = stmt.executeQuery(query);

                if(rs.next()){
                    num_faild.setText(String.valueOf(rs.getInt("counter")));
                    System.out.println("count name not pass "+rs.getInt("counter"));

                }




        }
    }

