package compiler;

import java.io.File;
import java.io.FileWriter;

public class Test {
    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\dell\\IdeaProjects\\compiler-project\\phase1-scanner\\compiler\\test.txt");
        String result= main.run(file);
        System.out.println(result);


    }
}
