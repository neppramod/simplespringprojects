package org.example.proxies;

import org.example.model.Comment;

public interface CommentNotificationProxy {
    void sentComment(Comment comment);
}
