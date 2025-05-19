package com.wowmania.service;

import com.wowmania.model.Listing;
import com.wowmania.model.User;
import com.wowmania.repository.ListingRepository;
import com.wowmania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListingService {
    @Autowired private ListingRepository listingRepo;
    @Autowired private UserRepository userRepo;

    public List<Listing> findAll() {
        return listingRepo.findAll();
    }

    public List<Listing> searchByTitle(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        return listingRepo.findByTitleContainingIgnoreCase(keyword);
    }

    public void save(Listing listing, Authentication auth) {
        User seller = userRepo.findByUsername(auth.getName());
        listing.setSeller(seller);
        listingRepo.save(listing);
    }
}
