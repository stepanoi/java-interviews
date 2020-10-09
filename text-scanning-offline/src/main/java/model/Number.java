package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@ToString
public class Number {
    private final Line top;
    private final Line middle;
    private final Line bottom;
}
