package TablaDeSimbolos;

public class TipoBoolean extends TipoPrimitivo{
	
	public boolean esTipo(TipoMetodo tipo){
        return tipo instanceof TipoBoolean;
    }
    
    
    public String getNombreTipo(){
        return "boolean";
    }
}
