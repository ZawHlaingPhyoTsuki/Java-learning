package com.devtiro.blog.services;

import com.devtiro.blog.domain.entities.Tag;
import java.util.List;
import java.util.Set;

public interface TagService {

  List<Tag> getAllTags();

  List<Tag> createTags(Set<String> tagNames);

}
