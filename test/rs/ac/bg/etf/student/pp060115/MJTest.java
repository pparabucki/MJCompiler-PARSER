package rs.ac.bg.etf.student.pp060115;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

import java_cup.runtime.Symbol;


//LEXER GENERATOR -d src\rs\ac\bg\etf\student\pp060115 spec\mjlexer.flex

public class MJTest {
	
	
	public static void main(String[] args) throws IOException {
	
		Reader br = null;
				
		try {
			System.out.println("Unesite ime programa koji je potrebno leksicki ispitati:\n(NIJE POTREBNO UNETI .mj pretpostavlja se da ce se uneti pravilno ime) ");
			String pName = Citaj.String();
			
			System.out.print("Unesite 0 - za stampanje na konzoli ili 1 - za pravljenje log fajlova: ");
			short odg = Citaj.Short();				
			if(odg==0){
				System.out.println("Sledi ispis tokena i gresaka na konzoli:");
				
				File sourceCode = new File("test/"+pName+".mj");	
				
				br = new BufferedReader(new FileReader(sourceCode));
				
				Yylex lexer = new Yylex(br);
				Symbol currToken = null;
				while ((currToken = lexer.next_token()).sym != sym.EOF) {
					if (currToken != null && currToken.value != null){
						
						System.out.println("INFO"+ System.currentTimeMillis() +" " + currToken.toString() + " " + currToken.value.toString()); 	
					}
				}
			
			}else if(odg==1){
				System.out.println("Sledi upis u fajlove, "+pName+".err i "+pName+".out");
				
				File file = new File("./test/"+pName+".err");
				FileOutputStream fos = new FileOutputStream(file);
				PrintStream ps = new PrintStream(fos);
				System.setErr(ps);
				
				File file1 = new File("./test/"+pName+".out");
				FileOutputStream fos1 = new FileOutputStream(file1);
				PrintStream ps1 = new PrintStream(fos1);
				System.setOut(ps1);
				
				File sourceCode = new File("test/"+pName+".mj");	
				
				br = new BufferedReader(new FileReader(sourceCode));
				
				Yylex lexer = new Yylex(br);
				Symbol currToken = null;
				while ((currToken = lexer.next_token()).sym != sym.EOF) {
					if (currToken != null && currToken.value != null){
						
						ps1.println("INFO "+ System.currentTimeMillis()  +" " + currToken.toString() + " " + currToken.value.toString()); 	// moj upis u fajl *.out
					}
				}
				
			}else{
				System.out.println("Pogresan unos sledi izlazak iz programa.");
				System.exit(1);
			}
			
			
		} 
		finally {
			if (br != null) 
				try { br.close(); } 
			catch (IOException e1) {
			}
		}
	}
	
}
