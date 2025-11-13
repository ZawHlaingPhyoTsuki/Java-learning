package com.devtiro.blog.services.impl;

import com.devtiro.blog.domain.entities.Tag;
import com.devtiro.blog.repositories.TagRepository;
import com.devtiro.blog.services.TagService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  @Override
  public List<Tag> getAllTags() {
    return tagRepository.findAllWithPostCount();
  }

  @Transactional
  @Override
  public List<Tag> createTags(Set<String> tagNames) {
    List<Tag> existingTags = tagRepository.findByNameIn(tagNames);
    Set<String> existingTagNames = existingTags.stream()
                                               .map(Tag::getName)
                                               .collect(Collectors.toSet());
    List<Tag> newTags = tagNames.stream()
                                .filter(
                                    name -> !existingTagNames.contains(name))
                                .map(name -> Tag.builder()
                                                .name(name)
                                                .posts(new HashSet<>())
                                                .build())
                                .toList();

    List<Tag> savedTags = new ArrayList<>();
    if (!newTags.isEmpty()) {
      savedTags = tagRepository.saveAll(newTags);
    }

    savedTags.addAll(existingTags);

    return savedTags;
  }
}
