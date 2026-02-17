package com.isep.controller;

import com.isep.dto.SearchResultDTO;
import com.isep.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {
    
    private final SearchService searchService;
    
    @GetMapping
    public ResponseEntity<List<SearchResultDTO>> search(@RequestParam String query) {
        return ResponseEntity.ok(searchService.search(query));
    }
}
