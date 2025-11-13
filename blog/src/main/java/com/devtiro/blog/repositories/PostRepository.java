package com.devtiro.blog.repositories;

import com.devtiro.blog.domain.PostStatus;
import com.devtiro.blog.domain.entities.Category;
import com.devtiro.blog.domain.entities.Post;
import com.devtiro.blog.domain.entities.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

  List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus postStatus,
                                                         Category category,
                                                         Tag tag);

  List<Post> findAllByStatusAndCategory(PostStatus postStatus,
                                        Category category);


  List<Post> findAllByStatusAndTagsContaining(PostStatus postStatus, Tag tag);

  List<Post> findAllByStatus(PostStatus postStatus);
}
