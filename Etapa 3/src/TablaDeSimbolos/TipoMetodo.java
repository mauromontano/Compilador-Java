package TablaDeSimbolos;

import Estructuras.Token;

public class TipoMetodo {
	protected Token id;
    
    public boolean esTipo(TipoMetodo tipo){
        return false;
    }
    
    public boolean esTipoValido(){
        return true;
    }
    
    public String getNombreTipo(){
        return null;
    }
}
