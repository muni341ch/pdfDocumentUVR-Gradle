package com.documentUVR.repository;

import com.documentUVR.model.PdfEntity;
import com.documentUVR.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPdfDocument(PdfEntity pdfDocument);
}