package app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
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

    private void createTodo(Todo todo, ListView<HBox> listView){
        HBox todoBox = new HBox();

        // checkbox:
        CheckBox checkBox = new CheckBox();
        // initialisation de l'etat de checkbox:
        checkBox.setSelected(todo.getIsChecked());

        // espace vide:
        Region todoSpacer = new Region();
        todoSpacer.setPrefWidth(10);

        // text de tache:
        Text todoText = new Text(todo.getValue());
        todoText.getStyleClass().add("todo-text");
        todoText.setWrappingWidth(460);
        // initialisation de l'etat de text de tache:
        if (todo.getIsChecked()){
            todoText.getStyleClass().add("text-checked");
        } else {
            todoText.getStyleClass().remove("text-checked");
        }

        // set la fonctionnalite de click de checkbox:
        checkBox.setOnAction(event -> {
            toggleTodo(todo, checkBox, todoText);
        });

        // set la fonctionnalite de click de text de tache:
        todoText.setOnMouseClicked(event -> {
            toggleTodo(todo, checkBox, todoText);
        });


        // ajoute les element de tache dans le conteneur:
        todoBox.getChildren().addAll(checkBox, todoSpacer, todoText);

        // ajoute le conteneur dans le ListView:
        listView.getItems().addAll(todoBox);
    }

    private void addNewTodo(ArrayList<Todo> todos, ListView<HBox> listView, TextField todoInput){
        String newTodoText = todoInput.getText();
        if (!newTodoText.isEmpty()){
            todos.add(new Todo(newTodoText));
            createTodo(todos.get(todos.size()-1), listView);
            todoInput.clear();
            listView.scrollTo(todos.size() - 1);
        }
    }

    @Override
    public void start(Stage stage) {
        ArrayList<Todo> todos = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            todos.add(new Todo("todo number " + (i+1)));
        }

        // conteneur principale des elements de l'app:
        VBox mainVBox = new VBox();
        mainVBox.setAlignment(Pos.TOP_CENTER);
        mainVBox.setPadding(new Insets(10, 0, 10, 0));

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

        // des espaces:
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        Region spacer3 = new Region();
        Region spacer4 = new Region();

        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        HBox.setHgrow(spacer4, Priority.ALWAYS);

        tabs.getChildren().addAll(spacer1, tab1, spacer2, tab2, spacer3, tab3, spacer4);
        tabs.setAlignment(Pos.CENTER);
        tabs.getStyleClass().add("tabs");

        // liste des taches:
            // initialisation de ListView:
        ListView<HBox> todoListView = new ListView<>();
        todoListView.getStyleClass().add("list");
        VBox.setVgrow(todoListView, Priority.ALWAYS);

            // fournir les taches dans ListView:
        for (Todo todo : todos) {
            createTodo(todo, todoListView);
        }



        HBox addTodoBox = new HBox();
        addTodoBox.setPadding(new Insets(0, 10, 0, 10));
        addTodoBox.setPrefHeight(50);
        addTodoBox.setAlignment(Pos.CENTER);

        TextField addTodoInput = new TextField();
        addTodoInput.setPromptText("ajouter tâche...");
        addTodoInput.getStyleClass().add("new-todo-input");
        HBox.setHgrow(addTodoInput, Priority.ALWAYS);
        addTodoInput.setOnAction(e -> {
            addNewTodo(todos, todoListView, addTodoInput);
        });

        Region newTodoSpacer = new Region();
        newTodoSpacer.setPrefWidth(10);

        Button submitButton = new Button("Ajouter");
        submitButton.getStyleClass().add("add-button");
        submitButton.setOnAction(event -> {
            addNewTodo(todos, todoListView, addTodoInput);
        });

        addTodoBox.getChildren().addAll(addTodoInput, newTodoSpacer, submitButton);



        // ajoute les element de l'app dans le conteneur principale:
        mainVBox.getChildren().addAll(title, tabs, addTodoBox, todoListView);

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
