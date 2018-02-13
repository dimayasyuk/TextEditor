package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {
        MenuBar menuBar;
        //ContextMenu editMenu;
        //ToolBar toolBar;
    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Текстовый редактор");
        primaryStage.getIcons().add(new Image("Блокнот.jpg"));
        BorderPane rootNode = new BorderPane();
        Scene myScene = new Scene(rootNode,300,300);
        primaryStage.setScene(myScene);

        menuBar = new MenuBar();

        Menu fileMenu = new Menu("_Файл");
        MenuItem open = new MenuItem("Открыть");
        open.setAccelerator(KeyCombination.keyCombination("shortcut+O"));
        MenuItem close = new MenuItem("Закрыть");
        close.setAccelerator(KeyCombination.keyCombination("shortcut+C"));
        MenuItem save = new MenuItem("Сохранить");
        save.setAccelerator(KeyCombination.keyCombination("shortcut+S"));
        MenuItem exit = new MenuItem("Выйти");
        exit.setAccelerator(KeyCombination.keyCombination("shortcut+E"));
        fileMenu.getItems().addAll(open,close,save,new SeparatorMenuItem(),exit);

        Menu helpMenu = new Menu("Помощь");
        MenuItem help = new MenuItem("О программе");
        helpMenu.getItems().addAll(help);

        MenuItem cut = new MenuItem("Вырезать");
        MenuItem copy = new MenuItem("Копировать");
        MenuItem paste = new MenuItem("Вставить");
        final ContextMenu editMenu = new ContextMenu(cut,copy,paste);

        TextArea textArea = new TextArea();
        textArea.setContextMenu(editMenu);




        menuBar.getMenus().addAll(fileMenu,helpMenu);

        rootNode.setTop(menuBar);
        rootNode.setCenter(textArea);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
