package com.vinayagam.cctv.repository;

import com.vinayagam.cctv.model.QuoteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<QuoteRequest, Long> {
}
