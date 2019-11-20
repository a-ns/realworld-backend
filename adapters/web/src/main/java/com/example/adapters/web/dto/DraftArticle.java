package com.example.adapters.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DraftArticle {

    private Body article;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Body {
        @NotEmpty
        private String title;
        @NotEmpty
        private String description;
        @NotEmpty
        private String body;
        @Nullable
        private List<String> tagList;
    }

}
