package digital.processor;

import java.util.List;

public final class NormalDigitalNumberProcessor extends DigitalNumberProcessor {
    
    @Override
    public Integer recognise(final List<String> lines, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        return super.read(lines, firstLinePos, middleLinePos, lastLinePos);
    }
}
