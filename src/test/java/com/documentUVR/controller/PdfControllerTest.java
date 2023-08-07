package com.documentUVR.controller;

import com.documentUVR.model.Comment;
import com.documentUVR.model.PdfEntity;
import com.documentUVR.model.Post;
import com.documentUVR.service.PdfService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class PdfControllerTest {

    @Mock
    private PdfService pdfService;

    @InjectMocks
    private PdfController pdfController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadPdfDocument() {
        MultipartFile mockFile = new MockMultipartFile("test.pdf", new byte[0]);
        String username = "Muni";
        when(pdfService.uploadPdfDocument(any(MultipartFile.class), eq(username)))
                .thenReturn("test.pdf - This Document uploaded successfully.");

        ResponseEntity<?> response = pdfController.uploadPdfDocument(mockFile, username);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("test.pdf - This Document uploaded successfully.", response.getBody());
    }

    @Test
    public void testGetAllPdfDocuments() {
        String username = "Muni";
        List<PdfEntity> mockDocuments = new ArrayList<>();
        when(pdfService.getAllPdfDocuments(eq(username))).thenReturn(mockDocuments);

        ResponseEntity<?> response = pdfController.getAllPdfDocuments(username);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockDocuments, response.getBody());
    }

    @Test
    public void testDeletePdfDocument() {
        Long pdfId = 1L;
        when(pdfService.deletePdfDocument(eq(pdfId)))
                .thenReturn("test.pdf - This Document deleted successfully.");

        ResponseEntity<?> response = pdfController.deletePdfDocument(pdfId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("test.pdf - This Document deleted successfully.", response.getBody());
    }

    @Test
    public void testCreateCommentAndAssociateWithDocument() {
        Long documentId = 1L;
        Comment commentRequest = new Comment();
        commentRequest.setContent("Testing comment");
        commentRequest.setUser("Muni");
        when(pdfService.createCommentAndAssociateWithDocument(eq(documentId), any(Comment.class)))
                .thenReturn("Comment created and associated with Document: test.pdf");

        ResponseEntity<?> response = pdfController.createCommentAndAssociateWithDocument(documentId, commentRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Comment created and associated with Document: test.pdf", response.getBody());
    }

    @Test
    public void testCreatePostAndAssociateWithDocument() {
        Long documentId = 1L;
        Post postRequest = new Post();
        postRequest.setContent("Testing post");
        postRequest.setUser("Muni");
        when(pdfService.createPostAndAssociateWithDocument(eq(documentId), any(Post.class)))
                .thenReturn("Post created and associated with Document: test.pdf");

        ResponseEntity<?> response = pdfController.createPostAndAssociateWithDocument(documentId, postRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Post created and associated with Document: test.pdf", response.getBody());
    }

    @Test
    public void testViewPostsAssociatedWithDocument() {
        Long documentId = 1L;
        List<Post> mockPosts = new ArrayList<>();
        when(pdfService.viewPostsAssociatedWithDocument(eq(documentId))).thenReturn(mockPosts);

        ResponseEntity<?> response = pdfController.viewPostsAssociatedWithDocument(documentId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPosts, response.getBody());
    }
}
