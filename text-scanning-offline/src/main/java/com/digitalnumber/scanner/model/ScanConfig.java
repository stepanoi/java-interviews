package com.digitalnumber.scanner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ScanConfig {
    private final int     width;
    private final boolean variableMode;
    private final boolean singleEmptyLineSeparator;
}
