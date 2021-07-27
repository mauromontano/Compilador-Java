package TablaDeSimbolos;

import java.util.HashMap;
import java.util.Map;

import Estructuras.Token;
import Excepciones.ExcepcionSemantica;

public class TablaSimbolos {
	    private Clase claseActual;
	    private Unidad unidadActual;
	    private Map<String,Clase> clases;
	    
	    public TablaSimbolos() throws ExcepcionSemantica{
	        claseActual=null;
	        unidadActual=null;
	        clases = new HashMap<String,Clase>();
	        
	        // Crear la clase Object
	        Token tokenObjeto= new Token("Clase", "Object", 0, 0);
	        Clase object = new Clase(tokenObjeto);
	        object.setActualizada(true);
	        insertarClase(object);
	        
	        //Crear la clase System
	        Token tokenSystem= new Token("Clase","System",0, 0);
	        Clase system= new Clase(tokenSystem);
	        system.setHerencia("Object");
	        system.setActualizada(true);
	        insertarClase(system);
	        
	        
	        //Crear los metodos para system
	        Metodo read = new Metodo(new Token("PR_idMetVar", "read",0, 0), "static", new TipoEntero());
	        system.insertarMetodo(read);
	        
	        Metodo printB = new Metodo(new Token("PR_idMetVar", "printB", 0, 0), "static", new Void());
	        system.insertarMetodo(printB);
	        printB.insertarArgumento(new Variable(new Token("PR_idMetVar", "b", 0, 0), new TipoBoolean()));
	        
	        Metodo printC = new Metodo(new Token("PR_idMetVar", "printC", 0, 0), "static", new Void());
	        system.insertarMetodo(printC);
	        printC.insertarArgumento(new Variable(new Token("PR_idMetVar", "c", 0, 0), new TipoChar()));
	        
	        Metodo printI = new Metodo(new Token("PR_idMetVar", "printI", 0, 0), "static", new Void());
	        system.insertarMetodo(printI);
	        printI.insertarArgumento(new Variable(new Token("PR_idMetVar", "i", 0, 0), new TipoEntero()));

	        Metodo printS = new Metodo(new Token("PR_idMetVar", "printS", 0, 0), "static", new Void());
	        system.insertarMetodo(printS);
	        printS.insertarArgumento(new Variable(new Token("PR_idMetVar", "s", 0, 0), new TipoString()));
	        
	        Metodo println = new Metodo(new Token("PR_idMetVar", "println", 0, 0), "static", new Void());
	        system.insertarMetodo(println);
	        
	        Metodo printBln = new Metodo(new Token("PR_idMetVar", "printBln", 0, 0), "static", new Void());
	        system.insertarMetodo(printBln);
	        printBln.insertarArgumento(new Variable(new Token("PR_idMetVar", "b", 0, 0), new TipoBoolean()));
	        
	        Metodo printCln = new Metodo(new Token("PR_idMetVar", "printCln", 0, 0), "static", new Void());
	        system.insertarMetodo(printCln);
	        printCln.insertarArgumento(new Variable(new Token("PR_idMetVar", "c", 0, 0), new TipoChar()));
	        
	        Metodo printIln = new Metodo(new Token("PR_idMetVar", "printIln", 0, 0), "static", new Void());
	        system.insertarMetodo(printIln);
	        printIln.insertarArgumento(new Variable(new Token("PR_idMetVar", "i", 0, 0), new TipoEntero()));
	        
	        Metodo printSln = new Metodo(new Token("PR_idMetVar", "printSln", 0, 0), "static", new Void());
	        system.insertarMetodo(printSln);
	        printSln.insertarArgumento(new Variable(new Token("PR_idMetVar", "s", 0, 0), new TipoString()));
	    }
	    
	    
	    
	    public void consolidar() throws ExcepcionSemantica{
	        
	        for(Clase c: clases.values()){
	            c.consolidar();
	        }
	        chequearMain();
	    }
	    
	    //Comento éste chequearMain y cambie de la clase Clase el metodo hayMain
	    
	    /*public void chequearMain() throws ExcepcionSemantico{
	        boolean hayMain=false;
	        for(Clase c: clases.values()){
	            if(c.hayMain()){
	                hayMain=true;
	            }
	        }
	        if(!hayMain)
	            throw new ExcepcionSemantico("Por lo menos alguna clase debe tener un metodo estatico main sin parametros.");      
	    }*/
	    
	    public void chequearMain() throws ExcepcionSemantica{
	        Metodo main=null;
	        for(Clase c: clases.values()){
	            Metodo mainActual= c.hayMain();
	            if(main!=null && mainActual!=null){
	                throw new ExcepcionSemantica("El metodo main debe ser unico en todo el codigo fuente, el mismo ya existe en la clase "+c.getId().getLexema(),c.getId().getLinea(),c.getId().getLexema());
	            }
	            else if(mainActual!=null)
	                main=mainActual;
	        }
	        if(main==null)
	            throw new ExcepcionSemantica("Debe haber una clase que tenga un método estático main sin parámetros.",1,"main");      
	    }

	    
	    public Clase getClaseActual(){
	        return claseActual;
	    }
	    
	    public Unidad getUnidadActual(){
	        return unidadActual;
	    }
	    
	    public void setClaseActual(Clase c){
	        claseActual=c;
	    }
	    
	    public void setUnidadActual(Unidad c){
	        unidadActual=c;
	    }
	    
	    
	    // Determina si existe la clase pasada por parametroario false
	    
	    public boolean existeClase(String nombre){
	        if(clases.containsKey(nombre))
	            return true;
	        else
	            return false;
	    }
	   
	     // Devuelve la clase que tiene el nombre pasado por parÃ¡metro
	     // nombre es el nombre de la clase
	     //return la clase si es que existe, de lo contrario null
	     
	    public Clase getClase(String nombre){
	        return clases.get(nombre);
	    }
	    public void insertarClase(Clase c) throws ExcepcionSemantica{
	        if((c.getId().getLexema().equals("Object") && clases.containsKey("Object"))|| (c.getId().getLexema().equals("System") && clases.containsKey("System"))){
	            throw new ExcepcionSemantica("No se puede declarar una clase con el nombre "+c.getId().getLexema()+", ya que la misma es predefinida.", c.getId().getLinea(),c.getId().getLexema());
	        }
	        if(clases.containsKey(c.getId().getLexema())){
	           throw new ExcepcionSemantica("Se quiere declarar la clase "+c.getId().getLexema()+" pero la misma ya existe.", c.getId().getLinea(),c.getId().getLexema()); 
	        }
	        clases.put(c.getId().getLexema(), c);
	    }
	

}
