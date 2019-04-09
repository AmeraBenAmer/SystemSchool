package TeacherStage;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sample.DBConnect;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class char_m implements Initializable {

    @FXML
    private Circle cp;

    @FXML
    private Circle cg;

    @FXML
    private Circle cb;

    @FXML
    private Circle cf;

    @FXML
    private Label field;

    @FXML
    private Label girl;

    @FXML
    private Label boy;

    @FXML
    private Label pass;

    @FXML
    private PieChart pioChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        animation();
        try {
            fullRec();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ObservableList<PieChart.Data> pio = FXCollections.observableArrayList(
                new PieChart.Data("الاول", getNumberOfStudent("اول")),
                new PieChart.Data("الثاني", getNumberOfStudent("ثاني")),
                new PieChart.Data("الخامس", getNumberOfStudent("خامس")),
                new PieChart.Data("السادس", getNumberOfStudent("سادس")),
                new PieChart.Data("كيجي1", getNumberOfStudent("كيجي1")),
                new PieChart.Data("كيجي2", getNumberOfStudent("كيجي2"))



        );
        pioChart.setData(pio);
        pioChart.setStartAngle(0);
        pioChart.setClockwise(true);
        pioChart.setLabelsVisible(true);
        pioChart.setAnimated(true);
        pioChart.setLabelLineLength(50);

        pio.stream().forEach(pieData -> {
            pieData.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Bounds b1 = pieData.getNode().getBoundsInLocal();
                double newX = (b1.getWidth()) / 2 + b1.getMinX();
                double newY = (b1.getHeight()) / 2 + b1.getMinY();
                // Make sure pie wedge location is reset
                pieData.getNode().setTranslateX(0);
                pieData.getNode().setTranslateY(0);
                TranslateTransition tt = new TranslateTransition(
                        Duration.millis(1500), pieData.getNode());
                tt.setByX(newX);
                tt.setByY(newY);
                tt.setAutoReverse(true);
                tt.setCycleCount(2);
                tt.play();
            });
        });



    }

    private int getNumberOfStudent(String classto){
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

            ScaleTransition tcb = new ScaleTransition(Duration.seconds(2),cb);
            tcb.setToX(1.5);
            tcb.setToY(1.5);
            tcb.setAutoReverse(true);
            tcb.setCycleCount(4);
            tcb.play();

            ScaleTransition tcp = new ScaleTransition(Duration.seconds(2),cp);
            tcp.setToX(1.5);
            tcp.setToY(1.5);
            tcp.setAutoReverse(true);
            tcp.setCycleCount(4);
            tcp.play();

            ScaleTransition tcg = new ScaleTransition(Duration.seconds(2),cg);
            tcg.setToX(1.5);
            tcg.setToY(1.5);
            tcg.setAutoReverse(true);
            tcg.setCycleCount(4);
            tcg.play();

            ScaleTransition tcf = new ScaleTransition(Duration.seconds(2),cf);
            tcf.setToX(1.5);
            tcf.setToY(1.5);
            tcf.setAutoReverse(true);
            tcf.setCycleCount(4);
            tcf.play();

        }

        public void fullRec() throws SQLException {
            Connection conn = DBConnect.setConnection();

                Statement stmt = conn.createStatement();
                String query = "select COUNT(*) from students WHERE gender = 'انتى' ";

                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()) {
                    girl.setText(String.valueOf(rs.getInt("COUNT(*)")));
                    System.out.println("count name gril " + rs.getInt("COUNT(*)"));

                }
               //  stmt = conn.createStatement();
                 query = "select COUNT(*) from students WHERE gender = 'ذكر' ";
                 rs = stmt.executeQuery(query);

                if(rs.next()){
                    boy.setText(String.valueOf(rs.getInt("COUNT(*)")));
                    System.out.println("count name boy "+rs.getInt("COUNT(*)"));

                }



                // stmt = conn.createStatement();
                 query = "select COUNT(*) from result WHERE workdegree+finaldegree >= 50 ";
                 rs = stmt.executeQuery(query);

                if(rs.next()){
                    pass.setText(String.valueOf(rs.getInt("COUNT(*)")));
                    System.out.println("count name pass "+rs.getInt("COUNT(*)"));

                }




               // Statement stmt = conn.createStatement();
                 query = "select COUNT(*) as counter from result WHERE workdegree+finaldegree < 50 ";
                 rs = stmt.executeQuery(query);

                if(rs.next()){
                    field.setText(String.valueOf(rs.getInt("counter")));
                    System.out.println("count name not pass "+rs.getInt("counter"));

                }




        }
    }

