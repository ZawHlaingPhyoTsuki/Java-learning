package com.devtiro.blog.controllers;

import com.devtiro.blog.domain.dtos.TagResponse;
import com.devtiro.blog.domain.entities.Tag;
import com.devtiro.blog.mappers.TagMapper;
import com.devtiro.blog.services.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

  private final TagService tagService;
  private final TagMapper tagMapper;

  @GetMapping
  public ResponseEntity<List<TagResponse>> getAllTags() {
    List<Tag> tags = tagService.getAllTags();
    List<TagResponse> tagResponses = tags.stream()
                                         .map(tagMapper::toTagResponse)
                                         .toList();

    return ResponseEntity.ok(tagResponses);
  }
}
