package com.devtiro.blog.services.impl;

import com.devtiro.blog.domain.entities.Category;
import com.devtiro.blog.repositories.CategoryRepository;
import com.devtiro.blog.services.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> listCategories() {
    return categoryRepository.findAllWithPostCount();
  }
}
