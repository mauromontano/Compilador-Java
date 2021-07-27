package TablaDeSimbolos;

public class TipoString extends TipoPrimitivo{
	
	public boolean esTipo(TipoMetodo tipo){
        return tipo instanceof TipoString;
    }
    
    public String getNombreTipo(){
        return "String";
    }
}
