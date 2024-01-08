package com.blogger.service;

import com.blogger.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId, CommentDto commentDto);

    void deleteComment(long commentId);

    List<CommentDto> getCommentsByPostId(long postId);

    List<CommentDto> getAllComments();

    CommentDto updateComment(long commentId, CommentDto commentDto);
}
