package com.wowmania.controller;

import com.wowmania.model.Message;
import com.wowmania.model.User;
import com.wowmania.service.MessageService;
import com.wowmania.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mailbox")
public class MailboxController {
    @Autowired private MessageService msgService;
    @Autowired private UserService userService;

    @GetMapping
    public String inbox(
            @RequestParam(value="page", defaultValue="0") int page,
            Authentication auth, Model model
    ) {
        User me = userService.findByUsername(auth.getName());
        Page<User> partnersPage = msgService.getPartners(me, page);

        model.addAttribute("partners", partnersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", partnersPage.getTotalPages());
        return "mailbox";
    }


    @GetMapping("/{username}")
    public String thread(@PathVariable String username,
                         Authentication auth, Model model) {
        User me = userService.findByUsername(auth.getName());
        model.addAttribute("other", username);
        model.addAttribute("thread", msgService.getThread(me, username));
        return "message_thread";
    }

    @PostMapping("/{username}/reply")
    public String reply(@PathVariable String username,
                        @RequestParam String content,
                        Authentication auth) {
        msgService.sendMessage(auth, username, content);
        return "redirect:/mailbox/" + username;
    }
}
