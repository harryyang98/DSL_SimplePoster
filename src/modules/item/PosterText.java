package modules.item;

import modules.util.PosterPoint;

public class PosterText extends PosterItem {
    private String content;
    private int fontSize;
    private String fontColor;

    public PosterText(){

    }

    public PosterText(String varName, PosterPoint pos, Integer width, Integer height, String content, int fontSize) {
        super(varName, pos, width, height);
        this.content = content;
        this.fontSize = fontSize;
    }

    public PosterText(String varName, PosterPoint pos, String content, int fontSize) {
        super(varName, pos, null, null);
        this.content = content;
        this.fontSize = fontSize;
    }

    public PosterText(String varName, PosterPoint pos, String content, int fontSize, String fontColor) {
        super(varName, pos, null, null);
        this.content = content;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getValueByAttr(String attr) {
        int val = super.getValueByAttr(attr);
        if (val != Integer.MIN_VALUE) return val;
        if (attr.equals("FONTSIZE")) {
            return this.fontSize;
        }
        return Integer.MIN_VALUE;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
}
