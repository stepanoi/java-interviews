package com.digitalnumber.scanner;

import com.digitalnumber.scanner.container.DigitalNumberContainer;
import com.digitalnumber.scanner.model.Number;
import com.digitalnumber.scanner.model.ScanConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

import static com.digitalnumber.scanner.container.DigitalNumberContainer.MIN_WIDTH;

@Slf4j
public final class DigitalNumberScanner {

    public String scan(final String input) {
        return scan(input, ScanConfig.builder()
                                     .width(DigitalNumberContainer.MIN_WIDTH)
                                     .variableMode(false)
                                     .singleEmptyLineSeparator(true)
                                     .build());
    }

    public String scan(final String input, final ScanConfig scanConfig) {
        log.info("Scanning with " + scanConfig);
        
        DigitalNumberContainer digitalNumberContainer = new DigitalNumberContainer(input, scanConfig);
        Number                 number;
        Iterator<Number>       iterator               = digitalNumberContainer.iterator();
        while (iterator.hasNext()) {
            number = iterator.next();
            digitalNumberContainer.process(number);
        }

        String actualDigits = digitalNumberContainer.getActualDigits();
        log.info("Result\n" + actualDigits);
        
        return actualDigits;
    }
}
