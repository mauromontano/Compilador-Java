package TablaDeSimbolos;

public class TipoEntero extends TipoPrimitivo{
	
	public boolean esTipo(TipoMetodo tipo){
        return tipo instanceof TipoEntero;
    }
    
    
    public String getNombreTipo(){
        return "int";
    }
}
