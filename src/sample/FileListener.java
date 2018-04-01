package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Lenovo on 01.04.2018.
 */
public class FileListener implements EventHandler<ActionEvent>{

    private Main main;

    public FileListener(Main main) {
        this.main = main;
    }
    @Override
    public void handle(ActionEvent event) {
        if(((MenuItem)event.getSource()).getText().equals("Новый")){
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(main.getMyStage());
            if (file != null) {
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write("");
                    fileWriter.flush();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                main.getTextDocument().setFile(file);
            }
        }
        else if(((MenuItem)event.getSource()).getText().equals("Открыть")){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.xml"));
            File file = fileChooser.showOpenDialog(main.getMyStage());
            if (file != null) {
                if (file.getName().endsWith(".txt")) {
                    openTxtFile(file);
                }
                else{
                    openXmlFile(file);
                }
                main.getTextDocument().setFile(file);
            }
        }
        else if(((MenuItem)event.getSource()).getText().equals("Сохранить как")){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showSaveDialog(main.getMyStage());
            if (file != null) {
                try {
                    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
                    XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter(file + ".xml"));
                    writer.writeStartDocument("UTF-8", "1.0");
                    writer.writeStartElement("Text");
                    for (Line line : main.getTextDocument().getLines()) {
                        writer.writeStartElement("Line");
                        for (Char ch : line.getChars()) {
                            writer.writeStartElement("Char");
                            writer.writeAttribute("Font", ch.getFontName());
                            writer.writeAttribute("FontWeight", ch.getFontWeight().toString());
                            writer.writeAttribute("FontPosture", ch.getFontPosture().toString());
                            writer.writeAttribute("Size", Integer.toString(ch.getFontSize()));
                            writer.writeCharacters(ch.getCharToString());
                            writer.writeEndElement();
                        }
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                    writer.writeEndDocument();
                    writer.flush();
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            main.getTextDocument().setFile(file);
        }else if(((MenuItem)event.getSource()).getText().equals("Сохранить")){
            if (main.getTextDocument().getFile() == null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showSaveDialog(main.getMyStage());
                main.getTextDocument().setFile(file);
            }
            if (main.getTextDocument().getFile() != null) {
                try {
                    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
                    XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileWriter(main.getTextDocument().getFile() + ".xml"));
                    writer.writeStartDocument("UTF-8", "1.0");
                    writer.writeStartElement("Text");
                    for (Line line : main.getTextDocument().getLines()) {
                        writer.writeStartElement("Line");
                        for (Char ch : line.getChars()) {
                            writer.writeStartElement("Char");
                            writer.writeAttribute("Font", ch.getFontName());
                            writer.writeAttribute("FontWeight", ch.getFontWeight().toString());
                            writer.writeAttribute("FontPosture", ch.getFontPosture().toString());
                            writer.writeAttribute("Size", Integer.toString(ch.getFontSize()));
                            writer.writeCharacters(ch.getCharToString());
                            writer.writeEndElement();
                        }
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                    writer.writeEndDocument();
                    writer.flush();
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(((MenuItem)event.getSource()).getText().equals("Выйти")){
            System.exit(0);
        }
    }
    public void openTxtFile(File file) {
        main.getTextDocument().setLines(new ArrayList<Line>());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                Line newLine = new Line();
                char[] charLine = line.toCharArray();
                for (char ch : charLine) {
                    newLine.add(ch, main.getMyPanel().getGraphicsContext().getFont(), main);
                }
               main.getTextDocument().getLines().add(newLine);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openXmlFile(File file) {
        try {
            Line line = new Line();
            main.getTextDocument().setLines(new ArrayList<Line>());
            XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(file.getName(), new FileInputStream(file));
            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.isStartElement()) {
                    if (xmlr.getLocalName().equals("Line")) {
                        line = new Line();
                    } else if (xmlr.getLocalName().equals("Char")) {
                        String font = xmlr.getAttributeValue(null, "Font");
                        String fontWeight = xmlr.getAttributeValue(null, "FontWeight");
                        String fontPosture = xmlr.getAttributeValue(null, "FontPosture");
                        String size = xmlr.getAttributeValue(null, "Size");
                        xmlr.next();
                        line.add(xmlr.getText(), font, size, fontPosture, fontWeight);
                    }
                } else if (xmlr.isEndElement()) {
                    if (xmlr.getLocalName().equals("Line")) {
                        main.getTextDocument().getLines().add(line);
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
