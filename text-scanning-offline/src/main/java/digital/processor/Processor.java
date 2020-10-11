package digital.processor;

import digital.model.Number;

import java.util.List;

public interface Processor {
    String process(final Number number, final int width);

    Integer recognise(final List<String> lines, final int firstLinePos, final int middleLinePos, final int lastLinePos);
}
