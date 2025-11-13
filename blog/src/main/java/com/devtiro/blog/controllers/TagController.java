package com.devtiro.blog.controllers;

import com.devtiro.blog.domain.dtos.CreateTagsRequest;
import com.devtiro.blog.domain.dtos.TagResponse;
import com.devtiro.blog.domain.entities.Tag;
import com.devtiro.blog.mappers.TagMapper;
import com.devtiro.blog.services.TagService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping
  public ResponseEntity<List<TagResponse>> createTags(
      @RequestBody CreateTagsRequest createTagsRequest) {
    List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());
    List<TagResponse> createdTagResponses = savedTags.stream()
                                                     .map(
                                                         tagMapper::toTagResponse)
                                                     .toList();

    return new ResponseEntity<>(createdTagResponses, HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
    tagService.deleteTagById(id);
    return ResponseEntity.noContent().build();
  }
}
