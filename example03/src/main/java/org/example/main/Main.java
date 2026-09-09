package org.example.main;

import org.example.configuration.ProjectConfiguration;
import org.example.model.Comment;
import org.example.services.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {

        var context = new AnnotationConfigApplicationContext(ProjectConfiguration.class);
        var commentService = context.getBean(CommentService.class);

        var comment = new Comment();
        comment.setAuthor("Hari");
        comment.setText("Look, I recently bought a new Parrot!");

        commentService.publishComment(comment);
    }
}
