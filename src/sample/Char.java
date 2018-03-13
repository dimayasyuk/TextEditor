package sample;

import javafx.scene.text.Font;

/**
 * Created by Lenovo on 12.03.2018.
 */
public class Char {
    private char symbol;
    private int coordinateX;
    private int coordinateY;
    private int height;
    private Font font;

    Char(char symbol){
        this.symbol = symbol;
    }
    Char(Char ch){
        symbol = ch.getSymbol();
    }
    public int getCoordinateX() {
        return coordinateX;
    }

    public String getCharToString(){
       return Character.toString(symbol);
    }

    public Font getFont() {
        return font;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCoordinateY(int coordinateY) {
        return coordinateY;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public char getSymbol(){
        return symbol;
    }
}
