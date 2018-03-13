package sample;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

       private MenuBar menuBar;
       private  ComboBox<String> fontesSize;
       private ComboBox<String> fontes;
       private ToolBar toolBar;
       private Scene myScene;
       private Canvas canvas;
       private Carriage carriage;
       private GraphicsContext graphicsContext;
       private int startCoordinate  = 10;
       private List<Line> myLines = new ArrayList<>();

    public Scene getScene() {
        return myScene;
    }

    public List<Line> getMyLines(){ return myLines; }

    public GraphicsContext getGraphicsContext(){ return graphicsContext; }

    public Canvas getCanvas(){ return canvas; }

    public int getStartX(){return startCoordinate;}

    private MenuBar createMenuBar(){
            MenuBar menuBar = new MenuBar();
            Menu fileMenu = new Menu("_Файл");

            MenuItem open = new MenuItem("Открыть",new ImageView("open.png"));
            open.setAccelerator(KeyCombination.keyCombination("shortcut+O"));
            open.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });

            MenuItem close = new MenuItem("Закрыть",new ImageView("close.png"));
            close.setAccelerator(KeyCombination.keyCombination("shortcut+C"));
            close.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });

            MenuItem save = new MenuItem("Сохранить",new ImageView("save.png"));
            save.setAccelerator(KeyCombination.keyCombination("shortcut+S"));
            save.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });

            MenuItem exit = new MenuItem("Выйти");
            exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });
            exit.setAccelerator(KeyCombination.keyCombination("shortcut+E"));
            fileMenu.getItems().addAll(open,close,save,new SeparatorMenuItem(),exit);

            Menu changeMenu = new Menu("Правка");


            Menu formatMenu = new Menu("Формат");

            Menu helpMenu = new Menu("Помощь");
            MenuItem help = new MenuItem("О программе",new ImageView("info.png"));
            help.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("О программе");
                    alert.setContentText("Это простой текстовый редактор");
                    alert.show();
                }
            });
            helpMenu.getItems().addAll(help);
            menuBar.getMenus().addAll(fileMenu,changeMenu,formatMenu,helpMenu);

            return menuBar;
        }

        private ToolBar createToolBar(){
            ToolBar toolBar = new ToolBar();

            Button BoldFont = new Button("Полужирный",new ImageView("bold.png"));
            BoldFont.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            BoldFont.setTooltip(new Tooltip("Полужирный)"));

            Button ItalicFont = new Button("Курсив",new ImageView("kyrsiv.jpg"));
            ItalicFont.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            BoldFont.setTooltip(new Tooltip("Курсив"));

            Button UnderlineFont = new Button("Подчеркнутый",new ImageView("line.gif"));
            UnderlineFont.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            BoldFont.setTooltip(new Tooltip("Подчеркнутый"));

            ObservableList<String> fontSize = FXCollections.observableArrayList("10","12","14","16","18","20");
            fontesSize = new ComboBox<>(fontSize);
            fontesSize.setValue("10");
            fontesSize.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });

            ObservableList<String> fontesList = FXCollections.observableArrayList("Arial","Times New Roman");
            fontes = new ComboBox<>(fontesList);
            fontes.setValue("Arial");
            fontes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });

            toolBar.getItems().addAll(BoldFont,ItalicFont,UnderlineFont,fontesSize,fontes);

            return toolBar;
        }

        public void carriageTimer(){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    paintCarriage();
                }
            },500,1000);
        }

        public void paintCarriage(){
            FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(graphicsContext.getFont());
            graphicsContext.strokeLine(carriage.getCoordinateX(),carriage.getCoordinateY(),carriage.getCoordinateX(),fontMetrics.getLineHeight());
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            graphicsContext.clearRect(carriage.getCoordinateX(),carriage.getCoordinateY(),graphicsContext.getLineWidth(),fontMetrics.getLineHeight());
        }

        public void createInput(){
            carriage = new Carriage();
            carriageTimer();
            myLines.add(new Line());
        }

       /* public void inputText(char key){
        myLines.get(carriage.getCarriageX()).add(carriage.getCarriageX(),key);
        paintCanvas();
        }
        public void paintCanvas(){
            int y = startCoordinate;
            for(Line lines:myLines) {
                for (Char ch : lines.getChars()) {
                    int x = startCoordinate;
                    FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(graphicsContext.getFont());
                    graphicsContext.strokeText(ch.getCharToString(), carriage.getCoordinateX(), carriage.getCoordinateTwoY());
                    ch.setCoordinateX(x);
                    ch.setCoordinateY(y);
                    x += fontMetrics.computeStringWidth(ch.getCharToString()) + 3;
                    //ch.setHeight(fontMetrics.getXheight());
                    //graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());

                }
            }
        }*/

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Текстовый редактор");
        primaryStage.getIcons().add(new Image("Блокнот.jpg"));
        BorderPane rootNode = new BorderPane();
        myScene = new Scene(rootNode,500,500);
        primaryStage.setScene(myScene);


        canvas = new Canvas();
        graphicsContext = canvas.getGraphicsContext2D();
        ScrollPane myPane = new ScrollPane(canvas);

        canvas.widthProperty().bind(myPane.widthProperty());
        canvas.heightProperty().bind(myPane.heightProperty());
        graphicsContext.setFont(new Font("Arial",14));
        rootNode.setCenter(myPane);

        myScene.setOnKeyPressed(new TextListener(this));
        myScene.setOnKeyTyped(new TextListener(this));

        toolBar = createToolBar();
        menuBar = createMenuBar();

        rootNode.setBottom(toolBar);
        rootNode.setTop(menuBar);

        primaryStage.show();

        createInput();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
