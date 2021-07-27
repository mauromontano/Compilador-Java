package Excepciones;

@SuppressWarnings("serial")
public class ExcepcionComentarioMalFormado extends ExcepcionLexica {
	
	public ExcepcionComentarioMalFormado (int nroLinea, String linea, char chr, int nroCol) {
		super(" Error lexico en linea '"+nroLinea+"': "+chr+" no es parte de un comentario bien formado");
		System.out.println(" [Error:"+chr+"|"+nroLinea+"] ");
		System.out.println(" Detalle: "+linea);
		
		int n= 0;
		String txt="";
		int i=0;
		while(i<10) {
			txt+=" ";
			i++;
		}
		
		// caso comentario multinea
		while(n < (linea.length()-1)) {
			n++;
			txt+=" ";
			//t= linea.charAt(n);
		}
		txt+="^";
		System.out.println(txt);
	}
	
}

