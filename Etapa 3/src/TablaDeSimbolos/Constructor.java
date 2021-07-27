package TablaDeSimbolos;

import Estructuras.Token;

public class Constructor extends Unidad{
	public Constructor(Token id) {
        super(id);
    }
    
    
    public String getNombreUnidad(){
        return "constructor";
    }
}
