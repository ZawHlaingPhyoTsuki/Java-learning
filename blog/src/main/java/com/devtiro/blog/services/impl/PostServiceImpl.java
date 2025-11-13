package com.devtiro.blog.services.impl;

import com.devtiro.blog.domain.PostStatus;
import com.devtiro.blog.domain.entities.Category;
import com.devtiro.blog.domain.entities.Post;
import com.devtiro.blog.domain.entities.Tag;
import com.devtiro.blog.repositories.PostRepository;
import com.devtiro.blog.services.CategoryService;
import com.devtiro.blog.services.PostService;
import com.devtiro.blog.services.TagService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final CategoryService categoryService;
  private final TagService tagService;

  @Override
  @Transactional(readOnly = true)
  public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
    if (categoryId != null && tagId != null) {
      Category category = categoryService.getCategoryById(categoryId);
      Tag tag = tagService.getTagById(tagId);
      return postRepository.findAllByStatusAndCategoryAndTagsContaining(
          PostStatus.PUBLISHED, category, tag);
    }

    if (categoryId != null) {
      Category category = categoryService.getCategoryById(categoryId);
      return postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED,
                                                       category);
    }

    if (tagId != null) {
      Tag tag = tagService.getTagById(tagId);
      return postRepository.findAllByStatusAndTagsContaining(
          PostStatus.PUBLISHED, tag);
    }

    return postRepository.findAllByStatus(PostStatus.PUBLISHED);
  }


}
