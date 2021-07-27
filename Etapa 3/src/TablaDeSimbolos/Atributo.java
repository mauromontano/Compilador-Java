package TablaDeSimbolos;

import Estructuras.Token;

public class Atributo extends Variable{
	
	@SuppressWarnings("unused")
	private String visibilidad;
    public Atributo(Token id, Tipo tipo,String visibilidad) {
        super(id, tipo);
        this.visibilidad=visibilidad;
    }
}
