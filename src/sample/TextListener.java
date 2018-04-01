package sample;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * Created by Lenovo on 05.03.2018.
 */
public class TextListener implements EventHandler<KeyEvent> {

    private Main main;
    private Panel panel;
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
                panel.falseAllSelection();
                panel.paintCanvas();
            }
            else if(event.getCode() == KeyCode.RIGHT) {
                panel.carriageToRight();
                panel.falseAllSelection();
                panel.paintCanvas();
            }
            else if(event.getCode() == KeyCode.UP){
                panel.carriageToUp();
                panel.falseAllSelection();
                panel.paintCanvas();
            }
            else if(event.getCode() == KeyCode.DOWN){
                panel.carriageToDown();
                panel.falseAllSelection();
                panel.paintCanvas();
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
            }
        }
       }
    }
