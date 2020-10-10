package digital.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Number {
    private final String[] lines;
    private final int start;
    private final int end;
}
