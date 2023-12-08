package app;

import app.section.ActiveSection;
import app.section.FinishedSection;
import app.section.ToutSection;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Controller {
    public void updateTabs(int page, Label tab1, Label tab2, Label tab3, VBox mainVBox, ArrayList<Todo> todos){
        tab1.getStyleClass().remove("selected-tab");
        tab2.getStyleClass().remove("selected-tab");
        tab3.getStyleClass().remove("selected-tab");

        switch (page) {
            case 0:
                if (mainVBox.getChildren().size() >= 3) {
                    mainVBox.getChildren().remove(2);
                }
                mainVBox.getChildren().add(2, new ToutSection(todos));
                tab1.getStyleClass().add("selected-tab");
                break;
            case 1:
                if (mainVBox.getChildren().size() >= 3) {
                    mainVBox.getChildren().remove(2);
                }
                mainVBox.getChildren().add(2, new ActiveSection(todos));
                tab2.getStyleClass().add("selected-tab");
                break;
            case 2:
                if (mainVBox.getChildren().size() >= 3) {
                    mainVBox.getChildren().remove(2);
                }
                mainVBox.getChildren().add(2, new FinishedSection(todos));
                tab3.getStyleClass().add("selected-tab");
                break;
            default:
                break;
        }
    }

    public void toggleTodo(Todo todo, CheckBox checkBox, Text text, int page, ListView<HBox> todoListView, ArrayList<Todo> todos){
        todo.toggleCheck();
        boolean newValue = todo.getIsChecked();

        checkBox.setSelected(newValue);
        if (newValue){
            text.getStyleClass().add("text-checked");
        } else {
            text.getStyleClass().remove("text-checked");
        }

        addTodosToListView(page, todoListView, todos);
    }

    public void createTodo(Todo todo, int page, ListView<HBox> listView, ArrayList<Todo> todos){
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
        todoText.setWrappingWidth(440);
        // initialisation de l'etat de text de tache:
        if (todo.getIsChecked()){
            todoText.getStyleClass().add("text-checked");
        } else {
            todoText.getStyleClass().remove("text-checked");
        }

        // set la fonctionnalite de click de checkbox:
        checkBox.setOnAction(event -> {
            toggleTodo(todo, checkBox, todoText, page, listView, todos);
        });

        // set la fonctionnalite de click de text de tache:
        todoText.setOnMouseClicked(event -> {
            toggleTodo(todo, checkBox, todoText, page, listView, todos);
        });


        Image trashIcon = new Image("TrashIcon.png");
        ImageView trashIconView = new ImageView(trashIcon);
        trashIconView.setPreserveRatio(true);
        trashIconView.setFitWidth(20);

        Button deleteButton = new Button();
        deleteButton.setGraphic(trashIconView);
        deleteButton.setOnMouseClicked(event -> {deleteTodo(todos, listView, todoBox, todo);});
        deleteButton.getStyleClass().add("delete-button");



        // ajoute les element de tache dans le conteneur:
        todoBox.getChildren().addAll(checkBox, todoSpacer, todoText, deleteButton);

        // ajoute le conteneur dans le ListView:
        listView.getItems().addAll(todoBox);
    }

    public void addNewTodo(int page, ArrayList<Todo> todos, ListView<HBox> listView, TextField todoInput){
        String newTodoText = todoInput.getText();
        if (!newTodoText.isEmpty()){
            todos.add(new Todo(newTodoText));
            Todo lastTodo = todos.get(todos.size()-1);
            createTodo(lastTodo, page, listView, todos);
            todoInput.clear();
            listView.scrollTo(todos.size() - 1);
        }
    }

    public void addTodosToListView(int page, ListView<HBox> todoListView, ArrayList<Todo> todos){
        todoListView.getItems().clear();

        switch (page){
            case 0:
                for (Todo todo : todos) {
                    createTodo(todo, 0, todoListView, todos);
                }
                break;

            case 1:
                for (Todo todo : todos) {
                    if (!todo.getIsChecked()) {
                        createTodo(todo, 1, todoListView, todos);
                    }
                }
                break;

            case 2:
                for (Todo todo : todos) {
                    if (todo.getIsChecked()) {
                        createTodo(todo, 2, todoListView, todos);
                    }
                }
                break;

            default:
                break;
        }


    }

    public void deleteTodo(ArrayList<Todo> todos, ListView<HBox> listView, HBox todoHBox, Todo todo){
        todos.remove(todo);
        listView.getItems().remove(todoHBox);
    }

    public void deleteAllTodos(ArrayList<Todo> todos, ListView<HBox> listView){
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getIsChecked()){
                todos.remove(todos.get(i));
                listView.getItems().clear();
                i--;
            }
        }
    }

}
