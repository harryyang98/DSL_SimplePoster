package modules.util;

public class PosterIFPair {
    private PosterCondition cond;
    private String res;

    public PosterIFPair(PosterCondition cond, String res) {
        this.cond = cond;
        this.res = res;
    }

    public PosterCondition getCond() {
        return cond;
    }

    public void setCond(PosterCondition cond) {
        this.cond = cond;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
}
