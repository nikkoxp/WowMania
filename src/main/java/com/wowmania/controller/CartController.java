package com.wowmania.controller;

import com.wowmania.model.CartItem;
import com.wowmania.model.Listing;
import com.wowmania.service.CartService;
import com.wowmania.service.ListingService;
import com.wowmania.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired CartService cartService;
    @Autowired ListingService listingService;
    @Autowired MessageService messageService;

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, @RequestHeader("Referer") String referer) {
        Listing listing = listingService.findById(id);
        cartService.add(listing);
        return "redirect:" + referer;
    }

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart";
    }

    @PostMapping("/confirm")
    public String confirm(Authentication auth) {
        String buyer = auth.getName();
        for (CartItem item : cartService.getItems()) {
            String seller = item.getListing().getSeller().getUsername();
            String title = item.getListing().getTitle();
            for (int i = 0; i < item.getQuantity(); i++) {
                messageService.notifyPurchase(seller, buyer, title);
            }
        }
        cartService.clear();
        return "redirect:/mailbox";
    }
}
