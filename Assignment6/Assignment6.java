/* 
Assignment #: 6
        Name: Michael Buerger
   StudentID: 1214351462
     Lecture: Tu Th 9:00 AM - 10:15 AM
 Description: The Assignment6 class creates a TabPane Pane with
              two tabs, one for adding Heroes and one for
              creating an Army
*/

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;

public class Assignment6 extends Application {
    private TabPane tabPane;
    private HeroPane heroPane;
    private ArmyPane armyPane;
    private ArrayList<Hero> heroList;
    public static final int WINSIZE_X = 950, WINSIZE_Y = 600;

    /*
        Setup root pane, setup heroes list, hero pane, and army pane
        Setup tab pane to handle hero and army panes and add to root pane
        Add root pane to scene and add this to stage, then show it
     */
    public void start(Stage stage) {
        StackPane root = new StackPane();

        // heroList to be used in all tabs
        Hero defaultUnicorn = new Hero("Aliz", "Unicorn", 20, 80, 60);

        heroList = new ArrayList<Hero>();
        heroList.add(defaultUnicorn);

        heroPane = new HeroPane(heroList);
        armyPane = new ArmyPane(heroList);


        tabPane = new TabPane();

        Tab tab1 = new Tab();
        tab1.setText("Add Hero");
        tab1.setContent(heroPane);

        Tab tab2 = new Tab();
        tab2.setText("Create Army");
        tab2.setContent(armyPane);

        tabPane.getSelectionModel().select(0);
        tabPane.getTabs().addAll(tab1, tab2);

        root.getChildren().add(tabPane);

        Scene scene = new Scene(root, WINSIZE_X, WINSIZE_Y);
        stage.setTitle("Create your army!");
        stage.setScene(scene);
        stage.show();
    }

    /*
        Launch JavaFX application
     */
    public static void main(String[] args) {
        launch(args);
    }
}