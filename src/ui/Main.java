package ui;

import modules.PosterParse;
import modules.PosterTokenize;

import java.util.Arrays;
import java.util.List;

class Main {
	public static void main(String[] args) {
//        List<String> fixedLiterals = Arrays.asList("PANEL", "WIDTH:", "HEIGHT:", "VAR:", "FILEPATH:", "POS:", "IMG", "TXT",
//                "CONTENT:", "FONTSIZE:", ";", "TIMES:" , "DIR:", "SHF:", "IF", "THEN" , "HOR", "VER", "LOOP", "ELSE");
////        List<String> fixedLiterals = Arrays.asList("PANEL", "WIDTH:", "HEIGHT:", "VAR:", "FILEPATH:", "POS:", "IMG", "TXT",
////                "CONTENT:", "FONTSIZE:", ";", "TIMES:" , "DIR:", "SHF:", "IF", "THEN" , "HOR", "VER", "LOOP");
//        PosterTokenize tokenizer = new PosterTokenize("PANEL     HEIGHT: 700, VAR: test ,WIDTH: 500;\n" +
//                "IMG FILEPATH: \"src/imgSource/dog.jpg\", POS: (50,50), WIDTH: 100, HEIGHT: 100, VAR: doggy;\n" +
//                "TXT CONTENT: \"This is a dog\", POS: (20,20), FONTSIZE: 20, VAR: txtDog;\n" +
//                "IF (doggy.WIDTH, test.WIDTH / 2, <), THEN (doggy.WIDTH = doggy.WIDTH * 2) ;\n" +
//                "LOOP [doggy, txtDog],  DIR: HOR,TIMES: 3, SHF: 10, POS: (10,  5);", fixedLiterals);
//        System.out.println("Done tokenization!!");
//        String t = "abc";
//        String[] aa = t.split(",");
//        System.out.println(Arrays.asList(aa));

////
//        PosterParse parser = new PosterParse(tokenizer);
//        parser.run();
//        System.out.println("Done parsing!!");

//        PosterEvaluate evaluater = new PosterEvaluate(parser.getParsedList());
//        evaluater.evaluate();

         new IDE();
    }
}