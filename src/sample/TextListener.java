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
            if (event.getCode() == KeyCode.LEFT)
                main.carriageToLeft();
            else if(event.getCode() == KeyCode.RIGHT)
                main.carriageToRight();
            else if(event.getCode() == KeyCode.UP)
                main.carriageToUp();
            else if(event.getCode() == KeyCode.DOWN)
                main.carriageToDown();
            else if (event.getCode() == KeyCode.ENTER) {
              main.newLine();
            } else {
                main.inputText(event.getText().charAt(0));
            }
        }
        if(event.getEventType() == KeyEvent.KEY_TYPED){
            if(event.getCharacter().equals(" ")){
                main.inputText( ' ');
            }
        }
    /*if(event.getEventType() == KeyEvent.KEY_PRESSED){
        if(event.getCode() == KeyCode.BACK_SPACE) {
            main.getMyLine().delete();
            main.paintCanvas();
        }
        else if(event.isShiftDown()){
            main.getMyLine().add(new Char(event.getText().toUpperCase().charAt(0)));
        main.paintCanvas();
        }
        else {
            main.getMyLine().add(new Char(event.getText().charAt(0)));
        main.paintCanvas();
        }
    }*/
       }
    }
