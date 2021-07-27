package TablaDeSimbolos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Estructuras.Token;
import Excepciones.ExcepcionSemantica;

public class Clase {
	   private Token id;
	   private String extiende;
	   private Map<String,Metodo> metodos;
	   private Constructor miConstructor;
	   private ArrayList<Constructor> Constructores;
	   private Map<String,Atributo> atributos;
	   private boolean actualizada;
	   
	   public Clase(Token id) throws ExcepcionSemantica{
	       this.id=id;
	       metodos = new HashMap<String,Metodo>();
	       atributos = new HashMap<String,Atributo>();
	       actualizada= false;
	       miConstructor= null;
	       Constructores= new ArrayList<Constructor>();
	       extiende=null;
	   }

	    public Token getId() {
	        return id;
	    }

	    public boolean isActualizada() {
	        return actualizada;
	    }

	    public void setActualizada(boolean actualizada) {
	        this.actualizada = actualizada;
	    }
	    	
	   
	   public void setHerencia(String extiende) throws ExcepcionSemantica{
	       this.extiende=extiende;
	   }
	   
	   
	   public Metodo hayMain(){
	       for(Metodo m : metodos.values()){
	       if(m.getEnlace().equals("static") && m.getId().getLexema().equals("main")){
	           //el metodo estatico main no debe contener parametros
	           if(m.cantidadParametros()==0)
	               return m;
	       }
	       }
	       return null;
	   }
	   
	   
	   public void insertarMetodo(Metodo m) throws ExcepcionSemantica{	   
		   
	       //No pueden haber dos metodos con el mismo nombre
	       if(metodos.containsKey(m.getId().getLexema())){
	           throw new ExcepcionSemantica("Ya existe el metodo "+m.getId().getLexema()+" en la clase "+id.getLexema()+".",m.getId().getLinea(),m.getId().getLexema());
	       }
	       
		   
	       //Si el metodo tiene el mismo nombre que un atributo
	       if(atributos.containsKey(m.getId().getLexema())){
	           throw new ExcepcionSemantica("Un metodo no puede tener el mismo nombre que un atributo.", m.getId().getLinea(),m.getId().getLexema());
	       }
	       
	       
	       metodos.put(m.getId().getLexema(), m);
	   }
	   
	   
	   public void insertarAtributo(Atributo a) throws ExcepcionSemantica{
	       //No pueden haber dos atributos con el mismo nombre
	       if(atributos.containsKey(a.getId().getLexema())){
	           throw new ExcepcionSemantica("Ya existe el atributo "+a.getId().getLexema()+" en la clase "+id.getLexema()+".",a.getId().getLinea(),a.getId().getLexema());
	       }
	       //El atributo no puede tener el mismo nombre que un metodo
	       if(metodos.containsKey(a.getId().getLexema())){
	           throw new ExcepcionSemantica("Un atributo no puede tener el mismo nombre que un metodo.",a.getId().getLinea(),a.getId().getLexema());
	       }
	       atributos.put(a.getId().getLexema(), a);
	   }
	   
	   public void insertarConstructor(Constructor c) throws ExcepcionSemantica{
	       //El constructor debe tener el mismo nombre que la clase
	       if(miConstructor!=null)
	           throw new ExcepcionSemantica("Ya existe un constructor en esa clase.", c.getId().getLinea(), c.getId().getLexema());
	       if(!c.getId().getLexema().equals(id.getLexema()))
	           throw new ExcepcionSemantica("El constructor debe tener el mismo nombre que la clase.", c.getId().getLinea(), c.getId().getLexema());
	       miConstructor=c;
	   }
	   
	   public void consolidar() throws ExcepcionSemantica{
		  //Veo si ancestro esta actualizado
		  if(!actualizada){
		        Clase claseExtiende = Modulos.AnalizadorSintactico.ts.getClase(extiende);
		   
	      //La clase que hereda debe ser una clase existente    
	      if(!Modulos.AnalizadorSintactico.ts.existeClase(extiende) && !id.getLexema().equals("Object")){
	          throw new ExcepcionSemantica("La clase heredada "+extiende+" no existe.", id.getLinea(),id.getLexema());
	      }
	      
	      //Herencia circular
	      ArrayList<String> cadenaHerencia= new ArrayList<String>();
	      cadenaHerencia.add(id.getLexema());
	      claseExtiende.herenciaCircular(cadenaHerencia);           
	       
	      //Consolido los atributos:
	       for(Atributo a: atributos.values()){
	           //Deben tener un tipo valido
	           if(!a.esTipoValido()){
	               throw new ExcepcionSemantica("El tipo "+a.getTipo().getNombreTipo()+" definido en el atributo "+a.getId().getLexema()+" no existe.", id.getLinea(), id.getLexema());
	           }      
	       } 
	       
	       //Consolido los metodos
	       for(Metodo m: metodos.values()){
	           //El tipo de retorno del metodo debe ser de un tipo valido
	           m.chequearTipoRetornoValido();
	           //El tipo de los argumentos del metodo debe ser de un tipo valido
	           m.chequearTiposArgumentos();
	           //Reviso redifinicion del metodo
	           claseExtiende.chequearRedifinicionMetodo(m); 
	       }
	       
	       
	       //Chequeo el constructor
	       if(Constructores.size()!=0) {
	    	   for(Constructor miConstructor : Constructores)
	    		   miConstructor.chequearTiposArgumentos();
	       }
	       else
	    	  Constructores.add(new Constructor(new Token("PR_idMetVar",id.getLexema(),0,0)));
	       
	       //Debo agregar los metodos de la superclase
	       Map<String,Metodo> metodosExtiende=claseExtiende.getMetodos();
	       for(Metodo m: metodosExtiende.values()){
	           if(!metodos.containsKey(m.id.getLexema()))
	               metodos.put(m.id.getLexema(), m);
	       }
	       //Debo agregar los atributos de la superclase
	       Map<String,Atributo> atributosExtiende= claseExtiende.getAtributos();
	       for(Atributo a: atributosExtiende.values()){
	           atributos.put(a.id.getLexema(), a);
	       }
	      actualizada=true;
	     
		  }
	    
	   }
	   
	   
	   public Map<String,Metodo> getMetodos(){
	       return metodos;
	   }
	   
	   public Map<String,Atributo> getAtributos(){
	       return atributos;
	   }

	   
	   public void herenciaCircular(ArrayList<String> cadenaHerencia) throws ExcepcionSemantica{
	       cadenaHerencia.add(id.getLexema()); //Me agrego a la cadena de herencia
	       if(!actualizada){
	            if(cadenaHerencia.get(0).equals(id.getLexema())){
	            //Quiere decir que hay herencia circular o hereda de si mismo
	                if(cadenaHerencia.size()==2){
	                    //Solamente tenemos la clase inicial y la clase que hereda y son las mismas
	                    //Por lo tanto hereda de si misma
	                    throw new ExcepcionSemantica("La clase no puede heredar de si misma.", id.getLinea(), id.getLexema());
	                }
	                else{
	                    String toErr="Se produjo herencia circular entre las siguientes clases: [";
	                    for(int i=0; i<cadenaHerencia.size() ;i++){
	                        toErr+=cadenaHerencia.get(i);
	                        if(i<(cadenaHerencia.size()-1))
	                            toErr+=",";
	                    }
	                    toErr+="]";
	                    throw new ExcepcionSemantica(toErr,id.getLinea(),id.getLexema());
	                }
	            }else{
	                Clase claseExtiende=Modulos.AnalizadorSintactico.ts.getClase(extiende);
	                claseExtiende.herenciaCircular(cadenaHerencia);
	                actualizada=true;
	            }
	       }
	   }
	   
	   public void chequearRedifinicionMetodo(Metodo aChequear) throws ExcepcionSemantica{
	       for( Metodo m: metodos.values()){
	           
	           if(m.mismoNombre(aChequear)){
	               m.chequarSignatura(aChequear);
	               //Entonces este metodo no debe ser final
	           }
	       }
	       if(extiende!=null){
	              Clase claseExtiende=Modulos.AnalizadorSintactico.ts.getClase(extiende);
	              claseExtiende.chequearRedifinicionMetodo(aChequear);
	       }
	   }	
}
