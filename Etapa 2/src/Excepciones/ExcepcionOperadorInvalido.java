package Excepciones;

@SuppressWarnings("serial")
public class ExcepcionOperadorInvalido extends ExcepcionLexica {
	
	public ExcepcionOperadorInvalido(int nroLinea, String linea, char chr, int nroCol) {
		super(" Error lexico en linea '"+nroLinea+"': "+chr+" no es un operador valido");
		System.out.println(" [Error:"+chr+"|"+nroLinea+"] ");
		System.out.println(" Detalle: "+linea);
		
		int n= 0;
		
		String txt="";
		int i=0;
		while(i<10) {
			txt+=" ";
			i++;
		}
		
		char t=linea.charAt(n);
		while(t != chr) {          // si encuentro el operador me detengo y apunto a este
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
