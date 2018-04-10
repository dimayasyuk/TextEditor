package sample;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lenovo on 01.04.2018.
 */
public class Panel {
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private int startCoordinate = 0;
    private Main main;
    private Carriage carriage;
    private Font font;
    private MouseListener mouseListener;
    private FontWeight fontWeight;
    private FontPosture fontPosture;
    private List<Char> selectedChars = new ArrayList<>();

    public Panel(Main main) {
        this.main = main;
        canvas = new Canvas(1580,750);
        graphicsContext = canvas.getGraphicsContext2D();
        createInput();
        mouseListener = new MouseListener(this);
        canvas.setOnMouseDragged(mouseListener);
        canvas.setOnMouseClicked(mouseListener);
        canvas.setOnMousePressed(mouseListener);
        canvas.setOnMouseReleased(mouseListener);
    }

    public Font getFont(){
        return font;
    }

    public Carriage getCarriage() {
        return carriage;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public FontWeight getFontWeight() {
        return fontWeight;
    }

    public FontPosture getFontPosture() {
        return fontPosture;
    }
    public int getFontSize() {
        return (int) font.getSize();
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setFontName(String name){
        if (main.isBoldSelected()) {
            if (main.isBoldSelected()) {
                font = Font.font(name, FontWeight.BOLD, FontPosture.ITALIC, font.getSize());
            } else {
                font = Font.font(name, FontWeight.BOLD, FontPosture.REGULAR, font.getSize());
            }
        } else {
            if (main.isItalicSelected()) {
                font = Font.font(name, FontWeight.NORMAL, FontPosture.ITALIC, font.getSize());
            } else {
                font = Font.font(name, FontWeight.NORMAL, FontPosture.REGULAR, font.getSize());
            }
        }
        changeFontFamily(font);
    }

    public void setFontStyle(){
        if (main.isBoldSelected()){
            if (main.isItalicSelected()) {
                font = Font.font(font.getName(), FontWeight.BOLD, FontPosture.ITALIC, getFontSize());
                fontPosture = FontPosture.ITALIC;
                fontWeight = FontWeight.BOLD;
            } else {
                font = Font.font(font.getName(), FontWeight.BOLD, FontPosture.REGULAR, getFontSize());
                fontPosture = FontPosture.REGULAR;
                fontWeight = FontWeight.BOLD;
            }
        } else {
            if (main.isItalicSelected()) {
                font = Font.font(font.getName(), FontWeight.NORMAL, FontPosture.ITALIC, getFontSize());
                fontPosture = FontPosture.ITALIC;
                fontWeight = FontWeight.NORMAL;
            } else {
                font = Font.font(font.getName(), FontWeight.NORMAL, FontPosture.REGULAR, getFontSize());
                fontPosture = FontPosture.REGULAR;
                fontWeight = FontWeight.NORMAL;
            }
        }
        changeFontStyle(fontWeight,fontPosture);
    }

    public void setFontSize(int size){
        if (main.isBoldSelected()) {
            if (main.isItalicSelected()) {
                font = Font.font(font.getName(), FontWeight.BOLD, FontPosture.ITALIC, size);
            } else {
                font = Font.font(font.getName(), FontWeight.BOLD, FontPosture.REGULAR, size);
            }
        } else {
            if (main.isItalicSelected()) {
                font = Font.font(font.getName(), FontWeight.NORMAL, FontPosture.ITALIC, size);
            } else {
                font = Font.font(font.getName(), FontWeight.NORMAL, FontPosture.REGULAR, size);
            }
            changeFontSize(size);
        }
    }

    public void changeFontSize(int size) {
        for (Line line : main.getTextDocument().getLines()) {
            for (Char ch : line.getChars()) {
                if (selectedChars.contains(ch)) {
                    ch.setFontSize(size);
                }
            }
        }
    }

    public void changeFontStyle(FontWeight fontWeight, FontPosture fontPosture) {
        for (Line line : main.getTextDocument().getLines()) {
            for (Char ch : line.getChars()) {
                if (selectedChars.contains(ch)) {
                    ch.setFontStyle(fontWeight, fontPosture);
                }
            }
        }
    }

    public void changeFontFamily(Font font) {
        for (Line line : main.getTextDocument().getLines()) {
            for (Char ch : line.getChars()) {
                if (selectedChars.contains(ch)) {
                    ch.setFontFamily(font);
                }
            }
        }
    }

    public void carriageTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                paintCarriage();
            }
        }, 500, 1000);
    }

    public void paintCarriage() {
        FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
        int y2 = carriage.getCoordinateY() - (int) (0.8 * fontMetrics.getLineHeight());
        graphicsContext.clearRect(carriage.getCoordinateX(), y2, graphicsContext.getLineWidth(), carriage.getCoordinateY() + 1 - y2);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphicsContext.strokeLine(carriage.getCoordinateX(), carriage.getCoordinateY(), carriage.getCoordinateX(), y2);
    }

    public Point2D checkCarriage(){
        int x = 0;
        if(carriage.getCoordinateX() > main.getMyStage().getWidth()){
            x = carriage.getCoordinateX();
        }
        int y = carriage.getCoordinateY() - main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).getMaxLength();
        return new Point2D(x,y);
    }

    public void createInput() {
        carriage = new Carriage();
        carriageTimer();
        main.setTextDocument(new TextDocument());
        main.getTextDocument().getLines().add(new Line());
        font = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 10);
        graphicsContext.setFont(font);
        fontWeight = FontWeight.NORMAL;
        fontPosture = FontPosture.REGULAR;
    }

    public boolean deleteSelectedText() {
        boolean setCaret = true;
        for (int y = main.getTextDocument().getLines().size() - 1; y >= 0; y--) {
            if (!setCaret && carriage.getCarriageOfLine() == 0) deleteChar();
            for (int x = main.getTextDocument().getLines().get(y).getChars().size() - 1; x >= 0; x--) {
                if (selectedChars.contains(main.getTextDocument().getLines().get(y).getChars().get(x))) {
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

    public void copy() {
        boolean setCaret = true;
        String string = "";
        final ClipboardContent clipboardContent = new ClipboardContent();
        for (int y = main.getTextDocument().getLines().size() - 1; y >= 0; y--) {
            if (!setCaret && carriage.getCarriageOfLine() == 0) {
                string += "\n";
            }
            for (int x = main.getTextDocument().getLines().get(y).getChars().size() - 1; x >= 0; x--) {
                if (selectedChars.contains(main.getTextDocument().getLines().get(y).getChars().get(x))) {
                    if (setCaret) {
                        carriage.setCarriageOfLine(x + 1);
                        carriage.setCarriageOfColumn(y);
                        setCaret = false;
                    }
                    string += main.getTextDocument().getLines().get(y).getChars().get(x).getCharToString();
                    carriageToLeft();
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.reverse();
        clipboardContent.putString(new String(stringBuilder));
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    public void paste() {
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(Clipboard.getSystemClipboard().getString());
        Clipboard.getSystemClipboard().setContent(clipboardContent);
        if (clipboardContent.hasString()) {
            String string = clipboardContent.getString();
            deleteSelectedText();
            for (int index = 0; index < string.length(); index++) {
                if (string.charAt(index) == '\n')
                    newLine();
                else {
                    main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).add(new Char(string.charAt(index), font, main));
                    carriageToRight();
                }
            }
        }
    }

    public void cut() {
        boolean setCaret = true;
        String string = "";
        final ClipboardContent clipboardContent = new ClipboardContent();
        for (int y = main.getTextDocument().getLines().size() - 1; y >= 0; y--) {
            if (!setCaret && carriage.getCarriageOfLine() == 0) {
                deleteChar();
                string += "\n";
            }
            for (int x = main.getTextDocument().getLines().get(y).getChars().size() - 1; x >= 0; x--) {
                if (selectedChars.contains(main.getTextDocument().getLines().get(y).getChars().get(x))) {
                    if (setCaret) {
                        carriage.setCarriageOfLine(x + 1);
                        carriage.setCarriageOfColumn(y);
                        setCaret = false;
                    }
                    string += main.getTextDocument().getLines().get(y).getChars().get(x).getCharToString();
                    deleteChar();
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.reverse();
        clipboardContent.putString(new String(stringBuilder));
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    public void falseAllSelection() {
        selectedChars.clear();
    }

    public void inputText(char key) {
        deleteSelectedText();
        main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).add(carriage.getCarriageOfLine(), key, font, main);
        carriageToRight();
    }

    public void paintCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Line line : main.getTextDocument().getLines()) {
            line.setMaxHeightOfLine(0);
            for (Char ch : line.getChars()) {
                Font currFont = Font.font(ch.getFontName(), ch.getFontWeight(), ch.getFontPosture(), ch.getFontSize());
                FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(currFont);
                int ascent = (int) (fontMetrics.getAscent() + fontMetrics.getLeading() + 3);
                int descent = (int) (fontMetrics.getDescent() + 3);
                line.setMaxHeightOfLine(ascent + descent);
            }
            if (line.getMaxHeightOfLine() == 0) line.setMaxHeightOfLine(15);
        }
        int y = startCoordinate;
        int lineY = -1;
        for (Line line : main.getTextDocument().getLines()) {
            y += line.getMaxHeightOfLine();
            int x = startCoordinate;
            lineY++;
            int letterX = 0;
            for (Char ch : line.getChars()) {
                letterX++;
                Font currFont = Font.font(ch.getFontName(), ch.getFontWeight(), ch.getFontPosture(), ch.getFontSize());
                FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(currFont);
                graphicsContext.setFont(currFont);
                if (selectedChars.contains(ch)) {
                    graphicsContext.setFill(Color.AQUA);
                    graphicsContext.fillRect(x - 1, y - 0.8 * line.getMaxHeightOfLine(),
                            fontMetrics.computeStringWidth(ch.getCharToString()) + 3, line.getMaxHeightOfLine());
                    graphicsContext.setFill(Color.WHITE);

                }
                graphicsContext.strokeText(ch.getCharToString(), x, y);
                ch.setHeight(fontMetrics.getLineHeight());
                ch.setWeight(fontMetrics.computeStringWidth(ch.toString()));
                ch.setCoordinateX(x);
                ch.setCoordinateY(y);
                ch.setNumberLine(lineY);
                x += fontMetrics.computeStringWidth(ch.getCharToString()) + 2;
                if (carriage.getCarriageOfLine() == letterX && carriage.getCarriageOfColumn() == lineY) {
                    carriage.setCoordinateX(x);
                    carriage.setCoordinateY(y);
                }
            }
            line.setCoordinateY(y);
            line.setMaxLength(x);
            line.setNumberOfLine(lineY);
            if (carriage.getCarriageOfLine() == 0 && carriage.getCarriageOfColumn() == lineY) {
                carriage.setCoordinateX(startCoordinate);
                carriage.setCoordinateY(y);
            }
        }
        graphicsContext.setFont(font);
    }

    public void newLine() {
        deleteSelectedText();
        int lost = carriage.getCarriageOfLine();
        Line newLine = main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).copyOfSubLine(carriage.getCarriageOfLine(), main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).size());
        main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).removeCopyOfSubLine(lost);
        main.getTextDocument().getLines().add(carriage.getCarriageOfColumn() + 1, newLine);
        carriage.setCarriageOfLine(0);
        carriageToDown();
    }

    public void backSpace() {
        if (deleteSelectedText()) {
            deleteChar();
        }
    }

    public void delete() {
        if (deleteSelectedText()) {
            deleteNextChar();
        }
    }

    public void deleteNextChar() {
        boolean EndOfText = ((carriage.getCarriageOfColumn() == main.getTextDocument().getLines().size() - 1) && carriage.getCarriageOfLine() == main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).size());
        boolean EndOfLine = carriage.getCarriageOfLine() == main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).getChars().size();
        if (EndOfText) {
            return;
        } else if (EndOfLine) {
            if (main.getTextDocument().getLines().get(carriage.getCarriageOfColumn() + 1).size() != 0) {
                for (Char ch : main.getTextDocument().getLines().get(carriage.getCarriageOfColumn() + 1).getChars()) {
                    main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).getChars().add(ch);
                }
            }
            main.getTextDocument().getLines().remove(carriage.getCarriageOfColumn() + 1);
        } else {
            main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).getChars().remove(carriage.getCarriageOfLine());
        }
    }

    public void deleteChar() {
        boolean StartOfText = (carriage.getCarriageOfColumn() == 0 && carriage.getCarriageOfLine() == 0);
        boolean StartOfLine = (carriage.getCarriageOfLine() == 0);
        if (StartOfText)
            return;
        else if (StartOfLine) {
            carriage.setCarriageOfLine(main.getTextDocument().getLines().get(carriage.getCarriageOfColumn() - 1).size());
            if (main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).size() != 0) {
                for (Char ch : main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).getChars()) {
                    main.getTextDocument().getLines().get(carriage.getCarriageOfColumn() - 1).getChars().add(ch);
                }
            }
            main.getTextDocument().getLines().remove(carriage.getCarriageOfColumn());
            carriageToUp();
        } else if (carriage.getCarriageOfLine() != 0) {
            main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).getChars().remove(carriage.getCarriageOfLine() - 1);
            carriageToLeft();
        }
    }

    public void carriageToDown() {
        boolean moveToDown = main.getTextDocument().getLines().size() - 1 > carriage.getCarriageOfColumn();
        if (moveToDown) {
            carriage.setCarriageOfColumn(carriage.getCarriageOfColumn() + 1);
        }
        boolean nextLineLess = main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).size() < carriage.getCarriageOfLine();
        if (nextLineLess) {
            carriage.setCarriageOfLine(main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).size());
        }
    }

    public void carriageToUp() {
        boolean NotStartLine = (carriage.getCarriageOfColumn() != 0);
        if (NotStartLine)
            carriage.setCarriageOfColumn(carriage.getCarriageOfColumn() - 1);
        boolean ifLineLess = (carriage.getCarriageOfLine() > main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).size());
        if (ifLineLess) {
            carriage.setCarriageOfLine(main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).size());
        }

    }

    public void carriageToRight() {
        boolean NotEndOfLine = carriage.getCarriageOfLine() < main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).size();
        boolean EndText = ((carriage.getCarriageOfColumn() + 1) == main.getTextDocument().getLines().size() && main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).size() == carriage.getCarriageOfLine());
        if (EndText) {
            return;
        } else if (NotEndOfLine)
            carriage.setCarriageOfLine(carriage.getCarriageOfLine() + 1);
        else if (carriage.getCarriageOfColumn() < main.getTextDocument().getLines().size() - 1) {
            carriage.setCarriageOfColumn(carriage.getCarriageOfColumn() + 1);
            carriage.setCarriageOfLine(0);
        }
    }

    public void carriageToLeft() {
        boolean StartOfText = (carriage.getCarriageOfLine() == 0 && carriage.getCarriageOfColumn() == 0);
        boolean CanMoveToLeft = (carriage.getCarriageOfLine() > 0);
        if (StartOfText) {
            return;
        } else if (CanMoveToLeft) {
            carriage.setCarriageOfLine(carriage.getCarriageOfLine() - 1);
        } else {
            carriageToUp();
            carriage.setCarriageOfLine(main.getTextDocument().getLines().get(carriage.getCarriageOfColumn()).size());
        }
    }

    public void clickedMouse(Point2D click) {
        for (Line line : main.getTextDocument().getLines()) {
            checkEndLine(click, line);
            for (Char ch : line.getChars()) {
                if (findChar(click, ch)) {
                    carriage.setCarriageOfColumn(main.getTextDocument().getLines().indexOf(line));
                    carriage.setCarriageOfLine(line.indexOf(ch) + 1);
                }
            }
        }
    }

    public void clickedMouse(Point2D start, Point2D end) {
        falseAllSelection();
        for (Line line : main.getTextDocument().getLines()) {
            checkEndLine(end, line);
            for (Char ch : line.getChars()) {
                if(findChar(start, end, ch)){
                    selectedChars.add(ch);
                }
                if (findChar(end, ch)) {
                    carriage.setCarriageOfColumn(main.getTextDocument().getLines().indexOf(line));
                    carriage.setCarriageOfLine(line.indexOf(ch) + 1);
                }
            }
        }
    }

    public void checkEndLine(Point2D point2D, Line line) {
        boolean PointYMoreThenTextY = line.getCoordinateY() - line.getMaxHeightOfLine() <= point2D.getY();
        if (PointYMoreThenTextY) {
            carriage.setCarriageOfLine(line.getChars().size());
            carriage.setCarriageOfColumn(line.getNumberOfLine());
            if (startCoordinate >= point2D.getX()) {
                carriage.setCarriageOfLine(0);
            }
        }
    }

    public boolean findChar(Point2D point2D, Char ch) {
        int y = ch.getCoordinateY();
        int x = ch.getCoordinateX();
        return (x <= point2D.getX() && x + ch.getWeight() >= point2D.getX() && y - ch.getHeight() <= point2D.getY());
    }

    public boolean findChar(Point2D one, Point2D two, Char ch) {
        int y = ch.getCoordinateY();
        int x = ch.getCoordinateX();
        float height = main.getTextDocument().getLines().get(ch.getNumberLine()).getMaxHeightOfLine();
        Point2D upPoint = (one.getY() < two.getY()) ? one : two;
        Point2D downPoint = (one.getY() < two.getY()) ? two : one;
        Point2D leftPoint = (one.getX() < two.getX()) ? one : two;
        Point2D rightPoint = (one.getX() < two.getX()) ? two : one;
        if (y < downPoint.getY() || y - height > upPoint.getY()) {
            return ((x >= upPoint.getX()) && y - height < upPoint.getY() && y >= upPoint.getY() ||
                    (x <= downPoint.getX()) && y - height < downPoint.getY() && y >= downPoint.getY() ||
                    (y - height >= upPoint.getY() && y < downPoint.getY()));
        } else {
            return (y - height <= leftPoint.getY() && y >= leftPoint.getY()) &&
                    (y - height <= rightPoint.getY() && y >= rightPoint.getY()) &&
                    (x > leftPoint.getX() && x <= rightPoint.getX());
        }
    }
}
