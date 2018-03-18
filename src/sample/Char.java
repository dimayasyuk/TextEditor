package sample;

import javafx.scene.text.Font;

/**
 * Created by Lenovo on 12.03.2018.
 */
public class Char {
    private char symbol;
    private int coordinateX;
    private int coordinateY;
    private float height;
    private float weight;
    private Font font;

    Char(char symbol){
        this.symbol = symbol;
    }

    Char(char symbol,Font font){
        this.symbol = symbol;
        this.font = font;
    }
    Char(Char ch){
        symbol = ch.getSymbol();
    }
    public int getCoordinateX() {
        return coordinateX;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getCharToString(){
       return Character.toString(symbol);
    }

    public Font getFont() {
        return font;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public int getCoordinateY() {
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
