package modules.util;

import ui.UserException;

/**
 *
 * We only deal with the integer case, there is no much String case would be considered
 * in the if cases.
 * E.g.
 * varA.WIDTH < varB.WIDTH / 2,
 *
 *      "varA.WIDTH" is termA,
 *      "varB.WIDTH / 2" is termB,
 *      "<" is compare
 *
 */
public class PosterCondition {
    private String termA;
    private String termB;
    private String compare;

    public PosterCondition(String termA, String termB, String compare) {
        this.termA = termA;
        this.termB = termB;
        this.compare = compare;
    }

    public PosterCondition(){}

    public static boolean result(int a, int b, String compare) {
        compare = compare.trim();
        return switch (compare) {
            case "<" -> a < b;
            case "=" -> a == b;
            case ">" -> a > b;
            default -> throw new UserException("Error: the compare operation is not valid");
        };
    }

    public String getTermA() {
        return termA;
    }

    public void setTermA(String termA) {
        this.termA = termA;
    }

    public String getTermB() {
        return termB;
    }

    public void setTermB(String termB) {
        this.termB = termB;
    }

    public String getCompare() {
        return compare;
    }

    public void setCompare(String compare) {
        this.compare = compare;
    }
}
