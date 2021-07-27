package Excepciones;

@SuppressWarnings("serial")
public class ExcepcionCharMalFormado extends ExcepcionLexica {
	
	public ExcepcionCharMalFormado (int nroLinea, String linea, char chr, int nroCol) {
		super(" Error lexico en linea '"+nroLinea+"': "+chr+" no es parte de un caracter bien formado");
		System.out.println(" [Error:"+chr+"|"+nroLinea+"] ");
		System.out.println(" Detalle: "+linea);
		//System.out.println(" columna: "+nroCol);
		
		int n= 0;
		String txt="";
		int i=0;
		while(i<10) {
			txt+=" ";
			i++;
		}
		
		char t=linea.charAt(n);
		
		if(t == linea.charAt(1)) { //caso especial: ''
			txt+=" ";
			txt+="^";
			System.out.println(txt);
		}
		else {
			while(t != chr) {
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
}
