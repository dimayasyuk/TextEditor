package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    private Panel myPanel;
    private MenuBar menuBar;
    private ContextMenu editMenu;
    private ComboBox<String> fontesSize;
    private ComboBox<String> fontes;
    private ToolBar toolBar;
    private Scene myScene;
    private TextDocument textDocument;
    private Stage myStage;
    private ToggleButton BoldFont;
    private ToggleButton ItalicFont;
    private boolean italicSelected = false;
    private boolean boldSelected = false;
    private FileListener fileListener = new FileListener(this);


    public TextDocument getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(TextDocument textDocument){
        this.textDocument = textDocument;
    }

    public Stage getMyStage(){
        return  myStage;
    }
    public Panel getMyPanel(){
        return myPanel;
    }

    public boolean isBoldSelected() {
        return boldSelected;
    }

    public boolean isItalicSelected() {
        return italicSelected;
    }

    public void setBoldSelected(boolean boldSelected) {
        this.boldSelected = boldSelected;
    }
    public void setItalicSelected(boolean italicSelected){
        this.italicSelected = italicSelected;
    }

    private ContextMenu createContextMenu() {

        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");

        ContextMenu contextMenu = new ContextMenu(cut, copy, paste);
        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.cut();
                myPanel.paintCanvas();
            }
        });
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.copy();
                myPanel.paintCanvas();
            }
        });
        paste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.paste();
                myPanel.paintCanvas();
            }
        });
        return contextMenu;
    }


    public MenuItem createMenuItem(String text,String image,String combination){
        MenuItem menuItem = new MenuItem(text,new ImageView(image));
        menuItem.setAccelerator(KeyCombination.keyCombination(combination));
        menuItem.setOnAction(fileListener);
        return menuItem;
    }

    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("_Файл");

        MenuItem newFile = createMenuItem("Новый", "new.png","shortcut+N");
        MenuItem open = createMenuItem("Открыть", "open.png","shortcut+O");
        MenuItem saveHow = createMenuItem("Сохранить как","savehow.jpg","shortcut+H");
        MenuItem save = createMenuItem("Сохранить", "save.png","shortcut+S");
        MenuItem exit = createMenuItem("Выйти","exit.jpg","shortcut+Q");
        fileMenu.getItems().addAll(newFile, open, save, saveHow, new SeparatorMenuItem(), exit);

        Menu correctMenu = new Menu("Правка");
        MenuItem cut = new MenuItem("Вырезать",new ImageView("cut.png"));
        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.cut();
                myPanel.paintCanvas();
            }
        });
        MenuItem copy = new MenuItem("Копировать",new ImageView("copy.png"));
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.copy();
                myPanel.paintCanvas();
            }
        });
        MenuItem paste = new MenuItem("Вставить",new ImageView("paste.png"));
        paste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.paste();
                myPanel.paintCanvas();
            }
        });
        correctMenu.getItems().addAll(cut,copy,paste);

        Menu formatMenu = new Menu("Формат");
        Menu fontsSize  = new Menu("Размер шрифта");
        RadioMenuItem smallSize = new RadioMenuItem("10");
        RadioMenuItem mediumSize = new RadioMenuItem("15");
        RadioMenuItem bigSize = new RadioMenuItem("20");
        ToggleGroup sizeGroup = new ToggleGroup();
        smallSize.setToggleGroup(sizeGroup);
        mediumSize.setToggleGroup(sizeGroup);
        bigSize.setToggleGroup(sizeGroup);
        smallSize.setSelected(true);
        fontsSize.getItems().addAll(smallSize,mediumSize,bigSize);
        formatMenu.getItems().addAll(fontsSize);

        Menu helpMenu = new Menu("Помощь");
        MenuItem help = new MenuItem("О программе", new ImageView("info.png"));
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

        menuBar.getMenus().addAll(fileMenu,correctMenu,formatMenu,helpMenu);
        return menuBar;
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();

        BoldFont = new ToggleButton("Полужирный", new ImageView("bold.png"));
        BoldFont.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        BoldFont.setTooltip(new Tooltip("Полужирный"));
        BoldFont.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (BoldFont.isSelected()) {
                    boldSelected = true;
                } else {
                    boldSelected = false;
                }
                myPanel.setFontStyle();
            }
        });

        ItalicFont = new ToggleButton("Курсив", new ImageView("kyrsiv.jpg"));
        ItalicFont.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        ItalicFont.setTooltip(new Tooltip("Курсив"));
        ItalicFont.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (ItalicFont.isSelected()) {
                    italicSelected = true;
                }else{
                    italicSelected = false;
                }
                myPanel.setFontStyle();
            }
        });

        ObservableList<String> Size = FXCollections.observableArrayList("10", "15", "20");
        fontesSize = new ComboBox<>(Size);
        fontesSize.setValue("10");
        fontesSize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.setFontSize(Integer.parseInt(fontesSize.getValue()));
            }
        });

        List<String> fonts = Font.getFamilies();
        ObservableList<String> fontesList = FXCollections.observableArrayList(fonts);
        fontes = new ComboBox<>(fontesList);
        fontes.setValue("Arial");
        fontes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.setFontName(fontes.getValue());
            }
        });

        Button cutButton = new Button("Вырезать", new ImageView("cut.png"));
        cutButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        cutButton.setTooltip(new Tooltip("Вырезать"));
        cutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.cut();
            }
        });
        Button copyButton = new Button("Копировать", new ImageView("copy.png"));
        copyButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        copyButton.setTooltip(new Tooltip("Копировать"));
        copyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.copy();
            }
        });
        Button pasteButton = new Button("Вставить", new ImageView("paste.png"));
        pasteButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        pasteButton.setTooltip(new Tooltip("Вставить"));
        pasteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myPanel.paste();
            }
        });
        toolBar.getItems().addAll(BoldFont, ItalicFont, cutButton, copyButton, pasteButton, fontesSize, fontes);

        return toolBar;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.myStage = primaryStage;
        primaryStage.setTitle("Текстовый редактор");
        primaryStage.getIcons().add(new Image("Блокнот.jpg"));
        BorderPane rootNode = new BorderPane();
        myScene = new Scene(rootNode, 500, 500);
        primaryStage.setScene(myScene);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        myStage.setX(primaryScreenBounds.getMinX());
        myStage.setY(primaryScreenBounds.getMinY());
        myStage.setWidth(primaryScreenBounds.getWidth());
        myStage.setHeight(primaryScreenBounds.getHeight());
        myPanel = new Panel(this);

        ScrollPane myPane = new ScrollPane(myPanel.getCanvas());
        myPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        myPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        rootNode.setCenter(myPane);


        myPane.setOnKeyPressed(new TextListener(this));
        myPane.setOnKeyTyped(new TextListener(this));


        toolBar = createToolBar();
        menuBar = createMenuBar();
        editMenu = createContextMenu();

        rootNode.setBottom(toolBar);
        rootNode.setTop(menuBar);
        myPane.setContextMenu(editMenu);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
