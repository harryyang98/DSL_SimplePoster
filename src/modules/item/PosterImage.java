package modules.item;

import modules.util.PosterPoint;

public class PosterImage extends PosterItem {
    private String filePath;

    public PosterImage() {

    }
    public PosterImage(String varName, PosterPoint pos, Integer width, Integer height, String filePath) {
        super(varName, pos, width, height);
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getValueByAttr(String attr) {
        return super.getValueByAttr(attr);
    }

//    public boolean setAttr(String attr, int val) {
//        if (super.setAttr(attr, val)) return true;
//    }
}
