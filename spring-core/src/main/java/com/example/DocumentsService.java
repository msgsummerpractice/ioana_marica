package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DocumentsService {

    private DocumentsManager documentsManager;

    @Autowired
    public DocumentsService(@Qualifier("wordFile") DocumentsManager documentsManager) {
        this.documentsManager = documentsManager;
    }

    public void processDocument() {
        documentsManager.saveDocument();
    }

}
