package digital.processor;

import java.util.List;

public final class DigitalNumberProcessor extends AbstractDigitalNumberProcessor {
    
    @Override
    public Integer recognise(final List<String> lines, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        return super.read(lines, firstLinePos, middleLinePos, lastLinePos);
    }
}
