package com.devtiro.blog.controllers;

import com.devtiro.blog.domain.dtos.PostDto;
import com.devtiro.blog.domain.entities.Post;
import com.devtiro.blog.domain.entities.User;
import com.devtiro.blog.mappers.PostMapper;
import com.devtiro.blog.services.PostService;
import com.devtiro.blog.services.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;
  private final PostMapper postMapper;
  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<PostDto>> getAllPosts(
      @RequestParam(required = false) UUID categoryId,
      @RequestParam(required = false) UUID tagId) {
    List<Post> posts = postService.getAllPosts(categoryId, tagId);
    List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();

    return ResponseEntity.ok(postDtos);
  }

  @GetMapping(path = "/drafts")
  public ResponseEntity<List<PostDto>> getDraftPosts(
      @RequestAttribute UUID userId) {

    User loggedInUser = userService.getUserById(userId);
    List<Post> draftPosts = postService.getDraftPosts(loggedInUser);
    List<PostDto> draftPostDtos = draftPosts.stream()
                                            .map(postMapper::toDto)
                                            .toList();

    return ResponseEntity.ok(draftPostDtos);
  }
}
