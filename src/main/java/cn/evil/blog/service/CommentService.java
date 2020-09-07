package cn.evil.blog.service;

import cn.evil.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    int saveComment(Comment comment);
}
