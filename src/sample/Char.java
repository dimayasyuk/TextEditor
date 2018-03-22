package sample;

import javafx.scene.text.Font;

/**
 * Created by Lenovo on 12.03.2018.
 */
public class Char {
    private int numberLine;
    private char symbol;
    private int coordinateX;
    private int coordinateY;
    private float height;
    private float weight;
    private Font font;
    private boolean isSelect;


    Char(char symbol,Font font){
        this.symbol = symbol;
        this.font = font;
        isSelect = false;
    }
    Char(Char ch){
        symbol = ch.getSymbol();
        font = ch.getFont();
        isSelect = ch.isSelect();
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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getNumberLine() {
        return numberLine;
    }

    public void setNumberLine(int numberLine) {
        this.numberLine = numberLine;
    }
}
