package modules;

public class PosterObject {
    private Boolean isValid = true;//for validate
    public void setInvalid(){
        this.isValid=false;
    }

    public Boolean getValid() {
        return isValid;
    }
}
