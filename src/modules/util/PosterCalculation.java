package modules.util;

import ui.UserException;

/**
 * E.g.
 * varB.WIDTH / 2
 * elementA is varB.WIDTH
 * elementB is 2
 * function is /
 */
public class PosterCalculation {

    public static int calculate(int a, int b, String function) {
        if (function == null) return a;
        return switch (function) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new UserException("Error: the operation is not valid");
        };
    }

}
