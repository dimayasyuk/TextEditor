package sample;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * Created by Lenovo on 05.03.2018.
 */
public class TextListener implements EventHandler<KeyEvent> {

    private Main main;
    private Panel panel;
    private ScrollPane scrollPane;
    public TextListener(Main main){
        this.main = main;
        panel = main.getMyPanel();
    }

    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            if(event.getCode() == KeyCode.CAPS) {
                return;
            }
            else if(event.getCode() == KeyCode.CONTROL) {
                return;
            }
            else if(event.isControlDown() && event.getCode() == KeyCode.C){
                panel.copy();
                panel.paintCanvas();
            }
            else if(event.isControlDown() && event.getCode() == KeyCode.X){
                panel.cut();
                panel.paintCanvas();
            }
            else if(event.isControlDown() && event.getCode() == KeyCode.V){
                panel.paste();
                panel.paintCanvas();
            }
            else if(event.getCode() == KeyCode.DELETE) {
                panel.delete();
                panel.paintCanvas();
            }
            else if(event.getCode() == KeyCode.BACK_SPACE) {
                panel.backSpace();
                panel.paintCanvas();
            }
            else if (event.getCode() == KeyCode.LEFT) {
                panel.carriageToLeft();
                panel.paintCanvas();
                panel.falseAllSelection();
            }
            else if(event.getCode() == KeyCode.RIGHT) {
                panel.carriageToRight();
                panel.paintCanvas();
                panel.falseAllSelection();
            }
            else if(event.getCode() == KeyCode.UP){
                panel.carriageToUp();
                panel.paintCanvas();
                panel.falseAllSelection();
            }
            else if(event.getCode() == KeyCode.DOWN){
                panel.carriageToDown();
                panel.paintCanvas();
                panel.falseAllSelection();
            }
            else if (event.getCode() == KeyCode.ENTER) {
                panel.newLine();
                panel.paintCanvas();
            } else {
                panel.inputText(event.getText().charAt(0));
                panel.paintCanvas();
            }
        }
        if(event.getEventType() == KeyEvent.KEY_TYPED){
            if(event.getCharacter().equals(" ")){
                panel.inputText( ' ');
                panel.paintCanvas();
            }
            if(panel.getCanvas().getWidth() < panel.getCarriage().getCoordinateX()){
                FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(panel.getFont());
                panel.getCanvas().setWidth(panel.getCanvas().getWidth() + fontMetrics.computeStringWidth(event.getCharacter()) + 3);
            }
            if(panel.getCanvas().getHeight() < panel.getCarriage().getCoordinateY()){
                FontMetrics fontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(panel.getFont());
                int ascent = (int) (fontMetrics.getAscent() + fontMetrics.getLeading() + 3);
                int descent = (int) (fontMetrics.getDescent() + 3);
                panel.getCanvas().setHeight(panel.getCanvas().getHeight() + ascent + descent);
            }
            main.getMyPane().setHvalue(panel.getCarriage().getCoordinateX()/panel.getCanvas().getWidth());
            main.getMyPane().setVvalue(panel.getCarriage().getCoordinateY()/panel.getCanvas().getHeight());
            panel.paintCanvas();
        }
       }
    }
