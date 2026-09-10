package main;

import config.ProjectConfig;
import model.Comment;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.CommentService;

import java.util.logging.Logger;

public class Main {
    private Logger logger = Logger.getLogger(Main.class.getName());

    void main() {
        var c = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var service = c.getBean(CommentService.class);

        Comment comment = new Comment();
        comment.setText("I bought a Parrot");
        comment.setAuthor("Hari");

        String value = service.publishComment(comment);
        logger.info(value);
    }
}
