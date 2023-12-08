package app.section;

import app.Controller;
import app.Todo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class FinishedSection extends VBox {
    private Controller ctr = new Controller();

    public FinishedSection(ArrayList<Todo> todos) {
        // initialisation de ListView:
        ListView<HBox> todoListView = new ListView<>();
        todoListView.getStyleClass().add("list");
        VBox.setVgrow(todoListView, Priority.ALWAYS);

        // fournir les taches dans ListView:
        ctr.addTodosToListView(2, todoListView, todos);


        Button deleteAllButton = new Button("Supprimer tout");
        deleteAllButton.getStyleClass().add("delete-all-button");
        deleteAllButton.setOnMouseClicked(event -> {ctr.deleteAllTodos(todos, todoListView);});

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.getChildren().add(deleteAllButton);
        HBox.setHgrow(buttonContainer, Priority.ALWAYS);
        buttonContainer.setPadding(new Insets(10));

        super.getChildren().addAll(todoListView, buttonContainer);
    }
}
