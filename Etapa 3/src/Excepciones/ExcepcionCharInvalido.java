package Excepciones;


@SuppressWarnings("serial")
public class ExcepcionCharInvalido extends ExcepcionLexica {

	public ExcepcionCharInvalido(int nroLinea, String linea, char chr, int nroCol) {
		super(" Error lexico en linea '"+nroLinea+"': "+chr+" no es un caracter valido");
		System.out.println(" [Error:"+chr+"|"+nroLinea+"] ");
		System.out.println(" Detalle: "+linea);
		
		String txt="";
		int i=0;
		while(i<10) {
			txt+=" ";
			i++;
		}
		
	    int n= 0;
		char t=linea.charAt(n);
		while(t != chr) {             // si encuentro el char invalido me detengo y apunto a este
			if(t == '\t')
				txt+=""+'\t';
			else
				txt+=" ";
			n++;
			t=linea.charAt(n);
		}
		
		txt+="^";
		System.out.println(txt);
	}
	
}