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
    private int indexOfLine;
    private float maxHightOfLine;

    public Line(){
        chars = new ArrayList<>();
    }

    public void add(Char ch){
        chars.add(new Char(ch));
    }

    public void add(int index,char key,Font font)
    {
        chars.add(index,new Char(key,font));
    }
    public Line copyOfSubLine(int oneIndex,int twoIndex){
      Line line = new Line();
      for(int i = oneIndex;i<twoIndex;i++){
          line.add(this.chars.get(i));
      }
      return line;
    }

    public float getMaxHightOfLine() {
        return maxHightOfLine;
    }

    public void setMaxHightOfLine(float maxHightOfLine) {
        this.maxHightOfLine = maxHightOfLine;
    }

    public void removeCopyOfSubLine(int index){
        for(int i = this.size() - 1;i > index;i--){
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
    public void setIndexOfLine(int indexOfLine) {
        this.indexOfLine = indexOfLine;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }
}
