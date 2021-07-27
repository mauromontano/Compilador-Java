package TablaDeSimbolos;

public class Void extends TipoMetodo{
	
	public boolean esTipo(TipoMetodo tipo){
        return tipo instanceof Void;
    }
   
    
    
    public String getNombreTipo(){
        return "Void";
    }
}
