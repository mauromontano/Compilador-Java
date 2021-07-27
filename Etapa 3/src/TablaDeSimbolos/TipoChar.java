package TablaDeSimbolos;

public class TipoChar extends TipoPrimitivo{
	
	public boolean esTipo(TipoMetodo tipo){
        return tipo instanceof TipoChar;
    }
    
    
    public String getNombreTipo(){
        return "char";
    }
}
