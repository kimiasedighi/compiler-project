package compiler;

import compiler.AST.*;
import compiler.cgen.*;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class main {

    public static String run(java.io.File inputFile) throws Exception {
        //StringBuilder str = new StringBuilder();
        /* dshuisdhuihdsu/*fuihfuahuhd */
        PreScanner preScanner = null;
        Map<String, String> definemap;
        try {
            java.io.FileInputStream stream = new java.io.FileInputStream(inputFile);
            java.io.Reader reader = new java.io.InputStreamReader(stream);
            preScanner = new PreScanner(reader);

            while (!preScanner.zzAtEOF) preScanner.yylex();
            definemap = preScanner.definedMap;
            FileWriter fileWriter= new FileWriter(inputFile);
            fileWriter.write(preScanner.out.toString());
            fileWriter.flush();
            //--------------------------------------
            try {
                java.io.FileInputStream stream2 = new java.io.FileInputStream(inputFile);
                java.io.Reader reader2 = new java.io.InputStreamReader(stream2);
                preScanner = new PreScanner(reader2);
                preScanner.definedMap = definemap;
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
        LexerP scanner;
        try {
            java.io.FileInputStream stream = new java.io.FileInputStream(inputFile);
            java.io.Reader reader = new java.io.InputStreamReader(stream);
            scanner = new LexerP(reader);
            parser p = new parser(scanner);
            try {
                p.parse();
                ProgramNode root = p.getRoot();
                cgenVisitor cgenVisitor = new cgenVisitor();
                cgenVisitor.visit(root);
//                FileWriter fileWriter2= new FileWriter(inputFile);
//                fileWriter2.write(cgenVisitor.mipsCode);
//                fileWriter2.flush();
                return cgenVisitor.mipsCode;

            } catch (Exception ex){
                System.out.println(ex.getMessage());
                return "code did not compiled!";
            }

        } catch (Exception e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }

        return "code did not compiled!";
        //Laxer scanner = new Laxer(new FileReader(inputFile));
    }

    public static void main(String[] args) throws Exception {

        File file = new File(args[0]);
        String result= main.run(file);
        System.out.println(result);

        File outputFile = new File(args[1]);

        FileWriter fileWriter= new FileWriter(outputFile);
        fileWriter.write(result);
        fileWriter.flush();

    }
}
