package rs.ac.bg.etf.student.pp060115;

import java_cup.runtime.Symbol;


%%

%cup
%line
%column

%{
	StringBuffer string = new StringBuffer();
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}



%xstate COMMENT
%state	STRING


%eofval{
	return new_symbol(sym.EOF);
%eofval}



%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROGRAM, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"read" 		{ return new_symbol(sym.READ, yytext()); }
"if" 		{ return new_symbol(sym.IF, yytext()); }
"else" 		{ return new_symbol(sym.ELSE, yytext()); }
"break"		{ return new_symbol(sym.BREAK, yytext()); }
"class"		{ return new_symbol(sym.CLASS, yytext()); }
"extends"	{ return new_symbol(sym.EXTENDS, yytext()); }
"new"		{ return new_symbol(sym.NEW, yytext()); }
"while"		{ return new_symbol(sym.WHILE, yytext()); }
"do"		{ return new_symbol(sym.DO, yytext()); }
"void"		{ return new_symbol(sym.VOID, yytext()); }
"const"		{ return new_symbol(sym.CONST, yytext()); }
"return"	{ return new_symbol(sym.RETURN, yytext()); }

"+"			{ return new_symbol(sym.PLUS, yytext()); }
"-"			{ return new_symbol(sym.MINUS, yytext()); }
"*"			{ return new_symbol(sym.MULT, yytext()); }
"/"			{ return new_symbol(sym.DEV, yytext()); }
"%"			{ return new_symbol(sym.PERCENT, yytext()); }
"="			{ return new_symbol(sym.EQUALS, yytext()); }
"=="		{ return new_symbol(sym.EQUEQU, yytext()); }
"!="		{ return new_symbol(sym.NOTEQU, yytext()); }
">"			{ return new_symbol(sym.GREATER, yytext()); }
">="		{ return new_symbol(sym.GREEQU, yytext()); }
"<"			{ return new_symbol(sym.LESS, yytext()); }
"<="		{ return new_symbol(sym.LESEQU, yytext()); }
"&&"		{ return new_symbol(sym.AND, yytext()); }
"||"		{ return new_symbol(sym.OR, yytext()); }
"++"		{ return new_symbol(sym.INCREM, yytext()); }
"--"		{ return new_symbol(sym.DECREM, yytext()); }
";"			{ return new_symbol(sym.SEMICOL, yytext()); }
","			{ return new_symbol(sym.COMMA, yytext()); }
"."			{ return new_symbol(sym.PERIOD, yytext()); }
"("			{ return new_symbol(sym.LPAREN, yytext()); }
")"			{ return new_symbol(sym.RPAREN, yytext()); }
"{"			{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"["			{ return new_symbol(sym.LSQPAREN, yytext()); }
"]"			{ return new_symbol(sym.RSQPAREN, yytext()); }


"//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }


"false"|"FALSE"|"true"|"TRUE" {return new_symbol(sym.BOOLCONST,new Boolean(yytext()));}



"\"" . [^\"]* "\""	{ return new_symbol(sym.STRCONST, new String(yytext().substring(1,yytext().length()-1)) ); }


"'" [\040-\176] "'"  	{ return new_symbol(sym.CHARCONST,new Character( yytext().charAt(1)));}



[0-9]+ ([a-z]|[A-Z])[a-z|A-Z|0-9|_]* { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)+" i koloni "+(yycolumn+1));  }


[0-9]+  {  return new_symbol(sym.NUMBER, new Integer (yytext())); }

([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)+" i koloni "+(yycolumn+1));  }





