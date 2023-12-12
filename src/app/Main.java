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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;


public class Main extends Application {
    private int page = 0;
    private Controller ctr = new Controller();

    @Override
    public void start(Stage stage) {
        ArrayList<Todo> todos = new ArrayList<>();

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

        tab1.setOnMouseClicked(event -> {
            page = 0;
            ctr.updateTabs(page, tab1, tab2, tab3, mainVBox, todos);
        });
        tab2.setOnMouseClicked(event -> {
            page = 1;
            ctr.updateTabs(page, tab1, tab2, tab3, mainVBox, todos);
        });
        tab3.setOnMouseClicked(event -> {
            page = 2;
            ctr.updateTabs(page, tab1, tab2, tab3, mainVBox, todos);
        });


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

        // ajoute les element de l'app dans le conteneur principale:
        mainVBox.getChildren().addAll(title, tabs);

        ctr.updateTabs(page, tab1, tab2, tab3, mainVBox, todos);

        // Scene:
        Scene scene = new Scene(mainVBox);

        // importer css:
        String cssFileUrl = "style.css";
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssFileUrl)).toExternalForm());


        // Stage:
            // icon de stage:
        String iconUrl = "icon.png";
        Image AppIcon = new Image(iconUrl);
        stage.getIcons().add(AppIcon);

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
