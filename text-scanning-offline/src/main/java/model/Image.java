package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
public class Image {
    @Singular
    private final List<String> lines;
}
