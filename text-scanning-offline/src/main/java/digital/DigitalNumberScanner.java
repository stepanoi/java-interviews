package digital;

import digital.container.DigitalNumberContainer;
import digital.model.Number;

import java.util.Iterator;

import static digital.container.DigitalNumberContainer.MIN_WIDTH;

public final class DigitalNumberScanner { ;

    public String scan(final String input) {
        return scan(input, MIN_WIDTH, false);
    }

    public String scan(final String input, final int width) {
        return scan(input, width, width == MIN_WIDTH);
    }

    public String scan(final String input, final boolean variable) {
        return scan(input, MIN_WIDTH, variable);
    }

    private String scan(final String input, final int width, final boolean variable) {
        DigitalNumberContainer digitalNumberContainer = new DigitalNumberContainer(input, width, variable);
        Number number;
        Iterator<Number> iterator = digitalNumberContainer.iterator();
        while(iterator.hasNext()) {
            number = iterator.next();
            digitalNumberContainer.process(number);
        }

        return digitalNumberContainer.getActualDigits();
    }
}
