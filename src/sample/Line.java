package sample;

import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 03.03.2018.
 */
public class Line {
    List<Char> chars;
    private double coordinateY;
    private float maxHeightOfLine;
    private int maxLength;
    private int numberOfLine;

    public Line(){
        chars = new ArrayList<>();
        maxHeightOfLine = 15;
        maxLength = 0;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public int getNumberOfLine() {
        return numberOfLine;
    }

    public void setNumberOfLine(int numberOfLine) {
        this.numberOfLine = numberOfLine;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void add(Char ch){
        chars.add(new Char(ch));
    }

    public void add(int index,char key,Font font)
    {
        chars.add(index,new Char(key,font));
    }
    public void add(char key,Font font){
        chars.add(new Char(key,font));
    }
    public Line copyOfSubLine(int oneIndex,int twoIndex){
      Line line = new Line();
      for(int i = oneIndex;i<twoIndex;i++){
          line.add(this.chars.get(i));
      }
      return line;
    }


    public void deleteLine(Line line){
        for(int i = 0;i < line.size();i++){
            this.add(line.chars.get(i));
        }
    }

    public int indexOf(Char ch){
        return  chars.indexOf(ch);
    }
    public float getMaxHeightOfLine() {
        return maxHeightOfLine;
    }

    public void setMaxHeightOfLine(float maxHeightOfLine) {
        this.maxHeightOfLine = maxHeightOfLine;
    }

    public void removeCopyOfSubLine(int index){
        for(int i = this.size() - 1;i >= index;i--){
            this.chars.remove(i);
        }
    }
    public String toString(){
        String string = new String();

        for(Char a:chars){
            string+=a.getSymbol();
        }
        return string;
    }
    public List<Char> getChars(){
        return chars;
    }

    public int size(){
        return chars.size();
}

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }
}
