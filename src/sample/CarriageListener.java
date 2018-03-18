package sample;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Lenovo on 17.03.2018.
 */
public class CarriageListener implements EventHandler<KeyEvent> {
    Main main;
    public CarriageListener(Main main){
        this.main = main;
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
        }
    }
}
