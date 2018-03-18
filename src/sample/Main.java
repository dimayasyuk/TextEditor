package sample;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import javafx.scene.input.InputEvent;
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
       private int startCoordinate  = 0;
       private List<Line> myLines = new ArrayList<>();

    public Scene getScene() {
        return myScene;
    }

    public List<Line> getMyLines(){ return myLines; }

    public GraphicsContext getGraphicsContext(){ return graphicsContext; }

    public Canvas getCanvas(){ return canvas; }

    public Carriage getCarriage() {
        return carriage;
    }

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
            int y2 = carriage.getCoordinateY() - (int)(0.7*fontMetrics.getLineHeight());
            graphicsContext.strokeLine(carriage.getCoordinateX(),carriage.getCoordinateY(),carriage.getCoordinateX(),y2) ;
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            graphicsContext.clearRect(carriage.getCoordinateX(),carriage.getCoordinateY(),graphicsContext.getLineWidth(),carriage.getCoordinateY()- y2);
        }
         public void canvasTimer(){
            Timer timer = new Timer();
             timer.schedule(new TimerTask() {
                 @Override
                 public void run() {
                     paintCanvas();
                 }
             },100,100);
         }
        public void createInput(){
            carriage = new Carriage();
            carriageTimer();
            Line line = new Line();
            //canvasTimer();
            myLines.add(line);
        }

        public void inputText(char key){
            myLines.get(carriage.getCarriageOfColumn()).add(carriage.getCarriageOfLine(),key,graphicsContext.getFont());
            graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
            carriageToRight();
            paintCanvas();
        }
        public void paintCanvas(){
            for(Line line: myLines){
                 line.setMaxHightOfLine(0);
                 for (Char ch:line.getChars()){
                     Font font = ch.getFont();
                     FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
                     line.setMaxHightOfLine(fontMetrics.getLineHeight());
                 }
                 if (line.getMaxHightOfLine() == 0) line.setMaxHightOfLine(15);
            }
            int y = startCoordinate;
            int lineY = -1;
            for(Line line:myLines) {
                y+=line.getMaxHightOfLine();
                int x = startCoordinate;
                lineY++;
                int letterX = 0;
                for (Char ch : line.getChars()) {
                    letterX++;
                    Font font = ch.getFont();
                    graphicsContext.setFont(font);
                    FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
                    graphicsContext.strokeText(ch.getCharToString(), x, y);
                    ch.setHeight(fontMetrics.getLineHeight());
                    ch.setWeight(fontMetrics.computeStringWidth(ch.toString()));
                    ch.setCoordinateX(x);
                    ch.setCoordinateY(y);
                    x += fontMetrics.computeStringWidth(ch.getCharToString())+ 1;
                    if(carriage.getCarriageOfLine()== letterX && carriage.getCarriageOfColumn() == lineY) {
                        carriage.setCoordinateX(x);
                        carriage.setCoordinateY(y);
                    }
                }
                line.setCoordinateY(y);
                if(carriage.getCarriageOfLine()== 0 && carriage.getCarriageOfColumn() == lineY) {
                    carriage.setCoordinateX(startCoordinate);
                    carriage.setCoordinateY(y);
                }
            }
        }
        public void newLine(){
          Line newLine = myLines.get(carriage.getCarriageOfColumn()).copyOfSubLine(carriage.getCarriageOfLine(),myLines.get(carriage.getCarriageOfColumn()).size());
          myLines.get(carriage.getCarriageOfColumn()).removeCopyOfSubLine(carriage.getCarriageOfLine());
          myLines.add(carriage.getCarriageOfColumn() + 1,newLine);
          carriage.setCarriageOfLine(0);
          carriageToDown();
        }

        public void carriageToDown(){
            boolean isMoveToDown = myLines.size() - 1 > carriage.getCarriageOfColumn();
            boolean isNextLineLess = myLines.get(carriage.getCarriageOfColumn()).size() < carriage.getCarriageOfLine();
            if(isMoveToDown){
                carriage.setCarriageOfColumn(carriage.getCarriageOfColumn() +1 );
            }
            if(isNextLineLess){
                carriage.setCarriageOfColumn(carriage.getCarriageOfColumn() + 1 );
                carriage.setCarriageOfLine(myLines.get(carriage.getCarriageOfColumn()).size());
            }
        }
            public void carriageToUp(){

            }
        public void carriageToRight(){
            boolean isNotEndOfLine = (carriage.getCarriageOfLine() + 1) <= myLines.get(carriage.getCarriageOfColumn()).size();
            boolean isEndText = ((carriage.getCarriageOfColumn() + 1)  == myLines.size() && myLines.get(carriage.getCarriageOfColumn()).size() == carriage.getCarriageOfLine());
            if (isEndText)
                return;
            else if(isNotEndOfLine)
                carriage.setCarriageOfLine(carriage.getCarriageOfLine() + 1 );
            else if (carriage.getCarriageOfColumn() < myLines.size() -1){
                carriage.setCarriageOfColumn(carriage.getCarriageOfColumn() +1 );
                carriage.setCoordinateX(0);
            }
        }
        public void carriageToLeft(){
            boolean isStartOfText = (carriage.getCarriageOfLine() == 0 && carriage.getCarriageOfColumn() == 0);
            boolean isCanMoveToLeft = (carriage.getCarriageOfLine() > 0);
            if(isStartOfText)
                return;
            else if (isCanMoveToLeft){
                carriage.setCarriageOfLine(carriage.getCarriageOfLine()  - 1);
            }else {
                carriageToUp();
                carriage.setCarriageOfLine(myLines.get(carriage.getCarriageOfColumn()).size());
            }
        }
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Текстовый редактор");
        primaryStage.getIcons().add(new Image("Блокнот.jpg"));
        BorderPane rootNode = new BorderPane();
        myScene = new Scene(rootNode,500,500);
        primaryStage.setScene(myScene);


        canvas = new Canvas();
        graphicsContext = canvas.getGraphicsContext2D();
        ScrollPane myPane = new ScrollPane();
        rootNode.setCenter(myPane);
        myPane.setContent(canvas);
        canvas.widthProperty().bind(myPane.widthProperty());
        canvas.heightProperty().bind(myPane.heightProperty());
        myPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        graphicsContext.setFont(new Font("Times New Roman",14));

        myScene.setOnKeyPressed(new TextListener(this));

        myScene.setOnKeyTyped(new TextListener(this));
        /*myScene.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                paintCanvas();
            }
        });*/


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
