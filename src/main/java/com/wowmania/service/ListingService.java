package com.wowmania.service;

import com.wowmania.model.Listing;
import com.wowmania.model.User;
import com.wowmania.repository.ListingRepository;
import com.wowmania.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ListingService {
    @Autowired private ListingRepository listingRepo;
    @Autowired private UserRepository userRepo;

    @Transactional
    public boolean decrementInventory(Long id) {
        Listing l = listingRepo.findById(id).orElse(null);
        if (l == null || l.getQuantity() <= 0) return false;
        l.setQuantity(l.getQuantity() - 1);
        listingRepo.save(l);
        return true;
    }

    @Transactional
    public void incrementInventory(Long id) {
        Listing l = listingRepo.findById(id).orElse(null);
        if (l != null) {
            l.setQuantity(l.getQuantity() + 1);
            listingRepo.save(l);
        }
    }


    public List<Listing> findAll() {
        return listingRepo.findAll();
    }

    public List<Listing> searchByTitle(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        return listingRepo.findByTitleContainingIgnoreCase(keyword);
    }

    public Listing findById(Long id) {
        return listingRepo.findById(id).orElse(null);
    }

    public Page<Listing> findPage(String keyword, Pageable pageable) {
        String kw = (keyword == null ? "" : keyword);
        return listingRepo.findByQuantityGreaterThanAndTitleContainingIgnoreCase(
                0, kw, pageable
        );
    }



    public void save(Listing listing, Authentication auth) {
        User seller = userRepo.findByUsername(auth.getName());
        listing.setSeller(seller);
        listingRepo.save(listing);
    }
}
