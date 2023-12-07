package app.section;

import app.Controller;
import app.Todo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ToutSection extends VBox {
    private Controller ctr = new Controller();

    public ToutSection(ArrayList<Todo> todos){
        // initialisation de ListView:
        ListView<HBox> todoListView = new ListView<>();
        todoListView.getStyleClass().add("list");
        VBox.setVgrow(todoListView, Priority.ALWAYS);

        // fournir les taches dans ListView:
        ctr.addTodosToListView(0, todoListView, todos);



        HBox addTodoBox = new HBox();
        addTodoBox.setPadding(new Insets(0, 10, 0, 10));
        addTodoBox.setPrefHeight(50);
        addTodoBox.setAlignment(Pos.CENTER);

        TextField addTodoInput = new TextField();
        addTodoInput.setPromptText("ajouter tâche...");
        addTodoInput.getStyleClass().add("new-todo-input");
        HBox.setHgrow(addTodoInput, Priority.ALWAYS);
        addTodoInput.setOnAction(e -> {
            ctr.addNewTodo(0, todos, todoListView, addTodoInput);
        });

        Region newTodoSpacer = new Region();
        newTodoSpacer.setPrefWidth(10);

        Button submitButton = new Button("Ajouter");
        submitButton.getStyleClass().add("add-button");
        submitButton.setOnAction(event -> {
            ctr.addNewTodo(0, todos, todoListView, addTodoInput);
        });

        addTodoBox.getChildren().addAll(addTodoInput, newTodoSpacer, submitButton);

        super.getChildren().addAll(addTodoBox, todoListView);
    }
}
