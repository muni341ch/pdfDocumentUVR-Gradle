package com.documentUVR.exception;

public class PdfDocumentNotFoundException extends RuntimeException {
    public PdfDocumentNotFoundException(String message) {
        super(message);
    }
}