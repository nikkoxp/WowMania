package com.wowmania.repository;

import com.wowmania.model.Message;
import com.wowmania.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("""
      SELECT m FROM Message m 
       WHERE (m.sender = :me AND m.recipient = :other)
          OR (m.sender = :other AND m.recipient = :me)
       ORDER BY m.sentAt ASC
    """)
    List<Message> findThreadBetween(
            @Param("me") User me,
            @Param("other") User other
    );

    @Query("""
  SELECT m.sender
    FROM Message m
   WHERE m.recipient = :me
   GROUP BY m.sender
   ORDER BY MAX(m.sentAt) DESC
""")
    Page<User> findConversationPartners(
            @Param("me") User me,
            Pageable pageable
    );

    Page<Message> findByRecipientOrderBySentAtDesc(User recipient, Pageable pageable);

    List<Message> findBySenderAndRecipientOrRecipientAndSenderOrderBySentAtAsc(
            User sender, User recipient,
            User recipient2, User sender2
    );
}
