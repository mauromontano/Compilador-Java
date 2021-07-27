package Estructuras;

public class Token {
	
	private String nombre;
	private String lexema;
	private int nroLinea;
	private int nroCol;
	
	public Token(String nombre, String lexema, int nroLinea, int nroCol) {
		this.nombre = nombre;
		this.lexema = lexema;
		this.nroLinea = nroLinea;
		this.nroCol= nroCol;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public int getLinea() {
		return nroLinea;
	}
	
	public int getColumna() {
		return nroCol;
	}
}