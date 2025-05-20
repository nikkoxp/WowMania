package com.wowmania.controller;

import com.wowmania.model.Listing;
import com.wowmania.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("/listings")
public class ListingController {
    @Autowired private ListingService listingService;

    @GetMapping
    public String viewListings(
            @RequestParam(value = "q", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Listing> listingPage = listingService.findPage(keyword, pageable);

        model.addAttribute("listings", listingPage.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listingPage.getTotalPages());
        return "listings";
    }

    @GetMapping("/{id}")
    public String viewListingDetails(@PathVariable Long id, Model model) {
        Listing listing = listingService.findById(id);
        if (listing == null) {
            return "redirect:/listings";
        }
        model.addAttribute("listing", listing);
        return "listing_details";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public String showCreateForm(Model model) {
        model.addAttribute("listing", new Listing());
        return "new_listing";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    public String createListing(@ModelAttribute Listing listing, Authentication auth) {
        listingService.save(listing, auth);
        return "redirect:/listings";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteListing(@PathVariable Long id,
                                @RequestHeader("Referer") String referer) {
        listingService.deleteListing(id);
        return "redirect:" + referer;
    }

    @GetMapping("/{id}/buy")
    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    public String buyListing(@PathVariable Long id) {
        // TODO
        return "redirect:/listings";
    }
}
