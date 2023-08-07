package com.documentUVR.service;

import com.documentUVR.exception.PdfDocumentNotFoundException;
import com.documentUVR.model.Comment;
import com.documentUVR.model.PdfEntity;
import com.documentUVR.model.Post;
import com.documentUVR.repository.CommentRepository;
import com.documentUVR.repository.PdfRepository;
import com.documentUVR.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public String uploadPdfDocument(MultipartFile file, String username) {
        try {
            PdfEntity pdfModel = new PdfEntity();
            pdfModel.setName(file.getOriginalFilename());
            pdfModel.setData(file.getBytes());
            pdfModel.setUserName(username);
            System.out.println(pdfModel.toString());
            pdfRepository.save(pdfModel);
            return pdfModel.getName() + " - This Document uploaded successfully.";
        } catch (IOException e) {
            throw new RuntimeException("Error uploading the Pdf document.", e);
        }
    }

    public List<PdfEntity> getAllPdfDocuments(String username) {
        return pdfRepository.findByUserName(username);
    }

    public String deletePdfDocument(Long id) {
        PdfEntity pdfDocument = pdfRepository.findById(id)
                .orElseThrow(() -> new PdfDocumentNotFoundException("Pdf Document not found."));

        pdfRepository.delete(pdfDocument);
        return pdfDocument.getName() + " - This Document deleted successfully.";
    }

    public String createCommentAndAssociateWithDocument(Long documentId, Comment commentRequest) {
        PdfEntity pdfDocument = pdfRepository.findById(documentId)
                .orElseThrow(() -> new PdfDocumentNotFoundException("Pdf Document not found."));

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setUser(commentRequest.getUser());
        comment.setPdfDocument(pdfDocument);
        commentRepository.save(comment);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<Comment> requestEntity = new HttpEntity<>(commentRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange("https://jsonplaceholder.typicode.com/comments", HttpMethod.POST, requestEntity, String.class);
        String responseBody = response.getBody();

        return "Comment created and associated with Document: " + pdfDocument.getName();
    }

    public String createPostAndAssociateWithDocument(Long documentId, Post postRequest) {
        PdfEntity pdfDocument = pdfRepository.findById(documentId)
                .orElseThrow(() -> new PdfDocumentNotFoundException("Pdf Document not found."));

        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setUser(postRequest.getUser());
        post.setPdfDocument(pdfDocument);
        postRepository.save(post);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<Post> requestEntity = new HttpEntity<>(postRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange("https://jsonplaceholder.typicode.com/posts", HttpMethod.POST, requestEntity, String.class);
        String responseBody = response.getBody();

        return "Post created and associated with Document: " + pdfDocument.getName();
    }

    public List<Post> viewPostsAssociatedWithDocument(Long documentId) {
        PdfEntity pdfDocument = pdfRepository.findById(documentId)
                .orElseThrow(() -> new PdfDocumentNotFoundException("Pdf Document not found."));

        return postRepository.findByPdfDocument(pdfDocument);
    }
}