package sample;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Dimension2D;
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
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import javax.xml.stream.*;
import java.io.*;
import java.util.*;

public class Main extends Application {

       private MenuBar menuBar;
       private ContextMenu editMenu;
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
       private ToggleButton BoldFont;
       private ToggleButton ItalicFont;
       private MouseListener mouseListener = new MouseListener(this);
       private FontWeight fontWeight;
       private FontPosture fontPosture;

       private AnimationTimer animationTimer = new AnimationTimer() {//FIXME animationTimer
           @Override
           public void handle(long now) {
               paintCanvas();
             // graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
           }
       };


    public FontPosture getFontPosture() {
        return fontPosture;
    }

//    public void setFontPosture(FontPosture fontPosture) {
//        this.fontPosture = fontPosture;
//    }

    public FontWeight getFontWeight() {
        return fontWeight;
    }

//    public void setFontWeight(FontWeight fontWeight) {
//        this.fontWeight = fontWeight;
//    }

    public int getFontSize(){
        return (int)font.getSize();
    }
    public GraphicsContext getGraphicsContext(){
        return graphicsContext;
    }

//    public Canvas getCanvas() {
//        return canvas;
//    }

    public Carriage getCarriage() {
        return carriage;
    }

//    public ToggleButton getBoldFont(){
//        return BoldFont;
//    }
//
//    public ToggleButton getItalicFont() {
//        return ItalicFont;
//    }

    private ContextMenu createContextMenu(){

        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");

        ContextMenu contextMenu = new ContextMenu(cut,copy,paste);
        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cut();
                paintCanvas();
            }
        });
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                copy();
                paintCanvas();
            }
        });
        paste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                paste();
                paintCanvas();
            }
        });
        return contextMenu;
    }

    public void openTxtFile(){
                        myLines = new ArrayList<Line>();
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                Line newLine = new Line();
                                char[] charLine = line.toCharArray();
                                for (char ch : charLine) {
                                    newLine.add(ch, graphicsContext.getFont(),Main.this);
                                }
                                myLines.add(newLine);
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
    }
    public void openXmlFile(){
        try {
            Line line = new Line();
            myLines = new ArrayList<Line>();
                XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(file.getName(), new FileInputStream(file));
                while (xmlr.hasNext()){
                    xmlr.next();
                    if(xmlr.isStartElement()){
                        if(xmlr.getLocalName().equals("Line")){
                            line = new Line();
                        }
                        else if(xmlr.getLocalName().equals("Char")){
                            String font = xmlr.getAttributeValue(null,"Font");
                            String fontWeight = xmlr.getAttributeValue(null,"FontWeight");
                            String fontPosture  = xmlr.getAttributeValue(null,"FontPosture");
                            String size = xmlr.getAttributeValue(null,"Size");
                            xmlr.next();
                            line.add(xmlr.getText(),font,size,fontPosture,fontWeight);
                        }
                    }else  if(xmlr.isEndElement()){
                        if(xmlr.getLocalName().equals("Line")){
                            myLines.add(line);
                        }
                    }
                }
            }
        catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MenuBar createMenuBar(){
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
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Text Files", "*.txt","*.xml"));
                    file = fileChooser.showOpenDialog(myStage);
                    if(file!=null) {
                        if (file.getName().endsWith(".txt"))
                            openTxtFile();
                        else
                            openXmlFile();
                    }
                }
            });

        MenuItem saveHow = new MenuItem("Сохранить как",new ImageView("save.png"));
        saveHow.setAccelerator(KeyCombination.keyCombination("shortcut+H"));
        saveHow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                file = fileChooser.showSaveDialog(myStage);
                if(file != null) {
                                    try {
                                        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
                                        XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter(file + ".xml"));
                                        writer.writeStartDocument("UTF-8", "1.0");
                                        writer.writeStartElement("Text");
                                        for(Line line:myLines){
                                            writer.writeStartElement("Line");
                                            for(Char ch:line.getChars()){
                                                writer.writeStartElement("Char");
                                                writer.writeAttribute("Font",ch.getFontName());
                                                writer.writeAttribute("FontWeight",ch.getFontWeight().toString());
                                                writer.writeAttribute("FontPosture",ch.getFontPosture().toString());
                                                writer.writeAttribute("Size",Integer.toString(ch.getFontSize()));
                                                writer.writeCharacters(ch.getCharToString());
                                                writer.writeEndElement();
                                            }
                                            writer.writeEndElement();
                                        }
                                        writer.writeEndElement();
                                        writer.writeEndDocument();
                                        writer.flush();
                                    } catch (XMLStreamException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
        });
            MenuItem save = new MenuItem("Сохранить",new ImageView("save.png"));
            save.setAccelerator(KeyCombination.keyCombination("shortcut+S"));
            save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(file==null) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open Resource File");
                    file = fileChooser.showSaveDialog(myStage);
                }
                if(file != null) {
                    try {
                        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
                        XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter(file + ".xml"));
                        writer.writeStartDocument("UTF-8", "1.0");
                        writer.writeStartElement("Text");
                        for(Line line:myLines){
                            writer.writeStartElement("Line");
                            for(Char ch:line.getChars()){
                                writer.writeStartElement("Char");
                                writer.writeAttribute("Font",ch.getFontName());
                                writer.writeAttribute("FontWeight",ch.getFontWeight().toString());
                                writer.writeAttribute("FontPosture",ch.getFontPosture().toString());
                                writer.writeAttribute("Size",Integer.toString(ch.getFontSize()));
                                writer.writeCharacters(ch.getCharToString());
                                writer.writeEndElement();
                            }
                            writer.writeEndElement();
                        }
                        writer.writeEndElement();
                        writer.writeEndDocument();
                        writer.flush();
                    } catch (XMLStreamException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
//                    FileChooser fileChooser = new FileChooser();
//                    fileChooser.setTitle("Open Resource File");
//                    file = fileChooser.showSaveDialog(myStage);
//                }
//                if(file!=null) {
//                    try (FileWriter writer = new FileWriter(file)) {
//                        for (Line line : myLines) {
//                            String string = "";
//                            for (Char ch : line.getChars()) {
//                                string += ch.getCharToString();
//                            }
//                            writer.write(string + '\n');
//                        }
//                        //writer.flush();
//                    } catch (IOException io) {
//                        io.printStackTrace();
//                    }
//                }
//            }
        });

            MenuItem exit = new MenuItem("Выйти");
            exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });
            exit.setAccelerator(KeyCombination.keyCombination("shortcut+Q"));
            fileMenu.getItems().addAll(newFile,open,save,saveHow,new SeparatorMenuItem(),exit);


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

        menuBar.getMenus().addAll(fileMenu,helpMenu);
            return menuBar;
        }

        private ToolBar createToolBar(){
            ToolBar toolBar = new ToolBar();

            BoldFont = new ToggleButton("Полужирный",new ImageView("bold.png"));
            BoldFont.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            BoldFont.setTooltip(new Tooltip("Полужирный"));

            ItalicFont = new ToggleButton("Курсив",new ImageView("kyrsiv.jpg"));
            ItalicFont.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            ItalicFont.setTooltip(new Tooltip("Курсив"));
            ItalicFont.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(ItalicFont.isSelected()){
                        if(BoldFont.isSelected()){
                            font = Font.font(font.getName(),FontWeight.BOLD,FontPosture.ITALIC,getFontSize());
                            fontPosture = FontPosture.ITALIC;
                            fontWeight = FontWeight.BOLD;
                        }
                        else{
                            font = Font.font(font.getName(),FontWeight.NORMAL,FontPosture.ITALIC,getFontSize());
                            fontPosture = FontPosture.ITALIC;
                            fontWeight = FontWeight.NORMAL;
                        }
                    }else{
                        if(BoldFont.isSelected()){
                            font = Font.font(font.getName(),FontWeight.BOLD,FontPosture.REGULAR,getFontSize());
                            fontPosture = FontPosture.REGULAR;
                            fontWeight = FontWeight.BOLD;
                        }
                        else{
                            font = Font.font(font.getName(),FontWeight.NORMAL,FontPosture.REGULAR,getFontSize());
                            fontPosture = FontPosture.REGULAR;
                            fontWeight = FontWeight.NORMAL;
                        }
                    }
                    changeFontStyle(fontWeight,fontPosture);
                }
            });

            BoldFont.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(BoldFont.isSelected()){
                        if(ItalicFont.isSelected()){
                            font = Font.font(font.getName(),FontWeight.BOLD,FontPosture.ITALIC,getFontSize());
                            fontPosture = FontPosture.ITALIC;
                            fontWeight = FontWeight.BOLD;
                        }
                        else{
                            font = Font.font(font.getName(),FontWeight.BOLD,FontPosture.REGULAR,getFontSize());
                            fontPosture = FontPosture.REGULAR;
                            fontWeight = FontWeight.BOLD;
                        }
                    }else{
                        if(ItalicFont.isSelected()){
                            font = Font.font(font.getName(),FontWeight.NORMAL,FontPosture.ITALIC,getFontSize());
                            fontPosture = FontPosture.ITALIC;
                            fontWeight = FontWeight.NORMAL;
                        }
                        else{
                            font = Font.font(font.getName(),FontWeight.NORMAL,FontPosture.REGULAR,getFontSize());
                            fontPosture = FontPosture.REGULAR;
                            fontWeight = FontWeight.NORMAL;
                        }
                    }
                    changeFontStyle(fontWeight,fontPosture);
                }
            });
            ObservableList<String> Size = FXCollections.observableArrayList("10","15","20");
            fontesSize = new ComboBox<>(Size);
            fontesSize.setValue("10");
            fontesSize.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                        if(BoldFont.isSelected()){
                            if(ItalicFont.isSelected()){
                                font = Font.font(font.getName(),FontWeight.BOLD,FontPosture.ITALIC,Integer.parseInt(fontesSize.getValue()));
                            }
                            else{
                                font = Font.font(font.getName(),FontWeight.BOLD,FontPosture.REGULAR,Integer.parseInt(fontesSize.getValue()));
                            }
                        }else{
                            if(ItalicFont.isSelected()){
                                font = Font.font(font.getName(),FontWeight.NORMAL,FontPosture.ITALIC,Integer.parseInt(fontesSize.getValue()));
                            }
                            else{
                                font = Font.font(font.getName(),FontWeight.NORMAL,FontPosture.REGULAR,Integer.parseInt(fontesSize.getValue()));
                    }
                        changeFontSize(Integer.parseInt(fontesSize.getValue()));
                    }
                }
            });

            List<String> fonts = Font.getFamilies();
            ObservableList<String> fontesList = FXCollections.observableArrayList(fonts);
            fontes = new ComboBox<>(fontesList);
            fontes.setValue("Arial");
            fontes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                        if(BoldFont.isSelected()){
                            if(ItalicFont.isSelected()){
                                font = Font.font(fontes.getValue(),FontWeight.BOLD,FontPosture.ITALIC,font.getSize());
                            }
                            else{
                                font = Font.font(fontes.getValue(),FontWeight.BOLD,FontPosture.REGULAR,font.getSize());
                            }
                        }else{
                            if(ItalicFont.isSelected()){
                                font = Font.font(fontes.getValue(),FontWeight.NORMAL,FontPosture.ITALIC,font.getSize());
                            }
                            else{
                                font = Font.font(fontes.getValue(),FontWeight.NORMAL,FontPosture.REGULAR,font.getSize());
                            }
                    }
                    changeFontFamily(font);
                }
            });

            Button cutButton = new Button("Вырезать",new ImageView("cut.png"));
            cutButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            cutButton.setTooltip(new Tooltip("Вырезать"));
            cutButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    cut();
                }
            });
            Button copyButton = new Button("Копировать",new ImageView("copy.png"));
            copyButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            copyButton.setTooltip(new Tooltip("Копировать"));
            copyButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    copy();
                }
            });
            Button pasteButton = new Button("Вставить",new ImageView("paste.png"));
            pasteButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            pasteButton.setTooltip(new Tooltip("Вставить"));
            pasteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    paste();
                }
            });
            toolBar.getItems().addAll(BoldFont,ItalicFont,cutButton,copyButton,pasteButton,fontesSize,fontes);

            return toolBar;
        }

        public void changeFontSize(int size){
         for (Line line:myLines){
             for (Char ch:line.getChars()){
                 if(ch.isSelect()){
                     ch.setFontSize(size);
                 }
             }
            }
        }
    public void changeFontStyle(FontWeight fontWeight,FontPosture fontPosture){
        for (Line line:myLines){
            for (Char ch:line.getChars()){
                if(ch.isSelect()){
                    ch.setFontStyle(fontWeight,fontPosture);
                }
            }
        }
    }
        public void changeFontFamily(Font font){
        for (Line line:myLines){
            for (Char ch:line.getChars()){
                if(ch.isSelect()){
                    ch.setFontFamily(font);
                }
            }
        }
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
            FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
            int y2 = carriage.getCoordinateY() - (int)(0.8 * fontMetrics.getLineHeight());
            graphicsContext.strokeLine(carriage.getCoordinateX(),carriage.getCoordinateY(),carriage.getCoordinateX(),y2) ;
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            graphicsContext.clearRect(carriage.getCoordinateX(),y2,graphicsContext.getLineWidth(),carriage.getCoordinateY() + 1 - y2);
        }
        public void createInput(){
            carriage = new Carriage();
            carriageTimer();
            Line line = new Line();
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
            boolean setCaret = true;
            String string = "";
            final ClipboardContent clipboardContent = new ClipboardContent();
            for (int y = myLines.size() - 1; y >= 0; y--) {
                if (!setCaret && carriage.getCarriageOfLine() == 0) {
                    string+="\n";
                }
                for (int x = myLines.get(y).getChars().size() - 1; x >= 0; x--) {
                    if (myLines.get(y).getChars().get(x).isSelect()) {
                        if (setCaret) {
                            carriage.setCarriageOfLine(x +1 );
                            carriage.setCarriageOfColumn(y);
                            setCaret = false;
                        }
                        string+=myLines.get(y).getChars().get(x).getCharToString();
                        carriageToLeft();
                    }
                }
            }
            StringBuilder stringBuilder = new StringBuilder(string);
            stringBuilder.reverse();
            clipboardContent.putString(new String(stringBuilder));
            Clipboard.getSystemClipboard().setContent(clipboardContent);
        }
        public void paste(){
            final ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(Clipboard.getSystemClipboard().getString());
            Clipboard.getSystemClipboard().setContent(clipboardContent);
            if(clipboardContent.hasString()){
                String string = clipboardContent.getString();
                deleteSelectedText();
                for(int index = 0;index < string.length();index++) {
                    if (string.charAt(index) == '\n')
                        newLine();
                    else {
                        myLines.get(carriage.getCarriageOfColumn()).add(new Char(string.charAt(index), font,this));
                    carriageToRight();
                    }
                }
            }
        }
        public void cut(){
            boolean setCaret = true;
            String string = "";
            final ClipboardContent clipboardContent = new ClipboardContent();
            for (int y = myLines.size() - 1; y >= 0; y--) {
                if (!setCaret && carriage.getCarriageOfLine() == 0) {
                    deleteChar();
                    string+="\n";
                }
                for (int x = myLines.get(y).getChars().size() - 1; x >= 0; x--) {
                    if (myLines.get(y).getChars().get(x).isSelect()) {
                        if (setCaret) {
                            carriage.setCarriageOfLine(x +1 );
                            carriage.setCarriageOfColumn(y);
                            setCaret = false;
                        }
                        string+=myLines.get(y).getChars().get(x).getCharToString();
                        deleteChar();
                    }
                }
            }
            StringBuilder stringBuilder = new StringBuilder(string);
            stringBuilder.reverse();
            clipboardContent.putString(new String(stringBuilder));
            Clipboard.getSystemClipboard().setContent(clipboardContent);
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
            myLines.get(carriage.getCarriageOfColumn()).add(carriage.getCarriageOfLine(),key,font,this);
            carriageToRight();
        }
        public void paintCanvas(){
           graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
            for(Line line: myLines){
                 line.setMaxHeightOfLine(0);
                 for (Char ch:line.getChars()){
                     Font currFont = Font.font(ch.getFontName(),ch.getFontWeight(),ch.getFontPosture(),ch.getFontSize());
                     FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(currFont);
                     int ascent = (int)(fontMetrics.getAscent() + fontMetrics.getLeading() + 3);
                     int descent = (int)(fontMetrics.getDescent() + 3);
                     line.setMaxHeightOfLine(ascent + descent);
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
                    Font currFont = Font.font(ch.getFontName(),ch.getFontWeight(),ch.getFontPosture(),ch.getFontSize());
                    FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(currFont);
                    graphicsContext.setFont(currFont);
                    if(ch.isSelect()){
                        graphicsContext.setFill(Color.AQUA);
                        graphicsContext.fillRect(x-1,y-0.8*line.getMaxHeightOfLine(),
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
            if(deleteSelectedText());
            deleteChar();
        }
        public void delete(){
            if(deleteSelectedText());
            deleteNextChar();
        }
        public void deleteNextChar(){
            boolean ifEndOfText = ((carriage.getCarriageOfColumn() == myLines.size() - 1) && carriage.getCarriageOfLine() == myLines.get(carriage.getCarriageOfColumn()).size() );
            boolean ifEndOfLine = carriage.getCarriageOfLine() == myLines.get(carriage.getCarriageOfColumn()).getChars().size();
            if(ifEndOfText)
                return;
            else if (ifEndOfLine){
                if (myLines.get(carriage.getCarriageOfColumn()+1).size() != 0){
                    for (Char ch: myLines.get(carriage.getCarriageOfColumn()+1).getChars()){
                        myLines.get(carriage.getCarriageOfColumn()).getChars().add(ch);
                    }
                }
                myLines.remove(carriage.getCarriageOfColumn() + 1);
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
                myLines.remove(carriage.getCarriageOfColumn());
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
                   if(findChar(click,ch)){
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
                     ch.setSelect(findChar(start,end,ch));
                     if(findChar(end,ch)){
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
        public boolean findChar(Point2D point2D,Char ch){
            int y = ch.getCoordinateY();
            int x = ch.getCoordinateX();
            return (x<=point2D.getX() && x+ch.getWeight()>=point2D.getX()&& y-ch.getHeight()<=point2D.getY());
        }
        public boolean findChar(Point2D one,Point2D two,Char ch){
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
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        myStage.setX(primaryScreenBounds.getMinX());
        myStage.setY(primaryScreenBounds.getMinY());
        myStage.setWidth(primaryScreenBounds.getWidth());
        myStage.setHeight(primaryScreenBounds.getHeight());

        canvas = new Canvas();
        graphicsContext = canvas.getGraphicsContext2D();
        ScrollPane myPane = new ScrollPane();
        myPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myPane.setContent(canvas);
        rootNode.setCenter(myPane);
        canvas.widthProperty().bind(myPane.widthProperty());
        canvas.heightProperty().bind(myPane.heightProperty());
        //myPane.setHmax(4);

        font = Font.font("Arial",FontWeight.NORMAL,FontPosture.REGULAR,10);
        fontWeight = FontWeight.NORMAL;
        fontPosture = FontPosture.REGULAR;
        graphicsContext.setFont(font);

        canvas.setOnMouseDragged(mouseListener);
        canvas.setOnMouseClicked(mouseListener);
        canvas.setOnMousePressed(mouseListener);
        canvas.setOnMouseReleased(mouseListener);

        myPane.setOnKeyPressed(new TextListener(this));
        myPane.setOnKeyTyped(new TextListener(this));


        toolBar = createToolBar();
        menuBar = createMenuBar();
        editMenu = createContextMenu();

        rootNode.setBottom(toolBar);
        rootNode.setTop(menuBar);
        myPane.setContextMenu(editMenu);

        primaryStage.show();

        createInput();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
