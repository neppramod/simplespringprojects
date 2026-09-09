package org.example.proxies;

import org.example.model.Comment;

public class EmailCommentNotificationProxy implements CommentNotificationProxy{
    @Override
    public void sentComment(Comment comment) {
        System.out.println("Sending notification for comment: " + comment.getText());
    }
}
