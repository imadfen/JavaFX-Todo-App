package app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;


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
    private int page = 0;

    private void updateTabsStyle(Label tab1, Label tab2, Label tab3){
        tab1.getStyleClass().remove("selected-tab");
        tab2.getStyleClass().remove("selected-tab");
        tab3.getStyleClass().remove("selected-tab");

        switch (page) {
            case 0:
                tab1.getStyleClass().add("selected-tab");
                break;
            case 1:
                tab2.getStyleClass().add("selected-tab");
                break;
            case 2:
                tab3.getStyleClass().add("selected-tab");
                break;
            default:
                break;
        }
    }

    private void toggleTodo(Todo todo, CheckBox checkBox, Text text){
        todo.toggleCheck();
        boolean newValue = todo.getIsChecked();

        checkBox.setSelected(newValue);
        if (newValue){
            text.getStyleClass().add("text-checked");
        } else {
            text.getStyleClass().remove("text-checked");
        }
    }

    @Override
    public void start(Stage stage) {
        Todo[] todos = new Todo[30];

        for (int i = 0; i < 30; i++) {
            todos[i] = new Todo("todo number " + (i+1));
        }


        // titre:
        Label title = new Label("Liste De Tâches");
        title.getStyleClass().add("title");

        // pages:
        HBox tabs = new HBox();

        Label tab1 = new Label("Tout");
        Label tab2 = new Label("Active");
        Label tab3 = new Label("Terminé");
        updateTabsStyle(tab1, tab2, tab3);

        tab1.setOnMouseClicked(event -> {page = 0; updateTabsStyle(tab1, tab2, tab3);});
        tab2.setOnMouseClicked(event -> {page = 1; updateTabsStyle(tab1, tab2, tab3);});
        tab3.setOnMouseClicked(event -> {page = 2; updateTabsStyle(tab1, tab2, tab3);});

        tab1.getStyleClass().add("tab-label");
        tab2.getStyleClass().add("tab-label");
        tab3.getStyleClass().add("tab-label");


        Region spacer1 = new Region();
        Region spacer2 = new Region();
        Region spacer3 = new Region();
        Region spacer4 = new Region();

        // Set HGrow to ALWAYS for spacer nodes
        HBox.setHgrow(spacer1, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(spacer2, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(spacer3, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(spacer4, javafx.scene.layout.Priority.ALWAYS);

        tabs.getChildren().addAll(spacer1, tab1, spacer2, tab2, spacer3, tab3, spacer4);
        tabs.setAlignment(Pos.CENTER);
        tabs.getStyleClass().add("tabs");

        VBox mainVBox = new VBox();
        mainVBox.setAlignment(Pos.TOP_CENTER);

        // liste des taches:
        ListView<HBox> listView = new ListView<>();
        listView.getStyleClass().add("list");


        todos[0].toggleCheck();

        for (Todo todo : todos) {
            HBox todoBox = new HBox();
            CheckBox checkBox = new CheckBox();

            Region todoSpacer = new Region();
            todoSpacer.setPrefWidth(10);

            Text todoText = new Text(todo.getValue());
            todoText.getStyleClass().add("todo-text");

            checkBox.setSelected(todo.getIsChecked());
            if (todo.getIsChecked()){
                todoText.getStyleClass().add("text-checked");
            } else {
                todoText.getStyleClass().remove("text-checked");
            }

            checkBox.setOnAction(event -> {
                toggleTodo(todo, checkBox, todoText);
            });

            todoText.setOnMouseClicked(event -> {
                toggleTodo(todo, checkBox, todoText);
            });


            todoBox.getChildren().addAll(checkBox, todoSpacer, todoText);

            listView.getItems().addAll(todoBox);
        }

        mainVBox.getChildren().addAll(title, tabs, listView);



        // Scene:
        Scene scene = new Scene(mainVBox);

        // importer css:
        String cssFileUrl = "style.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFileUrl)).toExternalForm());


        // Stage:
            // icon de stage:
        String iconUrl = "icon.png";
        Image icon = new Image(iconUrl);
        stage.getIcons().add(icon);

            // taille de stage:
        stage.setHeight(650);
        stage.setWidth(550);
        stage.setResizable(false);

        stage.setTitle("Liste De Tâches");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
