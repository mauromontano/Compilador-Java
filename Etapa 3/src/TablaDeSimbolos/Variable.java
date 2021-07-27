package TablaDeSimbolos;

import Estructuras.Token;
import Excepciones.ExcepcionSemantica;

public class Variable {
	protected Token id;
    protected Tipo tipo;
    
    public Variable(Token id, Tipo tipo){
        this.id=id;
        this.tipo=tipo;
    }

    public Token getId() {
        return id;
    }
    public Tipo getTipo(){
        return tipo;
    }
    
    public boolean esTipoValido() throws ExcepcionSemantica{
        if(!tipo.esTipoValido()){
            return false;
        }
        else
            return true;
    }
}
