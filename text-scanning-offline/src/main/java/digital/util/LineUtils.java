package digital.util;

import digital.model.Line;

import java.util.Objects;

public class LineUtils {
    public static boolean matchEdge(final Line current, final Line ref) {
        return Objects.nonNull(current) &&
               Objects.nonNull(ref) &&
               current.getRight() == ref.getRight() &&
               current.getLeft() == ref.getLeft();
    }

    public static boolean matchBody(final Line current, final Line ref) {
        return Objects.nonNull(current) &&
               Objects.nonNull(ref) &&
               (current.getBody() == null || current.getBody() == ref.getBody());
    }
}
