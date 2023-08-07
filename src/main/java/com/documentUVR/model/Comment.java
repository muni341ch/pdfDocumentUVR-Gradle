package com.documentUVR.model;

import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String user;

    @ManyToOne
    private PdfEntity pdfDocument;

    public Comment() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPdfDocument(PdfEntity pdfDocument) {
        this.pdfDocument = pdfDocument;
    }

    public void setId(long l) {
    }
}