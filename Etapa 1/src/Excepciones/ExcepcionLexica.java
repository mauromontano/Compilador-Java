package Excepciones;

@SuppressWarnings("serial")
public class ExcepcionLexica extends Exception {
	
	public ExcepcionLexica (int nroLinea, int nroCol) {
		System.out.println("[Error] Error léxico en línea: "+nroLinea);
	}
	
	public ExcepcionLexica (String msg) {
		System.out.println(msg);
	}

}