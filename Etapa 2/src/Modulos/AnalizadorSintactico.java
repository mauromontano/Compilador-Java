package Modulos;

import Estructuras.Token;
import Excepciones.*;

public class AnalizadorSintactico {

	private static Token tokenActual;
    private static AnalizadorLexico aLexico;
    
    public AnalizadorSintactico(AnalizadorLexico al) {
    	aLexico = al;
			try {
				tokenActual = aLexico.getToken();
			} catch (ExcepcionLexica e1) {
				e1.getMessage();
			}
		
    	try {
			Inicial();
			System.out.println("Compilacion Exitosa ");  
			System.out.println("[SinErrores]");
		} catch (Excepcion ex) {
            System.out.println(ex.getError());
		}
    }
    
    // Metodos del Analizador Sintactico
    // Todos los metodos que siguen corresponden a las reglas de Sintaxis de MiniJava
    
    private static void match(String aComparar, String error) throws Excepcion{

    	if(aComparar.equals(tokenActual.getNombre())){
            try {
				tokenActual = aLexico.getToken();
			} catch (ExcepcionLexica e) {
					e.getMessage();
			}
        }
        else
            throw new ExcepcionSintactica(tokenActual,error);
    }
    
    private static boolean contenido(String[] aComparar){
        boolean toRet=false;
        for (int i=0; i<aComparar.length && !toRet; i++){
            if(aComparar[i].equals(tokenActual.getNombre()))
                toRet=true;
        }
        return toRet;
    }
    
    public static void Inicial() throws Excepcion{
	     ListaClases();
   }
    
    private static void ListaClases() throws Excepcion{
    	
    	Clase();
    	ListaClasesF();
    }
	
    private static void ListaClasesF() throws Excepcion{
    	if(tokenActual.getNombre().equals("PR_Class"))
            ListaClases();
        else if(tokenActual.getNombre().equals("EOF")){
        	
        }else 
            throw new ExcepcionSintactica(tokenActual, "class");
    }
    
    private static void Clase() throws Excepcion{
        
        match("PR_Class","class");
        match("PR_idClase","idClase");
        Herencia();
        if(tokenActual.getNombre().equals("P_Llave_Abre")){
            match("P_Llave_Abre","{");
            ListaMiembros();
            match("P_Llave_Cierra","}");
        }else 
            throw new ExcepcionSintactica(tokenActual, "{ o extends");  
    }
    
    private static void Herencia() throws Excepcion {
    	if(tokenActual.getNombre().equals("PR_Extends")){
    		match("PR_Extends","extends");
    		match("PR_idClase","idClase");
    	}
    	else {
    		//epsilon no hago nada
    	}
        
    }
    
    private static void ListaMiembros() throws Excepcion{
        String[] primerosMiembro = {"PR_Public", "PR_Private", "PR_Protected","PR_Static","PR_Dynamic","PR_Class"};
        if(tokenActual.getNombre().equals("P_Llave_Cierra")){
        	// epsilon no hago nada
        }else if(contenido(primerosMiembro)){
            Miembro();			
            ListaMiembros();
        }
        else if(tokenActual.getNombre().equals("PR_idClase")){
        	Constructor();
        	ListaMiembros();                
        }else
          throw new ExcepcionSintactica(tokenActual, "un modificador de acceso (para un atributo), una clase (para un constructor), un tipo estatico o dinamico (para un metodo) o una llave que cierra");
    }
    
    private static void Constructor() throws Excepcion {
        match("PR_idClase","una clase");
        ArgsFormales();
        Bloque();
    }
    
    private static void Miembro() throws Excepcion{
        String[] primerosAtributo ={"PR_Public","PR_Protected","PR_Private"};
        String[] primerosMetodo={"PR_Static","PR_Dynamic"};
        if(contenido(primerosAtributo)){
            Atributo();
        }else if(contenido(primerosMetodo)){
            Metodo();
        }else{
          throw new ExcepcionSintactica(tokenActual, "un modificador de acceso (para un atributo), una clase (para un constructor), un tipo estatico o dinamico (para un metodo) o una llave que cierra");
        }
    }
    
    private static void Atributo() throws Excepcion{
        Visibilidad();
        Tipo();
        ListaDecAtrs();
        match("P_Puntoycoma","un punto y coma");
        
    }	
    
    private static void Visibilidad() throws Excepcion{     
        switch (tokenActual.getNombre()) {
            case "PR_Public":
                match("PR_Public","public");
                break;
            case "PR_Private":
                match("PR_Private","private");
                break;
            default:
                throw new ExcepcionSintactica(tokenActual, "un modificador de acceso valido");
        }
    }
    
    private static void Tipo() throws Excepcion {
        String[] primerosTipoPrimitivo={"PR_Boolean","PR_Char","PR_Int","PR_String"};
        if(contenido(primerosTipoPrimitivo))
            TipoPrimitivo();
        else if(tokenActual.getNombre().equals("PR_idClase"))
            match("PR_idClase","una clase");
        else
            throw new ExcepcionSintactica(tokenActual, "un tipo primitivo o una clase");
    }
    
    private static void TipoPrimitivo() throws Excepcion{
       switch(tokenActual.getNombre()){
           case "PR_Boolean": match("PR_Boolean","boolean");
                            break;
           case "PR_Char": match("PR_Char","char");break;
           case "PR_Int": match("PR_Int","int");break;
           case "PR_String": match("PR_String","String");break;
           default: throw new ExcepcionSintactica(tokenActual, "un tipo primitivo");
       } 
    }
    
    private static void ListaDecAtrs() throws Excepcion{
    	match("PR_idMetVar","Identificador");
        ListaDecAtrsF();
        
    }
    
    private static void ListaDecAtrsF() throws Excepcion {
        if(tokenActual.getNombre().equals("P_Coma"))
        {
            match("P_Coma","una coma");
            ListaDecAtrs();
        }else if(tokenActual.getNombre().equals("P_Puntoycoma")){
        
        }else if(tokenActual.getNombre().equals("O_Asignacion")){
        	match("O_Asignacion","una asignacion");
            Expresion();
        } 
        else
            throw new ExcepcionSintactica(tokenActual, "una coma o un punto y coma");
    }
    
    private static void ArgsFormales() throws Excepcion {
    	
        match("P_Parentesis_Abre","(");
        ListaArgsFormalesOVacio();
        match("P_Parentesis_Cierra",")");
    }
    
    private static void ListaArgsFormalesOVacio() throws Excepcion {
    	
    	String[] primerosListaArgsFormales = {"PR_Boolean","PR_Char","PR_Int","PR_String","PR_idClase"};
    	if(contenido(primerosListaArgsFormales)) {
            ListaArgsFormales();
    	}
        else {
            //epsilon no hago nada	
        }
    }
    
    private static void ListaArgsFormales() throws Excepcion {
        ArgFormal();
        ListaArgsFormalesAux();
    }
    
    private static void ArgFormal() throws Excepcion {
    	
        Tipo();
        match("PR_idMetVar","Identificador");
    }
    
    private static void ListaArgsFormalesAux() throws Excepcion {
    	
        if(tokenActual.getNombre().equals("P_Coma")){
            match("P_Coma","Coma");
            ListaArgsFormales();
        }else if( tokenActual.getNombre().equals("P_Parentesis_Cierra")){
            //epsilon
        }else
            throw new ExcepcionSintactica(tokenActual, "una coma o un parentesis que cierra");
    }
    
    private static void Bloque() throws Excepcion {
    	
        match("P_Llave_Abre","una llave que abre");
        ListaSentencias();
        match("P_Llave_Cierra","una llave que cierra o una sentencia bien formada");
    }
    
    private static void ListaSentencias() throws Excepcion {
    	
        String[] primerosSentencia={"P_Puntoycoma","PR_idMetVar","P_Parentesis_Abre","PR_Boolean","PR_Char","PR_Int","PR_String","PR_If","PR_While","P_Llave_Abre","PR_Return","PR_idClase"};
        if(contenido(primerosSentencia)){
            Sentencia();
            ListaSentencias();
        }
        else {
        	//epsilon no hago nada
        }
    }
    
    private static void Sentencia() throws Excepcion {
    	
        String[] primerosTipo={"PR_Boolean","PR_Char","PR_Int","PR_String","PR_idClase"};
        if(tokenActual.getNombre().equals("P_Puntoycoma")){
            match("P_Puntoycoma","punto y coma");
        }else if(tokenActual.getNombre().equals("PR_idMetVar")){
            Asignacion();
            match("P_Puntoycoma","punto y coma");
        }else if(tokenActual.getNombre().equals("P_Parentesis_Abre")){
            Llamada();
            match("P_Puntoycoma","punto y coma");
        }else if( contenido(primerosTipo)){
            Tipo();
            ListaDecAtrs();
            match("P_Puntoycoma","punto y coma");
        }else if (tokenActual.getNombre().equals("PR_If")){
            match("PR_If","if");
            match("P_Parentesis_Abre","(");
            Expresion();
            match("P_Parentesis_Cierra",")");
            Sentencia();
            SentenciaFIf();
        }else if(tokenActual.getNombre().equals("PR_While")){
            match("PR_While","while");
            match("P_Parentesis_Abre","(");
            Expresion();
            match("P_Parentesis_Cierra",")");
            Sentencia();
        }else if(tokenActual.getNombre().equals("P_Llave_Abre")){
            Bloque();
        }else if(tokenActual.getNombre().equals("PR_Return")){
            match("PR_Return","return");
            SentenciaFRet();
        }else
            throw new ExcepcionSintactica(tokenActual,"una sentencia bien formada");
    }
    
    private static void SentenciaFRet() throws Excepcion {
    	
        String[] primerosExpresion= {"O_Suma","O_Resta","O_Not","PR_This","PR_Null","PR_True","PR_False","Entero_Literal","Caracter_Literal","String_Literal","P_Parentesis_Abre","PR_idMetVar","PR_Class","PR_New"};
        if(contenido(primerosExpresion)){
            ExpresionOVacio();
            match("P_Puntoycoma","un punto y coma (;)");
        }else if(tokenActual.getNombre().equals("P_Puntoycoma")){
            match("P_Puntoycoma","un punto y coma (;)");
            
        }else
            throw new ExcepcionSintactica(tokenActual, "un punto y coma o una expresion v�lida");
    }
    
    private static void SentenciaFIf() throws Excepcion {
    	
        if(tokenActual.getNombre().equals("P_Llave_Cierra")){
            //epsilon
        }else if(tokenActual.getNombre().equals("PR_Else")){
            match("PR_Else","else");
            Sentencia();
        }
    }
    
    private static void Asignacion() throws Excepcion {
    	
        Acceso();
        TipoDeAsignacion();
        Expresion();             
    }
    
    private static void TipoDeAsignacion() throws Excepcion {
    	
        if(tokenActual.getNombre().equals("O_Asignacion")){
            match("O_Asignacion","un operador asignacion");
        }else if(tokenActual.getNombre().equals("O_SumaAsignacion")){
            match("O_SumaAsignacion","un operador suma asignacion");
        }else if(tokenActual.getNombre().equals("O_RestaAsignacion")){
            match("O_RestaAsignacion","un operador resta asignacion");
        }else
            throw new ExcepcionSintactica(tokenActual, "un operador asignacion, suma asignacion o una resta asignacion");
    }
    
    
    private static void Llamada() throws Excepcion {

    	Acceso();
    }
       
    private static void ExpresionOVacio() throws Excepcion {

    	String[] primerosExpresion={"O_Suma","O_Resta","O_Not","PR_Null","PR_True","PR_False","Entero_Literal","Caracter_Literal","String_Literal","P_Parentesis_Abre","PR_idMetVar","PR_idClase","PR_New","PR_This"};    	
    	
    	if(contenido(primerosExpresion)){
            Expresion();
        }else{
        	//epsilon no hago nada
        }
    }
    
    private static void Expresion() throws Excepcion {
    	
    	ExpresionUnaria();	
    	ExpresionAux();								
    }
    
    private static void ExpresionAux() throws Excepcion {
    	
    	String[] primerosOperadoresBinarios={"O_Or","O_And","O_Comparacion","O_Distinto","O_Mayor","O_Menor","O_Mayorigual","O_Menorigual","O_Suma","O_Resta","O_Mult","O_Div","O_Mod"};
        if(contenido(primerosOperadoresBinarios)){
            OperadorBinario();
            ExpresionUnaria();
            ExpresionAux();
        }else {
            
        }
    }
    
    private static void OperadorBinario() throws Excepcion {

        switch(tokenActual.getNombre()){
            case "O_Or": match("O_Or","un operador or");break;
            case "O_And": match("O_And","un operador and");break;
            case "O_Comparacion": match("O_Comparacion","un operador comparacion");break;
            case "O_Distinto":match("O_Distinto","un operador distinto");break;
            case "O_Mayor":match("O_Mayor","un operador mayor");break;
            case "O_Menor": match("O_Menor","un operador menor");break;
            case "O_Mayorigual":match("O_Mayorigual","un operador >=");break;
            case "O_Menorigual": match("O_Menorigual","un operador <=");break;
            case "O_Suma": match("O_Suma","un operador mas");break;
            case "O_Resta": match("O_Resta","un operador menos");break;
            case "O_Mult": match("O_Mult","operador por");break;
            case "O_Div": match("O_Div","operador dividido");break;
            case "O_Mod": match("O_Mod","operador modulo");break;
            default: throw new ExcepcionSintactica(tokenActual, "un operador binario");
        }
        
        
    } 
     
     private static void ExpresionUnaria() throws Excepcion {
    	
    	ExpresionUnariaAux();
    	Operando();
    }
     
     private static void ExpresionUnariaAux() throws Excepcion {	
    	 
    	 String[] primerosOperadorUnario={"O_Suma","O_Resta","O_Not"};
    	 if(contenido(primerosOperadorUnario)){
    		  OperadorUnario();
    	 }
    	 
    }
    
    private static void OperadorUnario() throws Excepcion {
    	
    	switch(tokenActual.getNombre()){
        case "O_Suma": match("O_Suma","un operador mas");break;
        case "O_Resta": match("O_Resta","un operador menos");break;
        case "O_Not": match("O_Not","un operador negacion");break;
        default: throw new ExcepcionSintactica(tokenActual, "un operador mas , menos o negacion");
    	}
    }
    
    private static void Operando() throws Excepcion {
        
    	String[] primerosLiteral={"PR_Null","PR_True","PR_False","Entero_Literal","Caracter_Literal","String_Literal"};
        String[] primerosAcceso={"P_Parentesis_Abre","PR_idMetVar","PR_idClase","PR_New","PR_This"};
        
        if(contenido(primerosLiteral)){
            Literal();
        }else if(contenido(primerosAcceso)){
           Acceso();
        }else {
                throw new ExcepcionSintactica(tokenActual,"un operando");       
        }
    }
     
     private static void Acceso() throws Excepcion {      
           Primario();
           Encadenado();
    }
         
    private static void Primario() throws Excepcion {
        switch(tokenActual.getNombre()){
        	case "PR_This":								 	 // caso This
        		match("PR_This","un this");
        		break;	
            case "PR_idMetVar":								 // caso AccesoVar y AccesoMetodo 
                match("PR_idMetVar","un identificador");
                AccesoVarOMet(); 
                break;
            case "PR_Static":								 // caso AccesoEstatico
            	match("PR_Static","un static");
                match("PR_Class","una clase");
                match("P_Punto","un punto");
                ArgsActuales();
                break;
            case "PR_New":									// caso AccesoConstructor 
                match("PR_New","new");
                match("PR_idClase","una clase");
                ArgsActuales();
                break;
            case "P_Parentesis_Abre":						// caso (Expresion)
                match("P_Parentesis_Abre","(");
                Expresion();
                match("P_Parentesis_Cierra",")");
                //VarOMetodoEncadenadoAux();            
        }
    }
    
    private static void AccesoVarOMet() throws Excepcion {
    	
    	if (tokenActual.getNombre().equals("P_Parentesis_Abre"))
    		ArgsActuales();    		
    }
    
    private static void ArgsActuales() throws Excepcion {
    	
    	if(tokenActual.getNombre().equals("P_Parentesis_Abre")){
    		match("P_Parentesis_Abre","(");
    		ListaExpsOVacio();
    		match("P_Parentesis_Cierra",")");
    	}
    	else
            throw new ExcepcionSintactica(tokenActual, "argumentos bien formados");
        
    }
    
    private static void ListaExpsOVacio() throws Excepcion {
    	
       	String[] primerosExpresion={"O_Suma","O_Resta","O_Not","PR_Null","PR_True","PR_False","Entero_Literal","Caracter_Literal","String_Literal","P_Parentesis_Abre","PR_idMetVar","PR_idClase","PR_New","PR_This"};
    	    
    	if(contenido(primerosExpresion)){
        	ListaExps();
        }else{
        	//epsilon no hago nada
        }   	
    }
    
    private static void ListaExps() throws Excepcion {
    	
        Expresion();
        ListaExpsF();
    }  
    
    private static void ListaExpsF() throws Excepcion {
    	
        if(tokenActual.getNombre().equals("P_Coma")){
            match("P_Coma","una coma");
            ListaExps();																	
        }else
        	if(tokenActual.getNombre().equals("P_Parentesis_Cierra")) {
        		
        	}
        	else  	{	
        		throw new ExcepcionSintactica(tokenActual, "una coma o un parentesis que cierra");
        	}
    }
    
    private static void Encadenado() throws Excepcion {
    	
    	if(tokenActual.getNombre().equals("P_Punto")){
        	VarOMetodoEncadenado();
        }
        else {
        	// epsilon no hago nada
        }
    }
    
    private static void VarOMetodoEncadenado() throws Excepcion {
    	
        match("P_Punto","un punto");
        match("PR_idMetVar","un identificador");
        VarOMetodoEncadenadoF();
        Encadenado();
    }
    private static void VarOMetodoEncadenadoF() throws Excepcion {
    	
        if(tokenActual.getNombre().equals("P_Parentesis_Abre"))
            ArgsActuales();
    }
    
    private static void Literal() throws Excepcion {
    	
        switch(tokenActual.getNombre()){
            case "PR_Null": match("PR_Null","null");break;
            case "PR_True": match("PR_True","true");break;
            case "PR_False":match("PR_False","false");break;
            case "Entero_Literal":match("Entero_Literal","un entero");;break;
            case "Caracter_Literal":match("Caracter_Literal","un caracter");break;
            case "String_Literal":match("String_Literal","un String literal");break;
            default: throw new ExcepcionSintactica(tokenActual,"un literal");
        }
    }
    
    private static void Metodo() throws Excepcion {

        FormaMetodo();
        MetodoF();
    }
    
    
    private static void MetodoF() throws Excepcion {

        String[] primerosTipoMetodo = {"PR_Boolean","PR_Char","PR_Int","PR_String","PR_Void","PR_idClase"};
        if(contenido(primerosTipoMetodo)){
            TipoMetodo();
            match("PR_idMetVar","un identificador");
            ArgsFormales();
            Bloque();
        }else
            throw new ExcepcionSintactica(tokenActual, "un tipo primitivo");
    }
    
    private static void TipoMetodo() throws Excepcion {

        String[] primerosTipo={"PR_Boolean","PR_Char","PR_Int","PR_String","PR_idClase"};
        if(contenido(primerosTipo)){
            Tipo();
        }else if(tokenActual.getNombre().equals("PR_Void"))
            match("PR_Void","void");
        else
            throw new ExcepcionSintactica(tokenActual, "un tipo primitivo, una clase o void");
    }
    private static void FormaMetodo() throws Excepcion {

        switch(tokenActual.getNombre()){
            case "PR_Static": match("PR_Static","static");break;
            case "PR_Dynamic": match("PR_Dynamic","dynamic");break;
            default: throw new ExcepcionSintactica(tokenActual,"static o dynamic");
        }
        
    }
    
    
}
