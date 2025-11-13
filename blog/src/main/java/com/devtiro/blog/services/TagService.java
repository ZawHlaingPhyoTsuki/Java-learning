package com.devtiro.blog.services;

import com.devtiro.blog.domain.entities.Tag;
import java.util.List;

public interface TagService {
  List<Tag> getAllTags();
}
