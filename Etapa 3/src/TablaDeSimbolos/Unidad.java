package TablaDeSimbolos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Estructuras.Token;
import Excepciones.ExcepcionSemantica;

public class Unidad {
	
	protected Map<String,Variable> parametros;
    //Hay que agregar la lista ordenada
    protected Token id;
    protected ArrayList<Tipo> tiposOrdenados;
    protected ArrayList<String> nombresArgumentos;
    
    
    public Unidad (Token id ){
        this.id=id;
        parametros = new HashMap<String,Variable>();
        tiposOrdenados= new ArrayList<Tipo>();
        nombresArgumentos= new ArrayList<String>();
    }
    
    protected Token getId(){
        return id;
    }
    
    public String getNombreUnidad(){
        return "Metodo"; //Me salva de errores
    }
    
    public void insertarArgumento(Variable v) throws ExcepcionSemantica{
        //Chequeamos que el nombre del parámetro no sea el mismo que otro
        if(parametros.containsKey(v.getId().getLexema())){
            throw new ExcepcionSemantica("En el "+getNombreUnidad()+" "+id.getLexema()+" el parametro "+v.getId().getLexema()+" esta repetido.", v.getId().getLinea(),v.getId().getLexema());
        }
        parametros.put(v.getId().getLexema(),v);
        tiposOrdenados.add(v.getTipo());
        nombresArgumentos.add(v.getId().getLexema());
    }
    
    public boolean mismoTipo(int index, Tipo aComparar){
        boolean toRet=false;
        if(tiposOrdenados.get(index).esTipo(aComparar))
            toRet=true;
        return toRet;
    }
    
    public String getNombreArgumento(int index){
        return nombresArgumentos.get(index);
    }
    
    public int cantidadParametros(){
        return  parametros.size();
    }
    
    public void chequearTiposArgumentos() throws ExcepcionSemantica{
        for(Variable v: parametros.values()){
            if(!v.esTipoValido()){
               throw new ExcepcionSemantica("El tipo "+v.getTipo().getNombreTipo()+" definido en el parametro "+v.getId().getLexema()+" no existe.", id.getLinea(), id.getLexema());

            }
        }
    }
    
    public ArrayList<Tipo> getTiposOrdenados(){
        return tiposOrdenados;
    }
}
