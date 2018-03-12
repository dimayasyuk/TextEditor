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

    Main main;

    TextListener(Main main){
        this.main = main;
    }

    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_TYPED){
            if(event.getCharacter().equals(" ")){
                main.getMyLine().add(new Char(' '));
            }
        }
    if(event.getEventType() == KeyEvent.KEY_PRESSED){
        if(event.getCode() == KeyCode.BACK_SPACE) {
            main.getMyLine().delete();
        }
        else if(event.isShiftDown()){
            main.getMyLine().add(new Char(event.getText().toUpperCase().charAt(0)));
        }
        else {
            main.getMyLine().add(new Char(event.getText().charAt(0)));
        }
        main.getGraphicsContext().clearRect(0,0,main.getCanvas().getWidth(),main.getCanvas().getHeight());
        main.getGraphicsContext().strokeText(main.getMyLine().toString(),10,10);
    }
       }
    }
