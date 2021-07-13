package modules;

import ui.UserException;

import java.util.Arrays;
import java.util.List;

public class PosterTokenize{

    private static final String RESERVEDWORD = "@@@";

    private String input;
    private List<String> fixedLiterals;
    private String[] tokens;
    private int currentToken = 0;

    public PosterTokenize(String input, List<String> fixedLiteralsList){
        this.input = input;
        fixedLiterals = fixedLiteralsList;
        tokenize();
    }

    private void tokenize() {
    //0. Pick some RESERVEDWORD (string which never occurs in your input) we pick @@@
    //1. Read the whole program into a single string; kill the newlines
        String tokenizedProgram = input.replace("\n", "");
        tokenizedProgram = tokenizedProgram.replaceAll("\\s+", " ");
//        System.out.println(tokenizedProgram);
    //2. Replace all constant literals with “RESERVEDWORD”<the literal>“RESERVEDWORD”
        for(String s : fixedLiterals) {
            tokenizedProgram = tokenizedProgram.replace(s, RESERVEDWORD + s + RESERVEDWORD);
//            System.out.println(tokenizedProgram);
        }
    //3. Replace all “RESERVEDWORDRESERVEDWORD” with just “RESERVEDWORD”
    //   Replace all “RESERVEDWORD,RESERVEDWORD” with just “RESERVEDWORD”
    //   Replace all “RESERVEDWORD RESERVEDWORD” with just “RESERVEDWORD”
        tokenizedProgram = tokenizedProgram.replace(RESERVEDWORD+RESERVEDWORD,RESERVEDWORD);
        tokenizedProgram = tokenizedProgram.replace(RESERVEDWORD+" "+RESERVEDWORD,RESERVEDWORD);
        tokenizedProgram = tokenizedProgram.replace(RESERVEDWORD+","+RESERVEDWORD,RESERVEDWORD);
//        System.out.println(tokenizedProgram);
    //4. Remove leading “@@@” character, then split on “@@@”
        if(tokenizedProgram.length() > 0 && tokenizedProgram.startsWith(RESERVEDWORD)) {
            tokenizedProgram = tokenizedProgram.substring(RESERVEDWORD.length()); // without first character
        }
        tokens = tokenizedProgram.split(RESERVEDWORD);
//        System.out.println(Arrays.asList(tokens));
    //5. Trim whitespace around tokens
    // remove the leading "," in any token
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
            int length = tokens[i].length();
            if (tokens[i].substring(length-1).equals(",")) {
                tokens[i] = tokens[i].substring(0, length-1);
                tokens[i] = tokens[i].trim();
            }
        }
        System.out.println(Arrays.asList(tokens));
    }


    public String getNext() {
        String token="";
        if (currentToken<tokens.length){
            token = tokens[currentToken];
            currentToken++;
        }
        else
            token="NULLTOKEN";
        return token;
    }


    public boolean checkToken(String regexp) {
        String s = checkNext();
//        System.out.println("comparing: |"+s+"|  to  |"+regexp+"|");
        return (s.matches(regexp));
    }

    private String checkNext() {
        String token="";
        if (currentToken<tokens.length){
            token = tokens[currentToken];
        }
        else
            token="NO_MORE_TOKENS";
        return token;
    }


    public String getAndCheckNext(String regexp) {
        String s = getNext();
        if (!s.matches(regexp)) {
            throw new UserException("Unexpected next token for Parsing! Expected something matching: " + regexp + " but got: " + s);
        }
//        System.out.println("matched: "+s+"  to  "+regexp);
        return s;
    }

    public Boolean checkNext(String n){
        System.out.println(checkNext());
        return checkNext().equals(n);
    }



    public boolean moreTokens() {
        return currentToken<tokens.length;
    }

    public String[] getTokens() { return tokens; }
}
