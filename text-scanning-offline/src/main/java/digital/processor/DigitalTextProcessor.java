package digital.processor;

import java.util.List;

public class DigitalTextProcessor extends AbstractDigitalTextProcessor {
    
    @Override
    public Integer value(final List<String> lines, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        return super.handle(lines, firstLinePos, middleLinePos, lastLinePos);
    }
}
