package com.example;
import org.springframework.stereotype.Component;

@Component("pdfFile")
public class PDFFile implements DocumentsManager {

    public void saveDocument() {
        System.out.println("PDF document saved.");
    }

}
