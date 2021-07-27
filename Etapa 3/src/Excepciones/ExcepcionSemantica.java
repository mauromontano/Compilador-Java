package Excepciones;



@SuppressWarnings("serial")
public class ExcepcionSemantica extends Excepcion{
	private final String error;
    private final int nroLinea;
    private final String lexema;
    
    public ExcepcionSemantica(String error,int nroLinea, String lexema){
        this.error= error;
        this.nroLinea= nroLinea;
        this.lexema= lexema;
    }
    /*
    public ExcepcionSemantica(String error){
        this.error=error;
        nroLinea=-1;
        token= null;
    }*/
    
    
    public String getError() { 
    	System.out.println("Error semantico en linea "+nroLinea+": "+error);
        return "[Error:"+lexema+"|"+nroLinea+"]";      
    }
}
