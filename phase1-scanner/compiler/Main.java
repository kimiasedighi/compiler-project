package compiler;

import AST.ProgramNode;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class Main {

    public static boolean run(java.io.File inputFile) throws Exception {
        //StringBuilder str = new StringBuilder();
        /* dshuisdhuihdsu/*fuihfuahuhd */
        PreScanner preScanner = null;
        Map<String,String> definemap;
        try {
            java.io.FileInputStream stream = new java.io.FileInputStream(inputFile);
            java.io.Reader reader = new java.io.InputStreamReader(stream);
            preScanner = new PreScanner(reader);

            while (!preScanner.zzAtEOF) preScanner.yylex();
            definemap=preScanner.definedMap;
            FileWriter fileWriter= new FileWriter(inputFile);
            fileWriter.write(preScanner.out.toString());
            fileWriter.flush();
            //--------------------------------------
            try {
                java.io.FileInputStream stream2 = new java.io.FileInputStream(inputFile);
                java.io.Reader reader2 = new java.io.InputStreamReader(stream2);
                preScanner = new PreScanner(reader2);
                preScanner.definedMap=definemap;
                while (!preScanner.zzAtEOF) preScanner.yylex();

                FileWriter fileWriter2= new FileWriter(inputFile);
                fileWriter2.write(preScanner.out.toString());
                fileWriter2.flush();
                //--------------------------------------

            } catch (Exception e) {

                System.out.println("Unexpected exception:");
                e.printStackTrace();
            }

        } catch (Exception e) {

            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }

        //PreScanner preScanner = new PreScanner(new FileReader(inputFile));
        //new FileWriter(inputFile).write(preScanner.out.toString());
        LexerP scanner = null;
        try {
            java.io.FileInputStream stream = new java.io.FileInputStream(inputFile);
            java.io.Reader reader = new java.io.InputStreamReader(stream);
            scanner = new LexerP(reader);
            parser p = new parser(scanner);
            try {
                p.parse();
                ProgramNode root = p.getRoot();

                return true;
            }catch (Exception ex){
                System.out.println(ex.toString());
                return false;
            }

        } catch (Exception e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }

        return false;
        //Laxer scanner = new Laxer(new FileReader(inputFile));
    }

    public static void main(String[] args) throws Exception {
        /* dshuisdhuihdsu/*fuihfuahuhd */
        File file3 = new File("D:\\University-term7\\compiler-project\\phase1-scanner\\compiler\\test.txt");
        boolean result=Main.run(file3);

    }
}
