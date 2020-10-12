package com.digitalnumber.scanner.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Number {
    private final String[] lines;
    private final int      start;
    private final int      end;
}
