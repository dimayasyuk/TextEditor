package sample;

import javafx.scene.text.Font;

/**
 * Created by Lenovo on 12.03.2018.
 */
public class Char {
    private char symbol;
    private double coordinateX;
    private double coordinateY;
    private Font font;

    Char(char symbol){
        this.symbol = symbol;
    }
    Char(Char ch){
        symbol = ch.getSymbol();
    }
    public double getCoordinateX() {
        return coordinateX;
    }

    public Font getFont() {
        return font;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public char getSymbol(){
        return symbol;
    }
}
