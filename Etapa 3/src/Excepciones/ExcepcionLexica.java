package Excepciones;

@SuppressWarnings("serial")
public class ExcepcionLexica extends Exception {
	
	public ExcepcionLexica (int nroLinea, int nroCol, char chr) {
		System.out.println("[Error] Error l�xico en l�nea: "+nroLinea);
		System.out.println(" [Error:"+chr+"|"+nroLinea+"NroLinea] ");
	}
	
	public ExcepcionLexica (String msg) {
		System.out.println(msg);
	}

}