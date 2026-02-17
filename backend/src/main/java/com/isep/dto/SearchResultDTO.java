package com.isep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDTO {
    private String type; // SERVICE, FORUM, ACTIVITY, ANNOUNCEMENT
    private Long id;
    private String title;
    private String description;
    private String url;
}
