package sample;

import com.sun.javafx.scene.paint.GradientUtils;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

/**
 * Created by Lenovo on 05.03.2018.
 */
public class MouseListener implements EventHandler<MouseEvent> {
    private Main main;
    private Point2D clickOnMouse;
    public MouseListener(Main main){
        this.main = main;
    }

    @Override
    public void handle(MouseEvent event) {
        if(event.getEventType() == MouseEvent.MOUSE_RELEASED){
            Point2D point2D = new Point2D(event.getX(),event.getY());
            main.clickedMouse(clickOnMouse,point2D);
        }
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED){
            clickOnMouse = new Point2D(event.getX(),event.getY());
        }
        if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
          Point2D point2D = new Point2D(event.getX(),event.getY());
           main.clickedMouse(point2D);
       }
        if (event.getEventType() == MouseEvent.MOUSE_DRAGGED){
            Point2D point2D = new Point2D(event.getX(),event.getY());
            main.clickedMouse(clickOnMouse,point2D);
        }
        //main.paintCanvas();
    }
}
