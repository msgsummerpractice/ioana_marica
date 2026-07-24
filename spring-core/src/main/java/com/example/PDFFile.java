package com.example;

import org.springframework.stereotype.Component;

@Component("pdfFile")
public class PDFFile implements DocumentsManager {

    @Override
    public void saveDocument() {
        System.out.println("PDF document saved.");
    }

}
