package rs.ac.bg.etf.student.pp060115;
// Citaj.java - Citanje podataka standardnih tipova iz ulaznog toka,
//              iz datoteke i s glavnog ulaza.

import  java.io.*;

public class Citaj {

  private InputStream ut;    // Ulazni tok iz kojeg se cita.
  private char c;            // Poslednji procitani znak.
  private boolean eos;       // Indikator kraja toka.

  public Citaj(InputStream uut) { ut = uut; }  // Inicijalizacija
                                               //   ulaznim tokom.
  public Citaj(String ime) throws FileNotFoundException  // Otvaranje
    { ut = new FileInputStream(ime); }                   //   datoteke.

  public boolean endS() { return eos; }        // Da li je kraj toka?

  public char getChS() {     // Dohvatanje sledeceg znaka.
    try { int i = ut.read(); return c = (eos = i == -1) ? ' ' : (char)i; }
      catch (Exception g) { eos = true; return c = ' '; }
  }

  public char CharS() {      // Citanje jednog (nebelog) znaka.
    while (Character.isWhitespace(c = getChS()));
    return !eos ? c : ' ';
  }

  public String StringS() {  // Citanje jedne reci.
    String s = "";
    while ( Character.isWhitespace(c = getChS()) && !eos);
    if (eos) return "";
    s += c;
    while (!Character.isWhitespace(c = getChS()) && !eos) s += c;
    eos = false;
    return s;
  }

  public String LineS() {     // Citanje jednog reda teksta.
    String s="";
    while ((c = getChS()) != '\n' && !eos) if (c != '\r') s += c;
    if (s.length() != 0) eos = false;
    return s;
  }

  public void getNLS()       // Preskakanje znakova do kraja reda.
    { while (c!='\n' && !eos) c = getChS(); c = '\0'; }

  public byte   ByteS    ()  // Citanje jednog podatka tipa byte.
    { String s = StringS (); return !eos ? Byte.parseByte(s) : 0; }

  public short  ShortS   ()  // Citanje jednog podatka tipa short.
    { String s = StringS (); return !eos ? Short.parseShort(s) : 0; }

  public int    IntS     ()  // Citanje jednog podatka tipa int.
    { String s = StringS (); return !eos ? Integer.parseInt(s) : 0; }

  public long   LongS    ()  // Citanje jednog podatka tipa long.
    { String s = StringS (); return !eos ? Long.parseLong(s) : 0; }

  public float  FloatS   ()  // Citanje jednog podatka tipa float.
    { String s = StringS (); return !eos ? Float.parseFloat(s) : 0; }

  public double DoubleS  ()  // Citanje jednog podatka tipa double.
    { String s = StringS (); return !eos ? Double.parseDouble(s) : 0; }

  public boolean BooleanS()  // Citanje jednog podatka tipa boolean.
    { String s = StringS (); return !eos ? Boolean.parseBoolean(s) : false; }

  // PODRSKA ZA CITANJE S GLAVNOG ULAZA.

  private static Citaj gl = new Citaj(System.in);   // Predstavnik
                                                     //   glavnog ulaza.
  public static boolean end    () { return gl.endS    (); } // Varijante
  public static char    getCh  () { return gl.getChS  (); } //   metoda
  public static char    Char   () { return gl.CharS   (); } //   koje
  public static String  String () { return gl.StringS (); } //   citaju sa
  public static String  Line   () { return gl.LineS   (); } //   glavnog
  public static void    getNL  () {        gl.getNLS  (); } //   ulaza.
  public static byte    Byte   () { return gl.ByteS   (); }
  public static short   Short  () { return gl.ShortS  (); }
  public static int     Int    () { return gl.IntS    (); }
  public static long    Long   () { return gl.LongS   (); }
  public static float   Float  () { return gl.FloatS  (); }
  public static double  Double () { return gl.DoubleS (); }
  public static boolean Boolean() { return gl.BooleanS(); }
}