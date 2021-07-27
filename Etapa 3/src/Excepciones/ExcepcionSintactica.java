package Excepciones;

import Estructuras.Token;

@SuppressWarnings("serial")
public class ExcepcionSintactica extends Excepcion{
	
	private final Token encontrado;
    private final String esperado;
    
    public ExcepcionSintactica(Token encontrado, String esperado) {
        this.encontrado = encontrado;
        this.esperado=esperado;
    }
    
    public ExcepcionSintactica(Token encontrado){
        this.encontrado=encontrado;
        esperado=null;
    }
    
    public String getError() {
        String encontradoMostrar="";
        if(encontrado.getNombre().equals("EOF"))
			return "[Error:|1]";
		else 
            encontradoMostrar=encontrado.getLexema();
        if(esperado!=null) {
        	System.out.println("Error Sintactico en linea "+encontrado.getLinea()+": se esperaba "+esperado+" se encontro "+encontradoMostrar);
        	return "[Error:"+encontradoMostrar+"|"+encontrado.getLinea()+"]";
        }
        else {
        	System.out.println("Error Sintactico en linea "+encontrado.getLinea()+": se encontro "+encontradoMostrar);
    		return "[Error:"+encontradoMostrar+"|"+encontrado.getLinea()+"]";
        }
    
    
    }
}
