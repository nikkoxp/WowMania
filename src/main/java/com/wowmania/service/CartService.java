package com.wowmania.service;

import com.wowmania.model.CartItem;
import com.wowmania.model.Listing;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.*;

@Service
@SessionScope
public class CartService {
    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    public void add(Listing listing) {
        items.compute(listing.getId(), (k, v) -> {
            if (v == null) return new CartItem(listing);
            v.increment();
            return v;
        });
    }

    public boolean removeOne(Long listingId) {
        CartItem item = items.get(listingId);
        if (item == null) return false;
        item.decrement();
        if (item.getQuantity() <= 0) {
            items.remove(listingId);
        }
        return true;
    }

    public Collection<CartItem> getItems() { return items.values(); }
    public BigDecimal getTotal() {
        return items.values().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() { items.clear(); }
}
