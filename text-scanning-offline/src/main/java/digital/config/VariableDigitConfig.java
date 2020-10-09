package digital.config;

import digital.model.Line;
import digital.model.VariableDigit;

import java.util.Map;

import static digital.model.Fill.BLANK;
import static digital.model.Fill.LINE;

public class VariableDigitConfig {
    
    
    /*   *    *
     *  --   --
     * |  | |  | *
     * |  | |__|
     * |  | |  | *
     * |__| |__|
     */
    public static final VariableDigit VARIABLE_NUMBER_0_8 = VariableDigit.builder()
                                                                         .top(Line.builder()
                                                                                    .left(LINE)
                                                                                    .body(BLANK)
                                                                                    .right(LINE)
                                                                                    .build())
                                                                         .bottom(Line.builder()
                                                                                       .left(LINE)
                                                                                       .body(BLANK)
                                                                                       .right(LINE)
                                                                                       .build())
                                                                         .build();
    /*   *    *    *
     *       --   --
     *    |    |    | *
     *    |  __|    |
     *    |    |    | *
     *    |  __|    |
     */
    public static final VariableDigit VARIABLE_NUMBER_1_3_7 = VariableDigit.builder()
                                                                           .top(Line.builder()
                                                                                      .left(BLANK)
                                                                                      .body(BLANK)
                                                                                      .right(LINE)
                                                                                      .build())
                                                                           .bottom(Line.builder()
                                                                                         .left(BLANK)
                                                                                         .body(BLANK)
                                                                                         .right(LINE)
                                                                                         .build())
                                                                           .build();
    /*   *
     *  __
     *    | *
     *  __|
     * |    *
     * |__
     */
    public static final VariableDigit VARIABLE_NUMBER_2 = VariableDigit.builder()
                                                                       .top(Line.builder()
                                                                                  .left(BLANK)
                                                                                  .body(BLANK)
                                                                                  .right(LINE)
                                                                                  .build())
                                                                       .bottom(Line.builder()
                                                                                     .left(LINE)
                                                                                     .body(BLANK)
                                                                                     .right(BLANK)
                                                                                     .build())
                                                                       .build();
    /*   *     *
     *        __
     * |  |  |  | *
     * |__|  |__|
     *    |     | *
     *    |     |
     */
    public static final VariableDigit VARIABLE_NUMBER_4_9 = VariableDigit.builder()
                                                                         .top(Line.builder()
                                                                                    .left(LINE)
                                                                                    .body(BLANK)
                                                                                    .right(LINE)
                                                                                    .build())
                                                                         .bottom(Line.builder()
                                                                                       .left(BLANK)
                                                                                       .body(BLANK)
                                                                                       .right(LINE)
                                                                                       .build())
                                                                         .build();
    /*   *
     *  __
     * |    *
     * |__
     *    | *
     *  __|
     */
    public static final VariableDigit VARIABLE_NUMBER_5 = VariableDigit.builder()
                                                                       .top(Line.builder()
                                                                                  .left(LINE)
                                                                                  .body(BLANK)
                                                                                  .right(BLANK)
                                                                                  .build())
                                                                       .bottom(Line.builder()
                                                                                     .left(BLANK)
                                                                                     .body(BLANK)
                                                                                     .right(LINE)
                                                                                     .build())
                                                                       .build();
    /*   *
     *  __
     * |    *
     * |__
     * |  | *
     * |__|
     */
    public static final VariableDigit VARIABLE_NUMBER_6 = VariableDigit.builder()
                                                                       .top(Line.builder()
                                                                                  .left(LINE)
                                                                                  .body(BLANK)
                                                                                  .right(BLANK)
                                                                                  .build())
                                                                       .bottom(Line.builder()
                                                                                     .left(LINE)
                                                                                     .body(BLANK)
                                                                                     .right(LINE)
                                                                                     .build())
                                                                       .build();

    public static Map<Integer, VariableDigit> MAP = Map.of(
            0, VARIABLE_NUMBER_0_8,
            1, VARIABLE_NUMBER_1_3_7,
            2, VARIABLE_NUMBER_2,
            3, VARIABLE_NUMBER_1_3_7,
            4, VARIABLE_NUMBER_4_9,
            5, VARIABLE_NUMBER_5,
            6, VARIABLE_NUMBER_6,
            7, VARIABLE_NUMBER_1_3_7,
            8, VARIABLE_NUMBER_0_8,
            9, VARIABLE_NUMBER_4_9
    );
}
