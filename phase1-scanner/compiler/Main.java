package compiler;

import java.io.FileWriter;

public class Main {

    public static boolean run(java.io.File inputFile) throws Exception {
        //StringBuilder str = new StringBuilder();
        /* dshuisdhuihdsu/*fuihfuahuhd */
        PreScanner preScanner = null;
        try {
            java.io.FileInputStream stream = new java.io.FileInputStream(inputFile);
            java.io.Reader reader = new java.io.InputStreamReader(stream);
            preScanner = new PreScanner(reader);
            while (!preScanner.zzAtEOF) preScanner.yylex();
            FileWriter fileWriter= new FileWriter(inputFile);
            fileWriter.write(preScanner.out.toString());
            fileWriter.flush();
            //System.out.println(preScanner.out.toString());
            String sdsa = preScanner.out.toString();

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
                return true;
            }catch (Exception ex){
                System.out.println(ex.toString());
                return false;
            }
            //.out.println(scanner.out.toString());
            //return scanner.out.toString();
        } catch (Exception e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }

        return false;
        //Laxer scanner = new Laxer(new FileReader(inputFile));
    }
}