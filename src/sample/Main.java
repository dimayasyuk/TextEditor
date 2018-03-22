package sample;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.*;
import java.util.*;

public class Main extends Application {

       private MenuBar menuBar;
       private  ComboBox<String> fontesSize;
       private ComboBox<String> fontes;
       private ToolBar toolBar;
       private Scene myScene;
       private Canvas canvas;
       private Carriage carriage;
       private GraphicsContext graphicsContext;
       private Font font;
       private int startCoordinate  = 0;
       private List<Line> myLines = new ArrayList<>();
       private Stage myStage;
       private File file;
       private MouseListener mouseListener = new MouseListener(this);
       private AnimationTimer animationTimer = new AnimationTimer() {
           @Override
           public void handle(long now) {
               paintCanvas();
             // graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
           }
       };

    public Scene getScene() {
        return myScene;
    }

    public List<Line> getMyLines(){ return myLines; }

    public GraphicsContext getGraphicsContext(){ graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());return graphicsContext; }

    public Canvas getCanvas(){ return canvas; }
    public void setLines(ArrayList<Line> lines){
        myLines = lines;
    }

    public void setFontSize(double fontSize){
      font = new Font(font.getName(),fontSize);
    }
    public void setFont(String myFont){
        font  = new Font(myFont,font.getSize());
    }
    public Carriage getCarriage() {
        return carriage;
    }

    private MenuBar createMenuBar(){
            MenuBar menuBar = new MenuBar();
            Menu fileMenu = new Menu("_Файл");

        MenuItem newFile = new MenuItem("Новый",new ImageView("save.png"));
        newFile.setAccelerator(KeyCombination.keyCombination("shortcut+N"));
        newFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                file = fileChooser.showOpenDialog(myStage);
                if (file != null) {
                    try (FileWriter fileWriter = new FileWriter(file)) {
                        fileWriter.write("");
                        fileWriter.flush();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

            MenuItem open = new MenuItem("Открыть",new ImageView("open.png"));
            open.setAccelerator(KeyCombination.keyCombination("shortcut+O"));
            open.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Resource File");
                    file = fileChooser.showOpenDialog(myStage);
                    setLines(new ArrayList<Line>());
                    try{
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String line;
                        while((line =reader.readLine() )!= null){
                            Line newLine = new Line();
                            char [] charLine = line.toCharArray();
                            for(char ch:charLine){
                                newLine.add(ch,graphicsContext.getFont());
                            }
                            myLines.add(newLine);
                        }
                        reader.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });

//            MenuItem close = new MenuItem("Закрыть",new ImageView("close.png"));
//            close.setAccelerator(KeyCombination.keyCombination("shortcut+C"));
//            close.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//
//                }
//            });

            MenuItem save = new MenuItem("Сохранить",new ImageView("save.png"));
            save.setAccelerator(KeyCombination.keyCombination("shortcut+S"));
            save.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(file!= null) {
                        try (FileWriter writer = new FileWriter(file)) {
                            for (Line line : myLines) {
                                String string = "";
                                for (Char ch : line.getChars()) {
                                    string += ch.getCharToString();
                                }
                                writer.write(string + '\n');
                            }
                            //writer.flush();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
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
            fileMenu.getItems().addAll(newFile,open,save,new SeparatorMenuItem(),exit);

//            Menu changeMenu = new Menu("Правка");
//
//
//            Menu formatMenu = new Menu("Формат");

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
//            menuBar.getMenus().addAll(fileMenu,changeMenu,formatMenu,helpMenu);
//
        menuBar.getMenus().addAll(fileMenu,helpMenu);
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

            ObservableList<String> Size = FXCollections.observableArrayList("10","15","20");
            fontesSize = new ComboBox<>(Size);
            fontesSize.setValue("10");
            fontesSize.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String size = fontesSize.getValue();
                    System.out.println(size);
                    if(size.equals("10"))
                        setFontSize(10);
                    else if(size.equals("15"))
                        setFontSize(15);
                    else setFontSize(20);
                }
            });

            ObservableList<String> fontesList = FXCollections.observableArrayList("Arial","Times New Roman");
            fontes = new ComboBox<>(fontesList);
            fontes.setValue("Arial");
            fontes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String myFont = fontes.getValue();
                    if(myFont.equals("Arial")){
                        setFont("Arial");
                    } else if(myFont.equals("Times New Roman")){
                        setFont("Times New Roman");
                    }
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
    public void canvasTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
            }
        },0,200);
    }
        public void paintCarriage(){
            FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(graphicsContext.getFont());
            int y2 = carriage.getCoordinateY() + 1 - (int)(0.9*fontMetrics.getLineHeight());
            graphicsContext.strokeLine(carriage.getCoordinateX(),carriage.getCoordinateY() + 1,carriage.getCoordinateX(),y2) ;
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            graphicsContext.clearRect(carriage.getCoordinateX(),y2,graphicsContext.getLineWidth(),carriage.getCoordinateY() +1 - y2);
        }
        public void createInput(){
            carriage = new Carriage();
            carriageTimer();
            Line line = new Line();
            canvasTimer();
            animationTimer.start();
            myLines.add(line);

        }
        public boolean deleteSelectedText(){
            boolean setCaret = true;
            for (int y = myLines.size() - 1; y >= 0; y--) {
                if (!setCaret && carriage.getCarriageOfLine() == 0) deleteChar();
                for (int x = myLines.get(y).getChars().size() - 1; x >= 0; x--) {
                    if (myLines.get(y).getChars().get(x).isSelect()) {
                        if (setCaret) {
                            carriage.setCarriageOfLine(x + 1);
                            carriage.setCarriageOfColumn(y);
                            setCaret = false;
                        }
                        deleteChar();
                    }
                }
            }
            return setCaret;
        }
        public void copy(){
            final ClipboardContent clipboardContent = new ClipboardContent();
            String string = "";
            for(Line line:myLines){
                string = "";
                for(Char ch:line.getChars()){
                    if(ch.isSelect()){
                        string+=ch.getCharToString();
                    }
                }
            }
            clipboardContent.putString(string);
            Clipboard.getSystemClipboard().setContent(clipboardContent);
        }
        public void paste(){

        }
        public void cut(){

        }

        public void falseAllSelection(){
            for(Line line:myLines){
                for (Char ch:line.getChars()){
                    ch.setSelect(false);
                }
            }
        }
        public void inputText(char key){
            deleteSelectedText();
            myLines.get(carriage.getCarriageOfColumn()).add(carriage.getCarriageOfLine(),key,font);
            //graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
            carriageToRight();
        }
        public void paintCanvas(){
            for(Line line: myLines){
                 line.setMaxHeightOfLine(0);
                 for (Char ch:line.getChars()){
                     Font font = ch.getFont();
                     FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
                     line.setMaxHeightOfLine(fontMetrics.getLineHeight());
                 }
                 if (line.getMaxHeightOfLine() == 0) line.setMaxHeightOfLine(15);
            }
            int y = startCoordinate;
            int lineY = -1;
            for(Line line:myLines) {
                y+=line.getMaxHeightOfLine();
                int x = startCoordinate;
                lineY++;
                int letterX = 0;
                for (Char ch : line.getChars()) {
                    letterX++;
                    Font font = ch.getFont();
                    graphicsContext.setFont(font);
                    FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
                    if(ch.isSelect()){
                        graphicsContext.setFill(Color.AQUA);
                        graphicsContext.fillRect(x-2,y-line.getMaxHeightOfLine(),
                                fontMetrics.computeStringWidth(ch.getCharToString()) + 3,line.getMaxHeightOfLine());
                        graphicsContext.setFill(Color.WHITE);

                    }
                    graphicsContext.strokeText(ch.getCharToString(), x, y);
                    ch.setHeight(fontMetrics.getLineHeight());
                    ch.setWeight(fontMetrics.computeStringWidth(ch.toString()));
                    ch.setCoordinateX(x);
                    ch.setCoordinateY(y);
                    ch.setNumberLine(lineY);
                    x += fontMetrics.computeStringWidth(ch.getCharToString())+ 2;
                    if(carriage.getCarriageOfLine()== letterX && carriage.getCarriageOfColumn() == lineY) {
                        carriage.setCoordinateX(x);
                        carriage.setCoordinateY(y);
                    }
                }
                line.setCoordinateY(y);
                line.setMaxLength(x);
                line.setNumberOfLine(lineY);
                if(carriage.getCarriageOfLine()== 0 && carriage.getCarriageOfColumn() == lineY) {
                    carriage.setCoordinateX(startCoordinate);
                    carriage.setCoordinateY(y);
                }
            }
            graphicsContext.setFont(font);
        }
        public void newLine(){
            deleteSelectedText();
            int lost = carriage.getCarriageOfLine();
          Line newLine = myLines.get(carriage.getCarriageOfColumn()).copyOfSubLine(carriage.getCarriageOfLine(),myLines.get(carriage.getCarriageOfColumn()).size());
          myLines.get(carriage.getCarriageOfColumn()).removeCopyOfSubLine(lost);
          myLines.add(carriage.getCarriageOfColumn() + 1,newLine);
          carriage.setCarriageOfLine(0);
          carriageToDown();
        }

        public void backSpace(){
            deleteSelectedText();
            deleteChar();
        }
        public void delete(){
            deleteSelectedText();
            deleteNextChar();
        }
        public void deleteNextChar(){
            boolean ifEndOfText = ((carriage.getCarriageOfColumn() == myLines.size() - 1) && carriage.getCarriageOfLine() == myLines.get(carriage.getCarriageOfColumn()).size() );
            boolean ifEndOfLine = carriage.getCarriageOfLine() == myLines.get(carriage.getCarriageOfColumn()).size() && (myLines.size()>1);
            if(ifEndOfText)
                return;
            else if (ifEndOfLine){
//                myLines.get(carriage.getCarriageOfColumn()).deleteLine(myLines.get(carriage.getCarriageOfColumn()+ 1));
//                myLines.remove(carriage.getCarriageOfColumn() + 1);
                if (myLines.get(carriage.getCarriageOfColumn()+1).size() != 0){
                    for (Char ch: myLines.get(carriage.getCarriageOfColumn()+1).getChars()){
                        myLines.get(carriage.getCarriageOfColumn()).getChars().add(ch);
                    }
                }
            }
            else{
                myLines.get(carriage.getCarriageOfColumn()).getChars().remove(carriage.getCarriageOfLine());
            }
        }
        public void deleteChar(){
            boolean ifStartOfText = (carriage.getCarriageOfColumn() == 0 && carriage.getCarriageOfLine() == 0);
            boolean ifStartOfLine = (carriage.getCarriageOfLine() == 0);
            if(ifStartOfText)
                return;
            else if(ifStartOfLine){
                carriage.setCarriageOfLine(myLines.get(carriage.getCarriageOfColumn()-1).size());
                if (myLines.get(carriage.getCarriageOfColumn()).size() != 0){
                    for (Char ch: myLines.get(carriage.getCarriageOfColumn()).getChars()){
                        myLines.get(carriage.getCarriageOfColumn()-1).getChars().add(ch);
                    }
                }
                myLines.remove(carriage.getCarriageOfColumn() + 1);
                carriageToUp();
            }
            else if(carriage.getCarriageOfLine() != 0){
                myLines.get(carriage.getCarriageOfColumn()).getChars().remove(carriage.getCarriageOfLine() -1 );
                carriageToLeft();
            }
        }
            public void carriageToDown (){
            boolean isMoveToDown = myLines.size() - 1 > carriage.getCarriageOfColumn();
            if(isMoveToDown){
                carriage.setCarriageOfColumn(carriage.getCarriageOfColumn() +1 );
            }
            boolean isNextLineLess = myLines.get(carriage.getCarriageOfColumn()).size() < carriage.getCarriageOfLine();
            if(isNextLineLess){
                carriage.setCarriageOfLine(myLines.get(carriage.getCarriageOfColumn()).size());
            }
        }
            public void carriageToUp(){
                boolean isNotStartLine = (carriage.getCarriageOfColumn() != 0 );
                if(isNotStartLine)
                    carriage.setCarriageOfColumn(carriage.getCarriageOfColumn() - 1);
                boolean ifLineLess = (carriage.getCarriageOfLine() > myLines.get(carriage.getCarriageOfColumn()).size());
                if(ifLineLess){
                    carriage.setCarriageOfLine(myLines.get(carriage.getCarriageOfColumn()).size());
                }

            }
        public void carriageToRight(){
            boolean isNotEndOfLine = carriage.getCarriageOfLine() < myLines.get(carriage.getCarriageOfColumn()).size();
            boolean isEndText = ((carriage.getCarriageOfColumn() + 1)  == myLines.size() && myLines.get(carriage.getCarriageOfColumn()).size() == carriage.getCarriageOfLine());
            if (isEndText) {
                return;
            }else if(isNotEndOfLine)
                carriage.setCarriageOfLine(carriage.getCarriageOfLine() + 1 );
            else if (carriage.getCarriageOfColumn() < myLines.size() -1){
                carriage.setCarriageOfColumn(carriage.getCarriageOfColumn() +1 );
                carriage.setCarriageOfLine(0);
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
        public void clickedMouse(Point2D click){
           for(Line line:myLines){
               checkEndLine(click,line);
               for (Char ch:line.getChars()){
                   if(contains(click,ch)){
                       carriage.setCarriageOfColumn(myLines.indexOf(line));
                       carriage.setCarriageOfLine(line.indexOf(ch) + 1);
                   }
               }
           }
        }
        public void clickedMouse(Point2D start,Point2D end){
             for(Line line:myLines){
                 checkEndLine(end,line);
                 for (Char ch:line.getChars()){
                     ch.setSelect(contains(start,end,ch));
                     if(contains(end,ch)){
                         carriage.setCarriageOfColumn(myLines.indexOf(line));
                         carriage.setCarriageOfLine(line.indexOf(ch) + 1);
                     }
                 }
             }
        }
        public void checkEndLine(Point2D point2D,Line line){
           boolean isPointYMoreThenTextY = line.getCoordinateY() - line.getMaxHeightOfLine()<=point2D.getY();
           if(isPointYMoreThenTextY){
            carriage.setCarriageOfLine(line.getChars().size());
            carriage.setCarriageOfColumn(line.getNumberOfLine());
            if(startCoordinate>=point2D.getX()){
            carriage.setCarriageOfLine(0);
        }
        }
        }
        public boolean contains(Point2D point2D,Char ch){
            int y = ch.getCoordinateY();
            int x = ch.getCoordinateX();
            return (x<=point2D.getX() && x+ch.getWeight()>=point2D.getX()&& y-ch.getHeight()<=point2D.getY());
        }
        public boolean contains(Point2D one,Point2D two,Char ch){
            int y = ch.getCoordinateY();
            int x = ch.getCoordinateX();
            float height = myLines.get(ch.getNumberLine()).getMaxHeightOfLine();
            Point2D upPoint = (one.getY() < two.getY()) ? one : two;
            Point2D downPoint = (one.getY() < two.getY()) ? two : one;
            Point2D leftPoint = (one.getX() < two.getX()) ? one : two;
            Point2D rightPoint = (one.getX() < two.getX()) ? two : one;
            if (y < downPoint.getY() || y-height > upPoint.getY()) {
                return ((x >= upPoint.getX()) && y - height < upPoint.getY() && y >= upPoint.getY() ||
                        (x <= downPoint.getX()) && y - height < downPoint.getY() && y >= downPoint.getY() ||
                        (y - height >= upPoint.getY() && y < downPoint.getY()));
            } else {
                return (y-height <= leftPoint.getY() && y >= leftPoint.getY()) &&
                        (y-height <= rightPoint.getY() && y >= rightPoint.getY()) &&
                        (x > leftPoint.getX() && x <= rightPoint.getX());
            }
        }
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.myStage = primaryStage;
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

        font = new Font("Arial",10);
        graphicsContext.setFont(font);
       canvas.setOnMouseDragged(mouseListener);
       canvas.setOnMouseClicked(mouseListener);
       canvas.setOnMousePressed(mouseListener);
       canvas.setOnMouseReleased(mouseListener);

        myPane.setOnKeyPressed(new TextListener(this));
       myPane.setOnKeyTyped(new TextListener(this));


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
