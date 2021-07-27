package Excepciones;


@SuppressWarnings("serial")
public class ExcepcionIdentificadorMalFormado extends ExcepcionLexica {

	public ExcepcionIdentificadorMalFormado (int nroLinea, String linea, char chr, int nroCol) {
		super(" Error lexico en linea '"+nroLinea+"': "+chr+" no es parte de un identificador bien formado");
		System.out.println(" Detalle: "+linea);
		
		String txt="";
		int i=0;
		while(i<10) {
			txt+=" ";
			i++;
		}
		
		int n= 0;
		char t=linea.charAt(n);
		while(t != chr) {         // si encuentro el id me detengo y apunto a este
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
