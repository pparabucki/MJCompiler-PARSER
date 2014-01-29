package rs.ac.bg.etf.student.pp060115;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java_cup.runtime.Symbol;


public class MJParserTest {
	public static void main(String[] args) throws Exception{
		
		Reader br = null;
		try{
			File sourceCode = new File("test/program.mj");
			System.out.println("Compiling...");
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			MJParser p = new MJParser(lexer);
			Symbol s = p.parse();
		}
		finally {
			if( br != null ) try { br.close(); } catch (IOException e1) {e1.printStackTrace();}
		}
		
	}
}
