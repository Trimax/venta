package com.gesoftware.venta.math.tools;

/**
 * Mathematics class definition
 **/
public final class Mathematics {
    private static final double c_Epsilon = 0.00001;

    /* *
     * METHOD: Compares two double values
     * RETURN: True if values are approximately the same, False otherwise
     *  PARAM: [IN] value1 - first value
     *  PARAM: [IN] value2 - second value
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean equals(final double value1, final double value2) {
        return value1 == value2 || Math.abs(value1 - value2) < c_Epsilon;
    } /* End of 'Mathematics::equals' method */

    /* *
     * METHOD: Finds minimal value of given
     * RETURN: The minumal value of given
     *  PARAM: [IN] numbers - values to find minimal
     * AUTHOR: Eliseev Dmitry
     * */
    public static <T extends Number> T min(final T... numbers) {
        T min = numbers[0];

        for (int i = 1; i < numbers.length; i++)
            if (numbers[i].doubleValue() < min.doubleValue())
                min = numbers[i];

        return min;
    } /* End of 'Mathematics::min' method */

    /* *
     * METHOD: Finds maximal value of given
     * RETURN: The maximal value of given
     *  PARAM: [IN] numbers - values to find maximal
     * AUTHOR: Eliseev Dmitry
     * */
    public static <T extends Number> T max(final T... numbers) {
        T max = numbers[0];

        for (int i = 1; i < numbers.length; i++)
            if (numbers[i].doubleValue() > max.doubleValue())
                max = numbers[i];

        return max;
    } /* End of 'Mathematics::max' method */
} /* End of 'Mathematics' class */
