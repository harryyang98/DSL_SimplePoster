package modules;

import modules.function.PosterIF;
import modules.function.PosterLOOP;
import modules.item.PosterImage;
import modules.item.PosterItem;
import modules.item.PosterPanel;
import modules.item.PosterText;
import modules.util.PosterCalculation;
import modules.util.PosterCondition;
import modules.util.PosterIFPair;
import modules.util.PosterPoint;
import ui.UserException;

import java.util.*;

public class PosterEvaluate {
    private final List<PosterObject> posterObjects;
    public PosterCanvas canvas;
    private final Map<String, PosterItem> varMap;
    private int loopCounts;

    public PosterEvaluate(List<PosterObject> posterObjects) {
        this.posterObjects = posterObjects;
        varMap = new LinkedHashMap<>();
        loopCounts = 0;
    }

    public void evaluate() {
        Map<Integer, List<PosterItem>> objectMap = new TreeMap<>();
        PosterPanel panel = new PosterPanel();
//        List<PosterObject> panelObjects = new ArrayList<>();
        for (PosterObject obj : posterObjects) {
            if (obj instanceof PosterPanel) {
                panel = (PosterPanel) obj;
                varMap.put(panel.getVarName(), panel);
            } else if (obj instanceof PosterIF) {
                PosterIF pif = (PosterIF) obj;
                evaluateIf(pif);
            } else if (obj instanceof PosterLOOP) {
                PosterLOOP ploop = (PosterLOOP) obj;
                evaluateLoop(ploop);
            } else if (obj instanceof PosterImage) {
                PosterImage pimg = (PosterImage) obj;
                varMap.put(pimg.getVarName(), pimg);
            } else if (obj instanceof PosterText) {
                PosterText ptxt = (PosterText) obj;
                varMap.put(ptxt.getVarName(), ptxt);
            }
        }
        for (Map.Entry<String, PosterItem> entry : varMap.entrySet()) {
            PosterItem item = entry.getValue();
            if (item instanceof PosterPanel) continue;
            int index = item.getPos().z;
            if (objectMap.containsKey(index)) {
                List<PosterItem> list = objectMap.get(index);
                list.add(item);
                objectMap.put(index, list);
            } else {
                List<PosterItem> list = new ArrayList<>();
                list.add(item);
                objectMap.put(index, list);
            }
        }
        canvas = new PosterCanvas(objectMap, panel);
    }

    public void evaluateIf(PosterIF pif) {
        boolean check = false;
        List<PosterIFPair> pairs = pif.getPairs();
        PosterCondition cond;
        for (PosterIFPair p : pairs) {
            cond = p.getCond();
            String termA = cond.getTermA().trim();
            String termB = cond.getTermB().trim();
            // TODO: validate termA
            // TODO: validate termB
            String compare = cond.getCompare();
            int valA = getValueOfTerm(termA);
            int valB = getValueOfTerm(termB);
            check = PosterCondition.result(valA, valB, compare);
            if (check) {
                doIfStatement(p.getRes());
                break;
            }
        }
        if (!check) {
            if(pif.getElseStatement()!=null) {
                doIfStatement(pif.getElseStatement());
            }
        }
    }

    public void evaluateLoop(PosterLOOP ploop) {
        List<String> vars = ploop.getItems();
        int t = ploop.getTime();
        String dir = ploop.getDir();
        int shift = ploop.getShift();
        PosterPoint pos = ploop.getPos();
        int x = 0, y = 0, xShift = 0, yShift = 0;
        if (dir.equals("HOR")) {
            xShift = shift;
        } else if (dir.equals("VER")) {
            yShift = shift;
        }

        for (String var : vars) {
            var = var.trim();
            for (int i = 0; i < t; i++) {
                PosterItem item = varMap.get(var);
                PosterItem itemCopy = PosterItem.copy(item);
                if (itemCopy == null) throw new RuntimeException("");
                itemCopy.setVarName(itemCopy.getVarName() + "#" + loopCounts + "-" + i);
                PosterPoint point = new PosterPoint(pos.x + x, pos.y + y, pos.z);
                x += xShift;
                y += yShift;
                itemCopy.setPos(point);
                varMap.put(itemCopy.getVarName(), itemCopy);
                loopCounts ++;
            }
        }
    }

    private int getValueOfTerm(String term) {
        int value = Integer.MIN_VALUE;
        String[] ss = term.split("\\.");
        if (ss.length > 1) {
            String var = ss[0].trim();
            String attr = ss[1].trim();
            if (varMap.containsKey(var)) {
                String[] tmp = attr.split("(?<=[\\*/\\+-])|(?=[|\\*/\\+-])");
                value = varMap.get(var).getValueByAttr(tmp[0].trim());
                for (int i = 1; i < tmp.length; i += 2) {
                    try {
                        value = PosterCalculation.calculate(value, Integer.parseInt(tmp[i + 1].trim()), tmp[i].trim());
                    } catch (NumberFormatException e) {
                        throw new UserException("Error: the expected math format is not correct." + e.getMessage());
                    }
                }
            } else {
                throw new UserException("Error: the term variable does not exist.");
            }

        } else {
            try {
                value = Integer.parseInt(term);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return value;
    }

    private void doIfStatement(String statement) {
        String[] terms = statement.split("=");
        if (terms.length != 2) throw new RuntimeException("Error: here should be the assignment statement with \"=\" sign");
        int val = getValueOfTerm(terms[1]);
        String[] ss = terms[0].split("\\.");
        String var = ss[0].trim();
        String attr = ss[1].trim();
        if (varMap.containsKey(var)) {
            if(!varMap.get(var).setAttr(attr, val)) {
                throw new UserException("Error: the attribute cannot be found, assign failed.");
            }
        } else {
            throw new UserException("Error: the variable cannot be found.");
        }
    }

}
