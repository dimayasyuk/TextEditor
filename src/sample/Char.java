package sample;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Created by Lenovo on 12.03.2018.
 */
public class Char {
    private int numberLine;
    private char symbol;
    private int coordinateX; //
    private int coordinateY;
    private float height;
    private float weight;
    private Font font;
    private FontWeight fontWeight;
    private FontPosture fontPosture;
    private boolean isSelect; //TODO Remove this field

    Char(char symbol,Font font,Main main){
        this.symbol = symbol;
        this.font = font;
        isSelect = false;
        this.fontWeight = main.getMyPanel().getFontWeight();
        this.fontPosture = main.getMyPanel().getFontPosture();
    }
    Char(Char ch){
        symbol = ch.getSymbol();
        font = ch.getFont();
        isSelect = ch.isSelect();
        fontPosture = ch.getFontPosture();
        fontWeight = ch.getFontWeight();
    }
    Char(String key, String font ,String size,String fontsPosture,String fontsWeight){
        this.symbol = key.charAt(0);
        System.out.println(FontWeight.valueOf(fontsWeight));
        System.out.println(FontPosture.valueOf(fontsPosture));
        this.font = Font.font(font,FontWeight.valueOf(fontsWeight),FontPosture.valueOf(fontsPosture),Integer.parseInt(size));
        this.fontPosture = FontPosture.valueOf(fontsPosture);
        this.fontWeight = FontWeight.valueOf(fontsWeight);
    }
    public FontPosture getFontPosture() {
        return fontPosture;
    }

    public FontWeight getFontWeight() {
        return fontWeight;
    }

    public String getFontName(){
        return font.getFamily();
    }
    public int getFontSize(){
        return (int)font.getSize();
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

    public void setFontSize(int size){
        font = Font.font(font.getName(),fontWeight,fontPosture,size);
    }
    public void setFontFamily(Font font){
        this.font = Font.font(font.getName(),fontWeight,fontPosture,this.font.getSize());
    }
    public void setFontStyle(FontWeight fontW,FontPosture fontP){
        fontPosture = fontP;
        fontWeight = fontW;
        font = Font.font(font.getName(),fontWeight,fontPosture,font.getSize());
    }
    public void setNumberLine(int numberLine) {
        this.numberLine = numberLine;
    }
}
