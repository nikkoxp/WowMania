package com.wowmania.service;

import com.wowmania.model.Message;
import com.wowmania.model.User;
import com.wowmania.repository.MessageRepository;
import com.wowmania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired private MessageRepository msgRepo;
    @Autowired private UserRepository userRepo;

    public Page<Message> getInbox(User me, int page) {
        Pageable pg = PageRequest.of(page, 10, Sort.by("sentAt").descending());
        return msgRepo.findByRecipientOrderBySentAtDesc(me, pg);
    }

    public List<Message> getThread(User me, String otherUsername) {
        User other = userRepo.findByUsername(otherUsername);
        return msgRepo.findBySenderAndRecipientOrRecipientAndSenderOrderBySentAtAsc(
                me, other, other, me
        );
    }

    public void sendMessage(Authentication auth, String toUsername, String content) {
        User sender = userRepo.findByUsername(auth.getName());
        User recipient = userRepo.findByUsername(toUsername);
        Message m = new Message();
        m.setSender(sender);
        m.setRecipient(recipient);
        m.setContent(content);
        m.setSentAt(LocalDateTime.now());
        msgRepo.save(m);
    }

    public void notifyPurchase(String sellerUsername, String buyerUsername, String listingTitle) {
        User seller = userRepo.findByUsername(sellerUsername);
        User buyer = userRepo.findByUsername(buyerUsername);
        Message m = new Message();
        m.setSender(buyer);
        m.setRecipient(seller);
        m.setSentAt(LocalDateTime.now());
        m.setContent("User “" + buyerUsername + "” bought your product: “" + listingTitle + "”");
        msgRepo.save(m);
    }
}
