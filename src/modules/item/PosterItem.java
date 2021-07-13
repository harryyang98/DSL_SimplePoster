package modules.item;

import modules.PosterObject;
import modules.util.PosterPoint;

public class PosterItem extends PosterObject {
    private String varName;
    private PosterPoint pos;
    private Integer width;
    private Integer height;


    public PosterItem(){}


    public PosterItem(String varName, PosterPoint pos, Integer width, Integer height) {
        this.varName = varName;
        this.pos = pos;
        this.width = width;
        this.height = height;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public PosterPoint getPos() {
        return pos;
    }


    public void setPos(PosterPoint pos) {
        this.pos = pos;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public int getValueByAttr(String attr) {
        if (attr.equals("WIDTH")) return this.getWidth();
        if (attr.equals("HEIGHT")) return this.getHeight();
        return Integer.MIN_VALUE;
    }

    public boolean setAttr(String attr, int val) {
        if (attr.equals("WIDTH")) setWidth(val);
        else if (attr.equals("HEIGHT")) setHeight(val);
        else return false;
        return true;
    }

    public static PosterItem copy(PosterItem item) {
        if (item instanceof PosterImage) {
            return new PosterImage(item.getVarName(), item.getPos(), item.getWidth(), item.getHeight(),
                    ((PosterImage) item).getFilePath());
        } else if (item instanceof PosterText) {
            return new PosterText(item.getVarName(), item.getPos(), ((PosterText) item).getContent(),
                    ((PosterText) item).getFontSize());
        }
        return null;
    }

}
