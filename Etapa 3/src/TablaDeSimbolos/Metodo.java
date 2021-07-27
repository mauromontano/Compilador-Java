package TablaDeSimbolos;

import java.util.ArrayList;

import Estructuras.Token;
import Excepciones.ExcepcionSemantica;

public class Metodo extends Unidad{
	private final String enlace;
    private TipoMetodo tipoRetorno;

    public Metodo(Token id, String enlace, TipoMetodo tipoRetorno) {
        super(id);
        this.enlace = enlace;
        this.tipoRetorno = tipoRetorno;
    }

    public String getEnlace() {
        return enlace;
    }
    
    
    public String getNombreUnidad(){
        return "metodo";
    }
    
    public boolean mismoNombre(Metodo m){
        return m.getId().getLexema().equals(id.getLexema());
    }
    public void chequearTipoRetornoValido() throws ExcepcionSemantica{
        if(!tipoRetorno.esTipoValido())
            throw new ExcepcionSemantica("El tipo de retorno del metodo "+id.getLexema()+" no existe.", id.getLinea(), id.getLexema());
    }
        
    public TipoMetodo getTipoRetorno(){
        return tipoRetorno;
    }
    /**
     * Chequea la signatura del metodo de esta clase (que es de la superclase) con el metodo pasado por parametro (que es de la subclase)
     */
    public void chequarSignatura(Metodo m) throws ExcepcionSemantica{
        boolean hayErrorSublcase=false;
        String toErrSubclase="Se esta queriendo redefinir el metodo "+m.getId().getLexema()+" pero el mismo en la subclase debe tener:\n";
        @SuppressWarnings("unused")
		String errFinal="Se esta queriendo redefinir el metodo "+m.getId().getLexema()+" pero en la superclase el mismo no debe ser final.\n";
        if(!tipoRetorno.esTipo(m.getTipoRetorno())){
            toErrSubclase+="* El tipo de retorno "+tipoRetorno.getNombreTipo()+"\n";
            hayErrorSublcase=true;
        }
        
        if(!enlace.equals(m.getEnlace())){
            toErrSubclase+="* El modificador "+enlace+"\n";
            hayErrorSublcase=true;
         }
        if(m.cantidadParametros()!=parametros.size()){
            toErrSubclase+="* "+cantidadParametros()+" parametros\n";
            hayErrorSublcase=true;
        }
            if(m.cantidadParametros()>=parametros.size()){
            int index=0;
            for(Tipo tipoSuper: tiposOrdenados){
                if(!m.mismoTipo(index, tipoSuper)){
                    toErrSubclase+="* El tipo de "+m.getNombreArgumento(index)+" debe ser "+tipoSuper.getNombreTipo()+"\n";
                    hayErrorSublcase=true;
                }
                index=index+1;
            }
            }else{
                ArrayList<Tipo> tiposOrdenadosSubclase= m.getTiposOrdenados();
                int index=0;
                for(@SuppressWarnings("unused") Tipo tipoSub: tiposOrdenadosSubclase){
                    if(!m.mismoTipo(index, tiposOrdenados.get(index))){
                        toErrSubclase+="* El tipo de "+m.getNombreArgumento(index)+" debe ser "+tiposOrdenados.get(index).getNombreTipo()+"\n";
                        hayErrorSublcase=true;
                    }
                    index=index+1;
                }
            }
         if(hayErrorSublcase)
             throw new ExcepcionSemantica(toErrSubclase, m.getId().getLinea(), m.getId().getLexema());
        
    }
}
