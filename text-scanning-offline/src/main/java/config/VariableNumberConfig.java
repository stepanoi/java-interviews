package config;

import model.Line;
import model.VariableNumber;

import java.util.Map;

import static model.Fill.BLANK;
import static model.Fill.LINE;

public class VariableNumberConfig {
    
    
    /*   *    *
     *  --   --
     * |  | |  | *
     * |  | |__|
     * |  | |  | *
     * |__| |__|
     */
    public static final VariableNumber VARIABLE_NUMBER_0_8 = VariableNumber.builder()
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
    public static final VariableNumber VARIABLE_NUMBER_1_3_7 = VariableNumber.builder()
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
    public static final VariableNumber VARIABLE_NUMBER_2 = VariableNumber.builder()
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
    public static final VariableNumber VARIABLE_NUMBER_4_9 = VariableNumber.builder()
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
    public static final VariableNumber VARIABLE_NUMBER_5 = VariableNumber.builder()
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
    public static final VariableNumber VARIABLE_NUMBER_6 = VariableNumber.builder()
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

    public static Map<Integer, VariableNumber> variableNumberMap = Map.of(
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
