package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchResult {
    private final double tfidf;
    private final Document document;
}
