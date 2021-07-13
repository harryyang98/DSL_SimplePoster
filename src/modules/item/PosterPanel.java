package modules.item;

public class PosterPanel extends PosterItem {
    private String title;

    public PosterPanel() {
    }

    public PosterPanel(String varName, Integer width, Integer height, String title) {
        super(varName, null, width, height);
        this.title = title;
    }

    public PosterPanel(String varName, Integer width, Integer height) {
        super(varName, null, width, height);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValueByAttr(String attr) {
        return super.getValueByAttr(attr);
    }
}
