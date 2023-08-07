package com.documentUVR.controller;

import com.documentUVR.model.Comment;
import com.documentUVR.model.PdfEntity;
import com.documentUVR.model.Post;
import com.documentUVR.service.PdfService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPdfDocument(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
        String response = pdfService.uploadPdfDocument(file, username);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @GetMapping("/view")
    public ResponseEntity<?> getAllPdfDocuments(@RequestParam("username") String username) {
        List<PdfEntity> documents = pdfService.getAllPdfDocuments(username);
        return ResponseEntity.ok(documents);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePdfDocument(@PathVariable Long id) {
        String response = pdfService.deletePdfDocument(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-comment")
    public ResponseEntity<?> createCommentAndAssociateWithDocument(@RequestParam("documentId") Long documentId, @RequestBody Comment commentRequest) {
        String response = pdfService.createCommentAndAssociateWithDocument(documentId, commentRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-post")
    public ResponseEntity<?> createPostAndAssociateWithDocument(@RequestParam("documentId") Long documentId, @RequestBody Post postRequest) {
        String response = pdfService.createPostAndAssociateWithDocument(documentId, postRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view-posts")
    public ResponseEntity<?> viewPostsAssociatedWithDocument(@RequestParam("documentId") Long documentId) {
        List<Post> posts = pdfService.viewPostsAssociatedWithDocument(documentId);
        return ResponseEntity.ok(posts);
    }
}