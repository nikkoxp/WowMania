package com.wowmania.repository;

import com.wowmania.model.Message;
import com.wowmania.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByRecipientOrderBySentAtDesc(User recipient, Pageable pageable);

    List<Message> findBySenderAndRecipientOrRecipientAndSenderOrderBySentAtAsc(
            User sender, User recipient,
            User recipient2, User sender2
    );
}
