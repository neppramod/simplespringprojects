package org.example.services;

import org.example.model.Comment;
import org.example.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommentProcessor {
    @Autowired
    private CommentRepository commentRepository;

    private Comment comment;

    public void setComment(Comment comment) {this.comment = comment;}
    public Comment getComment() {return this.comment;}

    public void processComment(Comment c) {
        // Changes the comment attribute
        c.setAuthor("Badri");
        commentRepository.storeComment(c);
    }
    public void validateComment(Comment c) {
        // validates and changes the comment attribute
        c.setText("Comment is bad, will be removed");
        System.out.println("Validation changed text to default template text");
    }
}
