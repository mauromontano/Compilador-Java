package TablaDeSimbolos;

public class TipoClase extends Tipo{
	private String clase;

    public TipoClase(String clase) {
        this.clase = clase;
    }
    
    
    public boolean esTipo(TipoMetodo tipo){
        return tipo instanceof TipoClase;
    }
    
    
    
    public boolean esTipoValido(){
    	if(Modulos.AnalizadorSintactico.ts.existeClase(clase))
    		return true;
        else
            return false;
    }
    
    
    public String getNombreTipo(){
        return clase;
    }
}
