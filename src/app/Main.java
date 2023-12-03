package app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


class Todo {
    private static int lastIndex = 0;
    private int index;
    private String value;
    private boolean isChecked;

    public Todo(String val){
        index = lastIndex + 1;
        value = val;
        isChecked = false;
        lastIndex++;
    }

    public int getIndex(){
        return index;
    }

    public String getValue(){
        return value;
    }

    public boolean getIsChecked(){
        return isChecked;
    }

    public void toggleCheck(){
        isChecked = !isChecked;
    }
}

public class Main extends Application {
    private String iconUrl = "icon.png";

    @Override
    public void start(Stage stage) {
        Todo[] todos = {
                new Todo("first todo."),
                new Todo("second todo."),
                new Todo("third todo."),
                new Todo("forth todo.")
        };


//        StackPane root = new StackPane();
        VBox vBox = new VBox();


        HBox hBox = new HBox();

        for (Todo todo : todos) {
            Label label = new Label(todo.getValue());
            vBox.getChildren().add(label);
        }


        hBox.getChildren().add(vBox);


        // Scene:
        Scene scene = new Scene(hBox);


        // Stage:
            // icon de stage:
        Image icon = new Image(iconUrl);
        stage.getIcons().add(icon);

            // taille de stage:
        stage.setHeight(450);
        stage.setMinHeight(400);
        stage.setMaxHeight(500);
        stage.setWidth(350);
        stage.setMinWidth(300);
        stage.setMaxWidth(450);

        stage.setTitle("Liste De Tâches");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
