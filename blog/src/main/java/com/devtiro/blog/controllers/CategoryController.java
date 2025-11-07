package com.devtiro.blog.controllers;

import com.devtiro.blog.domain.dtos.CategoryDto;
import com.devtiro.blog.domain.entities.Category;
import com.devtiro.blog.services.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> listCategories() {
    List<Category> categories = categoryService.listCategories();
    return categories;
  }
}
