package org.example.post.service;

import org.example.post.model.Post;
import org.example.post.model.Status;

import java.util.Optional;

public interface PostService {
    Post createPost(Post post);

    Optional<Post> findById(Long postId);

    Post changePostStatus(Long postId, Status status);
}
