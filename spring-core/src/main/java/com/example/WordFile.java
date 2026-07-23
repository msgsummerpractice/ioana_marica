package com.example;
import org.springframework.stereotype.Component;

@Component("wordFile")
public class WordFile implements DocumentsManager {

    public void saveDocument() {
        System.out.println("Word document saved.");
    }

}
