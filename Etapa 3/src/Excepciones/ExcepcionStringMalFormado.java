package Excepciones;

@SuppressWarnings("serial")
public class ExcepcionStringMalFormado extends ExcepcionLexica {
	
	public ExcepcionStringMalFormado (int nroLinea, String linea, char chr, int nroCol) {
		super(" Error lexico en linea '"+nroLinea+"': "+chr+" no es parte de un string bien formado");
		System.out.println(" [Error:"+chr+"|"+nroLinea+"] ");
		System.out.println(" Detalle: "+linea);
		
		//Agrego los espacios del principio hasta que arranca la linea
		String txt="";
		int i=0;
		while(i<10) {
			txt+=" ";
			i++;
		}
		
		int n= 1;
		
		while(n < nroCol) {
			n++;
			txt+=" ";
		}
		txt+="^";
		
		System.out.println(txt);
	}
}