package com.devtiro.blog.services.impl;

import com.devtiro.blog.domain.CreatePostRequest;
import com.devtiro.blog.domain.PostStatus;
import com.devtiro.blog.domain.UpdatePostRequest;
import com.devtiro.blog.domain.entities.Category;
import com.devtiro.blog.domain.entities.Post;
import com.devtiro.blog.domain.entities.Tag;
import com.devtiro.blog.domain.entities.User;
import com.devtiro.blog.repositories.PostRepository;
import com.devtiro.blog.services.CategoryService;
import com.devtiro.blog.services.PostService;
import com.devtiro.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private static final int WORDS_PER_MINUTE = 200;
  private final PostRepository postRepository;
  private final CategoryService categoryService;
  private final TagService tagService;

  @Override
  public Post getPostById(UUID id) {
    return postRepository.findById(id)
                         .orElseThrow(() -> new EntityNotFoundException(
                             "Post does not exist with ID: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
    if (categoryId != null && tagId != null) {
      Category newCategory = categoryService.getCategoryById(categoryId);
      Tag tag = tagService.getTagById(tagId);
      return postRepository.findAllByStatusAndCategoryAndTagsContaining(
          PostStatus.PUBLISHED, newCategory, tag);
    }

    if (categoryId != null) {
      Category newCategory = categoryService.getCategoryById(categoryId);
      return postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED,
                                                       newCategory);
    }

    if (tagId != null) {
      Tag tag = tagService.getTagById(tagId);
      return postRepository.findAllByStatusAndTagsContaining(
          PostStatus.PUBLISHED, tag);
    }

    return postRepository.findAllByStatus(PostStatus.PUBLISHED);
  }

  @Override
  public List<Post> getDraftPosts(User user) {
    return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
  }

  @Override
  @Transactional
  public Post createPost(User user, CreatePostRequest createPostRequest) {
    Post newPost = new Post();
    newPost.setTitle(createPostRequest.getTitle());
    newPost.setContent(createPostRequest.getContent());
    newPost.setStatus(createPostRequest.getStatus());
    newPost.setAuthor(user);
    newPost.setReadingTime(
        calculateReadingTime(createPostRequest.getContent()));

    Category newCategory = categoryService.getCategoryById(
        createPostRequest.getCategoryId());
    newPost.setCategory(newCategory);

    List<Tag> tags = tagService.getTagsByIds(createPostRequest.getTagIds());
    newPost.setTags(new HashSet<>(tags));

    return postRepository.save(newPost);
  }

  @Override
  @Transactional
  public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
    Post existingPost = postRepository.findById(id)
                                      .orElseThrow(
                                          () -> new EntityNotFoundException(
                                              "Post does not exist with id: "
                                                  + id));

    existingPost.setTitle(updatePostRequest.getTitle());
    String postContent = updatePostRequest.getContent();
    existingPost.setContent(postContent);
    existingPost.setStatus(updatePostRequest.getStatus());
    existingPost.setReadingTime(calculateReadingTime(postContent));

    UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();
    if (updatePostRequestCategoryId != null && !existingPost.getCategory()
                                                            .getId()
                                                            .equals(
                                                                updatePostRequestCategoryId)) {
      Category newCategory = categoryService.getCategoryById(
          updatePostRequestCategoryId);
      existingPost.setCategory(newCategory);
    }

    Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();
    if (updatePostRequestTagIds != null) {
      Set<UUID> existingTagIds = existingPost.getTags()
                                             .stream()
                                             .map(Tag::getId)
                                             .collect(Collectors.toSet());

      if (!existingTagIds.equals(updatePostRequestTagIds)) {
        List<Tag> newTags = tagService.getTagsByIds(updatePostRequestTagIds);
        existingPost.setTags(new HashSet<>(newTags));
      }
    }

    return postRepository.save(existingPost);
  }

  @Override
  public void deletePostById(UUID id) {
    Post post = getPostById(id);
    postRepository.delete(post);
  }

  private Integer calculateReadingTime(String content) {
    if (content == null || content.isEmpty()) {
      return 0;
    }

    int wordCount = content.trim().split("\\s+").length;
    return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);
  }


}
