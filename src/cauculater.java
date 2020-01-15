import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * author:Benjamin
 * data:2018.12.17
 */
public class cauculater extends Application {

    private static TextField value = new TextField("");


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculator");

        // ���û������ϵ��Ų��Լ��ߴ�
        Scene scene = new Scene(this.getCol(), 350, 380);
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    private VBox getCol() {
        VBox one = new VBox();

        // ���֮����
        one.setPadding(new Insets(10));
        one.setSpacing(0);

        one.getChildren().addAll(value, this.getGrid());

        return one;
    }

    private GridPane getGrid() {
        GridPane one = new GridPane();
        one.setPadding(new Insets(10, 10, 10, 10));
        one.setHgap(4);
        one.setVgap(5);
        setButton(one, 1, 1, "C");
        setButton(one, 1, 2, "Del");
        setButton(one, 1, 3, "%");
        setButton(one, 1, 4, "^");
        setButton(one, 2, 1, "7");
        setButton(one, 2, 2, "8");
        setButton(one, 2, 3, "9");
        setButton(one, 2, 4, "/");
        setButton(one, 3, 1, "4");
        setButton(one, 3, 2, "5");
        setButton(one, 3, 3, "6");
        setButton(one, 3, 4, "*");
        setButton(one, 4, 1, "1");
        setButton(one, 4, 2, "2");
        setButton(one, 4, 3, "3");
        setButton(one, 4, 4, "-");
        setButton(one, 5, 1, ".");
        setButton(one, 5, 2, "0");
        setButton(one, 5, 3, "=");
        setButton(one, 5, 4, "+");

        return one;
    }

    private void setButton(GridPane grid, int row, int col, String val) {
        Button btn = new Button(val);
        btn.setPrefSize(80, 60);
        switch (val) {
            case "Del":
                btn.setOnAction(event -> {
                    String s = value.getText();
                    if (!s.equals(""))
                        value.setText(s.substring(0, s.length() - 1));
                });
                break;
            case "C":
                btn.setOnAction(event -> value.setText(""));
                btn.setStyle("-fx-text-fill: rgb(190, 0, 0);");
                //"-fx-background-color: rgb(78.0,163.0,248.0);"
                break;
            case "=":
                btn.setStyle("-fx-base: rgb(250, 50, 50);");
                btn.setOnAction(event -> value.setText(getAnswer(value.getText())));
                break;
            default:
                btn.setOnAction(event -> value.setText(value.getText() + val));
                break;
        }
        grid.add(btn, col, row);
    }

    private String getAnswer(String one) {

        String[] numbers;
        double answer = 0;
        if (one.split("\\*").length == 2) {
            numbers = one.split("\\*");
            answer = Float.valueOf(numbers[0]) * Float.valueOf(numbers[1]);
        } else if (one.split("/").length == 2) {
            numbers = one.split("/");
            float num1 = Float.valueOf(numbers[0]);
            float num2 = Float.valueOf(numbers[1]);
            if (num2 == 0) return "Error!";
            else answer = num1 / num2;
        } else if (one.split("\\^").length == 2) {
            numbers = one.split("\\^");
            answer = Math.pow(Float.valueOf(numbers[0]), Float.valueOf(numbers[1]));
        } else if (one.split("%").length == 2) {
            numbers = one.split("%");
            answer = Float.valueOf(numbers[0]) % Float.valueOf(numbers[1]);
        } else if (one.split("\\+").length == 2) {
            numbers = one.split("\\+");
            answer = Float.valueOf(numbers[0]) + Float.valueOf(numbers[1]);
        } else if (one.split("-").length == 2) {
            numbers = one.split("-");
            System.out.println(one + "--" + one.split("-").length + "--" + numbers[0]);
            if (numbers[0].equals("")) {
                answer = Float.valueOf(numbers[1]) * -1;
            } else {
                answer = Float.valueOf(numbers[0]) - Float.valueOf(numbers[1]);
            }
        }
        return answer + "";
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
