package digital;

import digital.container.DigitalNumberContainer;
import digital.model.Number;
import digital.model.Task;

import java.util.Iterator;

import static digital.container.DigitalNumberContainer.MIN_WIDTH;

public final class DigitalNumberScanner { ;

    public String scan(final String input) {
        return scan(Task.builder()
                        .input(input)
                        .width(MIN_WIDTH)
                        .variableMode(false)
                        .singleEmptyLineSeparator(true)
                        .build());
    }
 
    public String scan(final Task task) {
        DigitalNumberContainer digitalNumberContainer = new DigitalNumberContainer(task);
        Number number;
        Iterator<Number> iterator = digitalNumberContainer.iterator();
        while(iterator.hasNext()) {
            number = iterator.next();
            digitalNumberContainer.process(number);
        }

        return digitalNumberContainer.getActualDigits();
    }
}
