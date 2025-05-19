package com.wowmania.repository;

import com.wowmania.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByTitleContainingIgnoreCase(String keyword);
    Page<Listing> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
