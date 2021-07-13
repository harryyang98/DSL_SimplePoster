package modules;

import modules.function.PosterIF;
import modules.function.PosterLOOP;
import modules.item.PosterImage;
import modules.item.PosterPanel;
import modules.item.PosterText;
import modules.util.PosterCondition;
import modules.util.PosterIFPair;
import modules.util.PosterPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PosterParse {
    private List<PosterObject> parsedList;

    private static final String NAME = "[A-Za-z][A-Za-z0-9]*";
    private static final String CONST = "[0-9]+"; // integer constants

    private final PosterTokenize tokenizer;

    public PosterParse(PosterTokenize tokenizer) {
        this.parsedList = new ArrayList<>();
        this.tokenizer = tokenizer;
    }

    public void run() {
        while (tokenizer.moreTokens()) {
            parse();
        }
    }

    private void parse() {
        if (tokenizer.checkToken("PANEL")) {
            parsePANEL();
        } else if (tokenizer.checkToken("IMG")) {
            parseIMG();
        } else if (tokenizer.checkToken("TXT")) {
            parseTXT();
        } else if (tokenizer.checkToken("LOOP")) {
            parseLOOP();
        } else if (tokenizer.checkToken("IF")) {
            parseIF();
        } else {
            throw new RuntimeException("Unknown statement: " + tokenizer.getNext());
        }

    }

    private PosterIF parseIF() {
        tokenizer.getAndCheckNext("IF");
        String init_cond = tokenizer.getNext();
        init_cond = init_cond.substring(1, init_cond.length()-1);
        String[] splt_cond = init_cond.split(",");
        String termA = splt_cond[0];
        String termB = splt_cond[1];
        String com = splt_cond[2];
        PosterCondition pc = new PosterCondition(termA, termB, com);
        tokenizer.getAndCheckNext("THEN");
        String init_cond_2 = tokenizer.getNext();
        init_cond_2 = init_cond_2.substring(1, init_cond_2.length()-1);
        PosterIFPair pifpair = new PosterIFPair(pc, init_cond_2);
        PosterIF pif = new PosterIF();
        pif.addIf(pifpair);
        //check if it contains else
        if(tokenizer.checkToken("ELSE")){
            tokenizer.getAndCheckNext("ELSE");
            String elseStr = tokenizer.getNext();
            elseStr = elseStr.substring(1, elseStr.length()-1);
            pif.addElse(elseStr);
        }
        tokenizer.getAndCheckNext(";");
        parsedList.add(pif);
        return pif;
    }

    private PosterLOOP parseLOOP() {
        tokenizer.getAndCheckNext("LOOP");
        String s = tokenizer.getNext();
        List<String> varloop = Arrays.asList(s.substring(1, s.length()-1).split(","));
        List<String> keywords = new ArrayList<>();
        keywords.add("TIMES:");
        keywords.add("DIR:");
        keywords.add("SHF:");
        keywords.add("POS:");
        int time = 0;
        String dir = "";
        PosterPoint pos = new PosterPoint();
        int shf = 0;
        PosterCondition con = new PosterCondition();
        Boolean hasif = false;

        int[] numTokens=new int[keywords.size()]; //ensure every field has exactly one token.
        for (String key : keywords) {
            String temp = tokenizer.getNext();
            if (temp.equals("TIMES:")) {
                time = Integer.parseInt(tokenizer.getAndCheckNext(CONST));
                numTokens[0]++;
            } else if (temp.equals("DIR:")) {
                dir = tokenizer.getNext();
                numTokens[1]++;
            } else if (temp.equals("POS:")) {
                String ps = tokenizer.getNext();
                ps = ps.replace("(", "");
                ps = ps.replace(")", "");
                String[] t = ps.split(",");
                for (int i = 0; i < t.length; i++) {
                    t[i] = t[i].trim();
                }
                int x = Integer.parseInt(t[0]);
                int y = Integer.parseInt(t[1]);
                int z = Integer.parseInt(t[2]);
                pos.setLocation(x, y, z);
                numTokens[2]++;
            }else {
                shf = Integer.parseInt(tokenizer.getAndCheckNext(CONST));
                numTokens[3]++;
            }
        }

        if(tokenizer.checkNext("IF")){
            tokenizer.getNext();
            hasif = true;
//              LOOP ([list], TIMES: 10, DIR: HOR/VER, SHF: 10, POS:(), IF(WIDTH, 200, >));
            String ifstatement = tokenizer.getNext();
            ifstatement = ifstatement.substring(1, ifstatement.length()-1);
            String[] splt_cond = ifstatement.split(",");
            String termA = splt_cond[0];
            termA = termA.trim();
            String termB = splt_cond[1];
            termB = termB.trim();
            String com = splt_cond[2];
            com = com.trim();
            con = new PosterCondition(termA, termB, com);
        }

        tokenizer.getAndCheckNext(";");

        PosterLOOP loop;
        if(hasif){
            loop = new PosterLOOP(varloop, time, dir, shf, pos);
        }else{
            loop = new PosterLOOP(varloop, time, dir, shf, pos, con);
        }

        if(!allValid(numTokens)){
            loop.setInvalid();
        }
        parsedList.add(loop);
        return loop;
    }

    private PosterText parseTXT() {
        tokenizer.getAndCheckNext("TXT");
        List<String> keywords = new ArrayList<>();
        keywords.add("FONTSIZE:");
        keywords.add("VAR:");
        keywords.add("POS:");
        keywords.add("CONTENT:");
        keywords.add("TCOLOR:");
        PosterPoint pos = new PosterPoint();
        String cont = "";
        String tcolor = "";
        int size = 0;
        String var = "";
        int[] numTokens=new int[keywords.size()]; //ensure every field has exactly one token.
        for (String key : keywords) {
            String check = tokenizer.getNext();
            if (check.equals("CONTENT:")) {
                cont = tokenizer.getNext().replaceAll("^\"|\"$", "");
                numTokens[0]++;
            } else if (check.equals("POS:")) {
                String ps = tokenizer.getNext();
                ps = ps.replace("(", "");
                ps = ps.replace(")", "");
                String[] temp = ps.split(",");
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = temp[i].trim();
                }
                int x = Integer.parseInt(temp[0]);
                int y = Integer.parseInt(temp[1]);
                int z = Integer.parseInt(temp[2]);
                pos.setLocation(x, y, z);
                numTokens[1]++;
            } else if (check.equals("FONTSIZE:")) {
                size = Integer.parseInt(tokenizer.getAndCheckNext(CONST));
                numTokens[2]++;
            } else if (check.equals("TCOLOR:")) {
                tcolor = tokenizer.getAndCheckNext(NAME);
                numTokens[3]++;
            } else {
                var = tokenizer.getAndCheckNext(NAME);
                numTokens[4]++;
            }
        }
        PosterText pt = new PosterText(var, pos, cont, size, tcolor);
        if(!allValid(numTokens)){
            pt.setInvalid();
        }
        parsedList.add(pt);
        tokenizer.getAndCheckNext(";");
        return pt;
    }

    private PosterImage parseIMG() {
        tokenizer.getAndCheckNext("IMG");
        List<String> keywords = new ArrayList<>();
        keywords.add("WIDTH:");
        keywords.add("HEIGHT:");
        keywords.add("VAR:");
        keywords.add("FILEPATH:");
        keywords.add("POS:");
        PosterPoint pos = new PosterPoint();
        String path = "";
        int width = 0;
        int height = 0;
        String var = "";
        int[] numTokens=new int[keywords.size()]; //ensure every field has exactly one token.
        for (String key : keywords) {
            String check = tokenizer.getNext();
            if (check.equals("FILEPATH:")) {
                path = tokenizer.getNext().replaceAll("^\"|\"$", "");
                numTokens[0]++;
            } else if (check.equals("POS:")) {
                String ps = tokenizer.getNext();
                ps = ps.replace("(", "");
                ps = ps.replace(")", "");
                String[] temp = ps.split(",");
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = temp[i].trim();
                }
                int x = Integer.parseInt(temp[0]);
                int y = Integer.parseInt(temp[1]);
                int z = Integer.parseInt(temp[2]);
                pos.setLocation(x, y, z);
                numTokens[1]++;
            } else if (check.equals("WIDTH:")) {
                width = Integer.parseInt(tokenizer.getAndCheckNext(CONST));
                numTokens[2]++;
            } else if (check.equals("HEIGHT:")) {
                height = Integer.parseInt(tokenizer.getAndCheckNext(CONST));
                numTokens[3]++;
            } else {
                var = tokenizer.getAndCheckNext(NAME);
                numTokens[4]++;
            }
        }
        PosterImage pi = new PosterImage(var, pos, width, height, path);
        if(!allValid(numTokens)){
            pi.setInvalid();
        }
        parsedList.add(pi);
        tokenizer.getAndCheckNext(";");
        return pi;
    }

    private PosterPanel parsePANEL() {
        tokenizer.getAndCheckNext("PANEL");
        List<String> keywords = new ArrayList<>();
        keywords.add("WIDTH:");
        keywords.add("HEIGHT:");
        keywords.add("VAR:");
        int width = 0;
        int height = 0;
        String var = "";
        int[] numTokens=new int[keywords.size()]; //ensure every field has exactly one token.
        for (String key : keywords) {
            String check = tokenizer.getNext();
            if (check.equals("WIDTH:")) {
                width = Integer.parseInt(tokenizer.getAndCheckNext(CONST));
                numTokens[0]++;
            } else if (check.equals("HEIGHT:")) {
                height = Integer.parseInt(tokenizer.getAndCheckNext(CONST));
                numTokens[1]++;
            } else {
                var = tokenizer.getAndCheckNext(NAME);
                numTokens[2]++;
            }
        }
        PosterPanel pp = new PosterPanel(var, width, height);
        if(!allValid(numTokens)){
            pp.setInvalid();
        }
        parsedList.add(pp);
        tokenizer.getAndCheckNext(";");
        return pp;
    }
    private boolean allValid(int[] set){
        for (int i: set){
            if(i!=1) return false;
        }
        return true;
    }
    public List<PosterObject> getParsedList() {
        return parsedList;
    }

}
