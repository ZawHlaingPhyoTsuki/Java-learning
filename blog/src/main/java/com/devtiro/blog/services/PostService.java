package com.devtiro.blog.services;

import com.devtiro.blog.domain.CreatePostRequest;
import com.devtiro.blog.domain.entities.Post;
import com.devtiro.blog.domain.entities.User;
import java.util.List;
import java.util.UUID;

public interface PostService {

  List<Post> getAllPosts(UUID categoryId, UUID tagId);

  List<Post> getDraftPosts(User user);

  Post createPost(User user, CreatePostRequest createPostRequest);
}
