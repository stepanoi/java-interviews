package digital;

import digital.container.Container;
import digital.container.DigitalTextContainer;
import digital.model.Block;

import java.util.Iterator;

public class DigitalTextScanner { ;
    public final static int MIN_WIDTH = 3;

    public String scan(String input) {
        return scan(input, MIN_WIDTH, false);
    }

    public String scan(String input, int width) {
        return scan(input, width, width == MIN_WIDTH);
    }

    public String scan(String input, boolean variable) {
        return scan(input, MIN_WIDTH, variable);
    }

    private String scan(String input, int width, boolean variable) {
        Container<Block> digitalText = new DigitalTextContainer(input, width, variable);
        Block block;
        Iterator<Block> iterator = digitalText.iterator();
        while(iterator.hasNext()) {
            block = iterator.next();
            digitalText.process(block);
        }

        return digitalText.getActualDigits();
    }
}
