package com.wowmania.controller;

import com.wowmania.model.Listing;
import com.wowmania.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/listings")
public class ListingController {
    @Autowired private ListingService listingService;

    @GetMapping
    public String viewListings(@RequestParam(value = "q", required = false) String keyword, Model model) {
        model.addAttribute("listings", listingService.searchByTitle(keyword));
        model.addAttribute("keyword", keyword);
        return "listings";
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

    @GetMapping("/{id}/buy")
    @PreAuthorize("hasAnyRole('BUYER','ADMIN')")
    public String buyListing(@PathVariable Long id) {
        // TODO
        return "redirect:/listings";
    }
}
