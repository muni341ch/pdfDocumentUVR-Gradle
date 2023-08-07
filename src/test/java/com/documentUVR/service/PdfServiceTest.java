package com.documentUVR.service;

import com.documentUVR.exception.PdfDocumentNotFoundException;
import com.documentUVR.model.Comment;
import com.documentUVR.model.PdfEntity;
import com.documentUVR.model.Post;
import com.documentUVR.repository.CommentRepository;
import com.documentUVR.repository.PdfRepository;
import com.documentUVR.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PdfServiceTest {

    @Mock
    private PdfRepository pdfRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private PdfService pdfService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadPdfDocument() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.pdf");
        when(mockFile.getBytes()).thenReturn(new byte[0]);

        String username = "Muni";
        String result = pdfService.uploadPdfDocument(mockFile, username);

        assertEquals("test.pdf - This Document uploaded successfully.", result);
        verify(pdfRepository, times(1)).save(any(PdfEntity.class));
    }

    @Test(expected = RuntimeException.class)
    public void testUploadPdfDocumentIOException() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.pdf");
        when(mockFile.getBytes()).thenThrow(new IOException());

        String username = "Muni";
        pdfService.uploadPdfDocument(mockFile, username);
    }

    @Test
    public void testGetAllPdfDocuments() {
        String username = "Muni";
        List<PdfEntity> mockPdfList = new ArrayList<>();
        PdfEntity mockPdf1 = new PdfEntity();
        mockPdf1.setId(1L);
        mockPdf1.setName("test1.pdf");
        mockPdf1.setData(new byte[0]);
        mockPdf1.setUserName(username);
        PdfEntity mockPdf2 = new PdfEntity();
        mockPdf2.setId(2L);
        mockPdf2.setName("test2.pdf");
        mockPdf2.setData(new byte[0]);
        mockPdf2.setUserName(username);
        mockPdfList.add(mockPdf1);
        mockPdfList.add(mockPdf2);

        when(pdfRepository.findByUserName(username)).thenReturn(mockPdfList);

        List<PdfEntity> result = pdfService.getAllPdfDocuments(username);

        assertEquals(2, result.size());
        assertEquals("test1.pdf", result.get(0).getName());
        assertEquals("test2.pdf", result.get(1).getName());
    }

    @Test
    public void testDeletePdfDocument() {
        Long pdfId = 1L;
        String username = "Muni";
        PdfEntity mockPdf = new PdfEntity();
        mockPdf.setId(pdfId);
        mockPdf.setName("test.pdf");
        mockPdf.setData(new byte[0]);
        mockPdf.setUserName(username);

        when(pdfRepository.findById(pdfId)).thenReturn(Optional.of(mockPdf));

        String result = pdfService.deletePdfDocument(pdfId);

        assertEquals("test.pdf - This Document deleted successfully.", result);
        verify(pdfRepository, times(1)).delete(mockPdf);
    }

    @Test(expected = PdfDocumentNotFoundException.class)
    public void testDeletePdfDocumentNotFound() {
        Long pdfId = 1L;
        when(pdfRepository.findById(pdfId)).thenReturn(Optional.empty());

        pdfService.deletePdfDocument(pdfId);
    }

    @Test
    public void testCreateCommentAndAssociateWithDocument() {
        Long documentId = 1L;
        String username = "Muni";
        Comment commentRequest = new Comment();
        commentRequest.setContent("Testing comment");
        commentRequest.setUser("Muni");

        PdfEntity mockPdf = new PdfEntity();
        mockPdf.setId(documentId);
        mockPdf.setName("test.pdf");
        mockPdf.setData(new byte[0]);
        mockPdf.setUserName(username);

        when(pdfRepository.findById(documentId)).thenReturn(Optional.of(mockPdf));

        Comment savedComment = new Comment();
        savedComment.setId(1L);
        savedComment.setContent(commentRequest.getContent());
        savedComment.setUser(commentRequest.getUser());
        savedComment.setPdfDocument(mockPdf);

        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        String result = pdfService.createCommentAndAssociateWithDocument(documentId, commentRequest);

        assertEquals("Comment created and associated with Document: test.pdf", result);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test(expected = PdfDocumentNotFoundException.class)
    public void testCreateCommentAndAssociateWithDocumentPdfNotFound() {
        Long documentId = 1L;
        Comment commentRequest = new Comment();
        commentRequest.setContent("Testing comment");
        commentRequest.setUser("Muni");

        when(pdfRepository.findById(documentId)).thenReturn(Optional.empty());

        pdfService.createCommentAndAssociateWithDocument(documentId, commentRequest);
    }

    @Test
    public void testCreatePostAndAssociateWithDocument() {
        Long documentId = 1L;
        String username = "Muni";
        Post postRequest = new Post();
        postRequest.setContent("Testing post");
        postRequest.setUser("Muni");

        PdfEntity mockPdf = new PdfEntity();
        mockPdf.setId(documentId);
        mockPdf.setName("test.pdf");
        mockPdf.setData(new byte[0]);
        mockPdf.setUserName(username);

        when(pdfRepository.findById(documentId)).thenReturn(Optional.of(mockPdf));

        Post savedPost = new Post();
        savedPost.setId(1L);
        savedPost.setContent(postRequest.getContent());
        savedPost.setUser(postRequest.getUser());
        savedPost.setPdfDocument(mockPdf);

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        String result = pdfService.createPostAndAssociateWithDocument(documentId, postRequest);

        assertEquals("Post created and associated with Document: test.pdf", result);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test(expected = PdfDocumentNotFoundException.class)
    public void testCreatePostAndAssociateWithDocumentPdfNotFound() {
        Long documentId = 1L;
        Post postRequest = new Post();
        postRequest.setContent("Testing post");
        postRequest.setUser("Muni");

        when(pdfRepository.findById(documentId)).thenReturn(Optional.empty());

        pdfService.createPostAndAssociateWithDocument(documentId, postRequest);
    }

    @Test
    public void testViewPostsAssociatedWithDocument() {
        Long documentId = 1L;
        String username = "Muni";
        PdfEntity mockPdf = new PdfEntity();
        mockPdf.setId(documentId);
        mockPdf.setName("test.pdf");
        mockPdf.setData(new byte[0]);
        mockPdf.setUserName(username);

        Post mockPost1 = new Post();
        mockPost1.setId(1L);
        mockPost1.setContent("Post 1");
        mockPost1.setUser("ABC");
        mockPost1.setPdfDocument(mockPdf);

        Post mockPost2 = new Post();
        mockPost2.setId(2L);
        mockPost2.setContent("Post 2");
        mockPost2.setUser("DEF");
        mockPost2.setPdfDocument(mockPdf);

        List<Post> mockPosts = new ArrayList<>();
        mockPosts.add(mockPost1);
        mockPosts.add(mockPost2);

        when(pdfRepository.findById(documentId)).thenReturn(Optional.of(mockPdf));
        when(postRepository.findByPdfDocument(mockPdf)).thenReturn(mockPosts);

        List<Post> result = pdfService.viewPostsAssociatedWithDocument(documentId);

        assertEquals(2, result.size());
        assertEquals("Post 1", result.get(0).getContent());
        assertEquals("Post 2", result.get(1).getContent());
    }

    @Test(expected = PdfDocumentNotFoundException.class)
    public void testViewPostsAssociatedWithDocumentPdfNotFound() {
        Long documentId = 1L;

        when(pdfRepository.findById(documentId)).thenReturn(Optional.empty());

        pdfService.viewPostsAssociatedWithDocument(documentId);
    }
}