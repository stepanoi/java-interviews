package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Fill {
    BLANK(" "),
    LINE("|"),
    BAR("_");

    private final String value;

    public static Fill from(String s) {
        return Arrays.stream(values())
                     .filter(e -> e.getValue().equalsIgnoreCase(s))
                     .findFirst()
                     .orElse(null);
    }
}
