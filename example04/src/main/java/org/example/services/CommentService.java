package org.example.services;

import org.example.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private ApplicationContext context;

    public void sendComment(Comment c) {
        /*
        // Old code
        CommentProcessor p = new CommentProcessor();
         */

        CommentProcessor p = context.getBean(CommentProcessor.class);
        p.setComment(c);
        p.processComment(c);
        p.validateComment(c);
        c = p.getComment();
        System.out.println("Modified comment: Author: " + c.getAuthor() + ", Text: " + c.getText());
    }
}
