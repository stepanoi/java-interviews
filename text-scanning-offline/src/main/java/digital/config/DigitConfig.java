package digital.config;

import digital.model.Digit;
import digital.model.Line;

import java.util.Map;

import static digital.model.Fill.BAR;
import static digital.model.Fill.BLANK;
import static digital.model.Fill.LINE;

public class DigitConfig {
    /*
     *  _
     * |_|
     * |_|
     */
    private static final Digit reference = Digit.builder()
                                                .top(Line.builder() //     -
                                                           .left(BLANK)
                                                           .body(BAR)
                                                           .right(BLANK)
                                                           .build())
                                                .middle(Line.builder() // |_|
                                                              .left(LINE)
                                                              .body(BAR)
                                                              .right(LINE)
                                                              .build())
                                                .bottom(Line.builder() // |_|
                                                              .left(LINE)
                                                              .body(BAR)
                                                              .right(LINE)
                                                              .build())
                                                .build();

    public static Map<Digit, Integer> MAP = Map.of(
            /*
             *  -
             * | |
             * |_|
             */
            reference.toBuilder()
                     .middle(reference.getMiddle()
                                      .toBuilder()
                                      .body(BLANK)
                                      .build())
                     .build(), 0,
            /*
             *
             *  |
             *  |
             */
            reference.toBuilder()
                     .top(reference.getTop()
                                   .toBuilder()
                                   .body(BLANK)
                                   .build())
                     .middle(reference.getMiddle()
                                      .toBuilder()
                                      .left(BLANK)
                                      .body(BLANK)
                                      .build())
                     .bottom(reference.getBottom()
                                      .toBuilder()
                                      .left(BLANK)
                                      .body(BLANK)
                                      .build())
                     .build(), 1,
            /*
             *  _
             *  _|
             * |_
             */
            reference.toBuilder()
                     .middle(reference.getMiddle()
                                      .toBuilder()
                                      .left(BLANK)
                                      .build())
                     .bottom(reference.getBottom()
                                      .toBuilder()
                                      .right(BLANK)
                                      .build())
                     .build(), 2,
            /*
             *  _
             *  _|
             *  _|
             */
            reference.toBuilder()
                     .middle(reference.getMiddle()
                                      .toBuilder()
                                      .left(BLANK)
                                      .build())
                     .bottom(reference.getBottom()
                                      .toBuilder()
                                      .left(BLANK)
                                      .build())
                     .build(), 3,
            /*
             *
             * |_|
             *   |
             */
            reference.toBuilder()
                     .top(reference.getTop()
                                   .toBuilder()
                                   .body(BLANK)
                                   .build())
                     .bottom(reference.getBottom()
                                      .toBuilder()
                                      .left(BLANK)
                                      .body(BLANK)
                                      .build())
                     .build(), 4,
            /*
             *  _
             * |_
             *  _|
             */
            reference.toBuilder()
                     .middle(reference.getMiddle()
                                      .toBuilder()
                                      .right(BLANK)
                                      .build())
                     .bottom(reference.getBottom()
                                      .toBuilder()
                                      .left(BLANK)
                                      .build())
                     .build(), 5,
            /*
             *  _
             * |_
             * |_|
             */
            reference.toBuilder()
                     .middle(reference.getMiddle()
                                      .toBuilder()
                                      .right(BLANK)
                                      .build())
                     .build(), 6,
            /*
             *  _
             *   |
             *   |
             */
            reference.toBuilder()
                     .middle(reference.getMiddle()
                                      .toBuilder()
                                      .left(BLANK)
                                      .body(BLANK)
                                      .build())
                     .bottom(reference.getBottom()
                                      .toBuilder()
                                      .left(BLANK)
                                      .body(BLANK)
                                      .build())
                     .build(), 7,
            /*
             *  _
             * |_|
             * |_|
             */
            reference, 8,
            /*
             *  _
             * |_|
             *  _|
             */
            reference.toBuilder()
                     .bottom(reference.getBottom()
                                      .toBuilder()
                                      .left(BLANK)
                                      .build())
                     .build(), 9
    );
}
