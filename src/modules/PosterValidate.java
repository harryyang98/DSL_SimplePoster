package modules;

import modules.function.PosterLOOP;
import ui.UserException;

import java.util.List;

public class PosterValidate {

    private final List<PosterObject> posterObjects;

    public PosterValidate(List<PosterObject> posterObjects) {
        this.posterObjects = posterObjects;
    }

    public void validate() {
        for (PosterObject po: posterObjects){
            validateVariable(po);
            if(po instanceof PosterLOOP){
                validateLOOP((PosterLOOP) po);
            }

        }
    }


    private void validateLOOP(PosterLOOP po) {
        if(!po.dirValid())
            throw new UserException("Error: Invalid Direction");

    }


    private void validateVariable(PosterObject po) {
        if(!po.getValid()){
            throw new UserException("Error: A " + getLastName(po)+ " has invalid number of variables.");
        }
    }
    public String getLastName(PosterObject po){
        String[] a = po.getClass().getName().split("\\.");
        return a[a.length-1];
    }
}
