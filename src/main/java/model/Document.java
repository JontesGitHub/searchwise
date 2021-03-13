package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Document {
    private final Integer id;
    private final String text;
}
