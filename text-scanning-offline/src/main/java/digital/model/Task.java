package digital.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Task {
    private final String input;
    private final int width;
    private final boolean variableMode;
    private final boolean singleEmptyLineSeparator;
}
