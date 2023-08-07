package com.documentUVR.repository;

import com.documentUVR.model.PdfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdfRepository extends JpaRepository<PdfEntity, Long> {

    List<PdfEntity> findByUserName(String username);






}