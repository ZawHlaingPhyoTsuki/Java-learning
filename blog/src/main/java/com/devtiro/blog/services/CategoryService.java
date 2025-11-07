package com.devtiro.blog.services;

import com.devtiro.blog.domain.entities.Category;
import java.util.List;

public interface CategoryService {

  List<Category> listCategories();

  Category createCategory(Category category);
}
