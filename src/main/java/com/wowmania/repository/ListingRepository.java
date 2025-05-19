package com.wowmania.repository;

import com.wowmania.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByTitleContainingIgnoreCase(String keyword);
}
