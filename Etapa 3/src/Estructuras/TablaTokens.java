package Estructuras;
import java.util.HashMap;

public class TablaTokens {

	private HashMap<String,String> tabla;
	private static char EOF = (char)-1;
	
	public TablaTokens() {
		
		// Inicializo la tabla hash map
		tabla = new HashMap<String,String>();
		
		// Palabras reservadas
		tabla.put("class", "PR_Class");
		tabla.put("extends", "PR_Extends");
		tabla.put("static", "PR_Static");
		tabla.put("dynamic", "PR_Dynamic");
		tabla.put("void", "PR_Void");
		tabla.put("boolean", "PR_Boolean");
		tabla.put("char", "PR_Char");
		tabla.put("int", "PR_Int");
		tabla.put("String", "PR_String");
		tabla.put("public", "PR_Public");
		tabla.put("protected", "PR_Protected");
		tabla.put("private", "PR_Private");
		tabla.put("final", "PR_Final");
		tabla.put("if", "PR_If");
		tabla.put("else", "PR_Else");
		tabla.put("while", "PR_While");
		tabla.put("return", "PR_Return");
		tabla.put("this", "PR_This");
		tabla.put("new", "PR_New");
		tabla.put("null", "PR_Null");
		tabla.put("true", "PR_True");
		tabla.put("false", "PR_False");
		tabla.put("idMetVar", "PR_idMetVar");
		tabla.put("idClase", "PR_idClase");
		
		// Operadores y puntuacion
		tabla.put("(", "P_Parentesis_Abre");
		tabla.put(")", "P_Parentesis_Cierra");
		tabla.put("{", "P_Llave_Abre");
		tabla.put("}", "P_Llave_Cierra");
		tabla.put(".", "P_Punto");
		tabla.put(";", "P_Puntoycoma");
		tabla.put(",", "P_Coma");
		tabla.put(">", "O_Mayor");
		tabla.put("<", "O_Menor");
		tabla.put("!", "O_Not");
		tabla.put("==", "O_Comparacion");
		tabla.put(">=", "O_Mayorigual");
		tabla.put("<=", "O_Menorigual");
		tabla.put("!=", "O_Distinto");
		tabla.put("+=", "O_SumaAsignacion");
		tabla.put("-=", "O_RestaAsignacion");
		tabla.put("+", "O_Suma");
		tabla.put("-", "O_Resta");
		tabla.put("*", "O_Mult");
		tabla.put("/", "O_Div");
		tabla.put("||", "O_Or");
		tabla.put("&&", "O_And");
		tabla.put("%", "O_Mod");
		
		//Asignacion
		tabla.put("=", "O_Asignacion");
		
		// EOF
		String aux = "" + EOF;
		tabla.put(aux, "EOF");
	}
	
	public boolean esPalabraReservada(String str){
		return tabla.containsKey(str);
	}
	
	public String obtenerTipo(String str){
		return tabla.get(str);
	}
	
}