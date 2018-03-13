package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 03.03.2018.
 */
public class Line {
   List<Char> chars;
    private double coordinateY;
    private int indexOfLine;

    public Line(){
        chars = new ArrayList<>();
    }
    public void add(Char ch){
        chars.add(new Char(ch));
    }
    public void add(int index,char key){
        chars.add(index,new Char(key));
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
    public void delete() {
        if (chars.size() > 0) {
            chars.remove(chars.size() - 1);
        }
    }

    public void setIndexOfLine(int indexOfLine) {
        this.indexOfLine = indexOfLine;
    }
}
