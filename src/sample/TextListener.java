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
    public TextListener(Main main){
        this.main = main;
    }

    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED){
           // main.inputText(event.getText().charAt(0));

        }
        /*if(event.getEventType() == KeyEvent.KEY_TYPED){
            if(event.getCharacter().equals(" ")){
                main.getMyLine().add(new Char(' '));
            }
        }*/
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
