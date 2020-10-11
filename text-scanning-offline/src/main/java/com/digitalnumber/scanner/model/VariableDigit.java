package com.digitalnumber.scanner.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class VariableDigit {
    private final Line top;
    private final Line bottom;
}


