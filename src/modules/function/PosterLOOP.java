package modules.function;

import modules.PosterObject;
import modules.util.PosterCondition;
import modules.util.PosterPoint;

import java.util.Arrays;
import java.util.List;

/**
 * LOOP ([list], TIMES: 10, DIR: HOR/VER, SHF: 10, POS:(), IF(WIDTH > 200));
 * [list]: list of var names
 * TIMES: loop times
 * DIR: loop direction, HOR/VER/CIR
 * SHF: shift
 * POS: start position
 * IF(WIDTH > 200): PosterIF applies to each element in the [list]
 */
public class PosterLOOP extends PosterObject {
    private List<String> items;
    private int time;
    private String dir;
    private int shift;
    private PosterPoint pos;
    private final List<String> dirs= Arrays.asList("HOR","VER");
//    private PosterIF ifStatement;
    private PosterCondition con;    //Bobo

//    public PosterLOOP(List<String> items, int time, String dir, int shift, Point pos, PosterIF ifStatement) {
    public PosterLOOP(List<String> items, int time, String dir, int shift, PosterPoint pos, PosterCondition con) {
        this.items = items;
        this.time = time;
        this.dir = dir;
        this.shift = shift;
        this.pos = pos;
//        this.ifStatement = ifStatement;
        this.con = con;
    }

    public PosterLOOP(List<String> items, int time, String dir, int shift, PosterPoint pos) {
        this.items = items;
        this.time = time;
        this.dir = dir;
        this.shift = shift;
        this.pos = pos;
    }
    public boolean dirValid(){
        return dirs.contains(dir);
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public PosterPoint getPos() {
        return pos;
    }

    public void setPos(PosterPoint pos) {
        this.pos = pos;
    }

//    public PosterIF getIfStatement() {
//        return ifStatement;
//    }
    public PosterCondition getCondition() { //Bobo
        return con;
    }
//    public void setIfStatement(PosterIF ifStatement) {
//        this.ifStatement = ifStatement;
//    }
    public void setCondition(PosterCondition con) {
        this.con = con;
    }
}
