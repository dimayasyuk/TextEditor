package sample;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by Lenovo on 05.03.2018.
 */
public class MouseListener implements EventHandler<MouseEvent> {
    Main main;
    MouseListener(Main main){
        this.main = main;
    }
    @Override
    public void handle(MouseEvent event) {
       if(event.getEventType() == MouseEvent.MOUSE_CLICKED){

       }
       if (event.getEventType() == MouseEvent.MOUSE_DRAGGED){

       }
    }
}
