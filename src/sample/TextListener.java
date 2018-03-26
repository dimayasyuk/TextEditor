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
    Carriage carriage;
    public TextListener(Main main){
        this.main = main;
        carriage = main.getCarriage();
    }

    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            if(event.getCode() == KeyCode.CAPS)
                return;
            else if(event.getCode() == KeyCode.CONTROL)
                return;
            else if(event.isControlDown() && event.getCode() == KeyCode.C){
                main.copy();
            }
            else if(event.isControlDown() && event.getCode() == KeyCode.X){
                main.cut();
            }
            else if(event.isControlDown() && event.getCode() == KeyCode.V){
                main.paste();
            }
            else if(event.getCode() == KeyCode.DELETE)
                main.delete();
            else if(event.getCode() == KeyCode.BACK_SPACE)
                main.backSpace();
            else if (event.getCode() == KeyCode.LEFT) {
                main.carriageToLeft();
                main.falseAllSelection();
            }
            else if(event.getCode() == KeyCode.RIGHT) {
                main.carriageToRight();
                main.falseAllSelection();
            }
            else if(event.getCode() == KeyCode.UP){
                main.carriageToUp();
                main.falseAllSelection();
            }
            else if(event.getCode() == KeyCode.DOWN){
                main.carriageToDown();
                main.falseAllSelection();
            }
            else if (event.getCode() == KeyCode.ENTER) {
              main.newLine();
            } else {
                main.inputText(event.getText().charAt(0));
            }
            main.paintCanvas();
        }
        if(event.getEventType() == KeyEvent.KEY_TYPED){
            if(event.getCharacter().equals(" ")){
                main.inputText( ' ');
            }
            main.paintCanvas();
        }
       }
    }
