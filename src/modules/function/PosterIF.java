package modules.function;

import modules.PosterObject;
import modules.util.PosterIFPair;

import java.util.ArrayList;
import java.util.List;

/**
 * E.g
 * IF (varA.WIDTH < varB.WIDTH / 2)
 * THEN varA.WIDTH = varA.WIDTH / 2
 * ELIF (varA.WIDTH > 300)
 *      do something B
 * ELSE
 *      do something C
 * ENDIF
 *
 * "varA.WIDTH < varB.WIDTH / 2" is a PosterCondition,
 *      "varA.WIDTH" is termA,
 *      "varB.WIDTH / 2" is termB
 * "varA.WIDTH = varA.WIDTH / 2" is a res String,
 *
 * the PosterCondition and res is a PosterIFPair which is one element of List<PosterIFPair></PosterIFPair>
 *
 *
 */
public class PosterIF extends PosterObject {
    List<PosterIFPair> pairs;
    String elseStatement;

    public PosterIF() {
        pairs = new ArrayList<>();
    }

    public void addIf(PosterIFPair p) {
        pairs.add(p);
    }

    public void addElse(String res) {
        elseStatement = res;
    }

    public List<PosterIFPair> getPairs() {
        return pairs;
    }

    public void setPairs(List<PosterIFPair> pairs) {
        this.pairs = pairs;
    }

    public String getElseStatement() {
        return elseStatement;
    }

    public void setElseStatement(String elseStatement) {
        this.elseStatement = elseStatement;
    }
}
