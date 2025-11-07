package com.devtiro.blog.mappers;

import com.devtiro.blog.domain.PostStatus;
import com.devtiro.blog.domain.dtos.CategoryDto;
import com.devtiro.blog.domain.dtos.CreateCategoryRequest;
import com.devtiro.blog.domain.entities.Category;
import com.devtiro.blog.domain.entities.Post;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

  @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
  CategoryDto toDto(Category category);

  Category toEntity(CreateCategoryRequest createCategoryRequest);

  @Named("calculatePostCount")
  default long calculatePostCound(List<Post> posts) {
    if (posts == null) {
      return 0;
    }
    return posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
  }
}
