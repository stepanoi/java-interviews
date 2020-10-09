package model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@ToString
public class Line {
    private final Fill left;
    private final Fill body;
    private final Fill right;
}
