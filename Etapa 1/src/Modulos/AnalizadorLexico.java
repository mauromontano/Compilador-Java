package Modulos;
import java.io.BufferedReader;
import java.io.IOException;

import Estructuras.Helper;
import Estructuras.TablaTokens;
import Estructuras.Token;
import Excepciones.ExcepcionCharInvalido;
import Excepciones.ExcepcionCharMalFormado;
import Excepciones.ExcepcionComentarioMalFormado;
import Excepciones.ExcepcionIdentificadorMalFormado;
import Excepciones.ExcepcionLexica;
import Excepciones.ExcepcionOperadorInvalido;
import Excepciones.ExcepcionStringMalFormado;

public class AnalizadorLexico {
	
	private final char EOF = (char)-1;
	private char actual;
	private int nroLinea;
	private BufferedReader buffer;
	private TablaTokens tabla;
	
	private String linea="";
	private int flag= 0;
	private int error= 0;
	private char auxError;
	private char auxiliar;
	private int nroCol;
	private int nroColAux;
	
	public AnalizadorLexico(BufferedReader buffer) throws IOException{
		tabla= new TablaTokens();
	    this.buffer= buffer;
	    nroLinea= 1;
	    actual= consumirChar();
	    nroCol= 0;
	    nroColAux= 1;
	}
	
	public Token getToken() throws ExcepcionLexica {
		String lexema= new String();
		Token token= null;
		
		// Consumo separadores
		while (Helper.esSeparador(actual)) {
			revisarEnter();
			actual = consumirChar();	
			nroColAux= nroCol;
		}		
		
		// Comienzo a identificar el token
				//Comienzo preguntando si es una letra
				if (Helper.esLetra(actual)) { 
					while (Helper.esIdentificador(actual)) {
						lexema += actual;
						actual = consumirChar();
					}
					if (tabla.esPalabraReservada(lexema)) {
						token = new Token(tabla.obtenerTipo(lexema),lexema,nroLinea,nroColAux);
					} else if (Helper.esMayuscula(lexema.charAt(0))) { // diferencio si es identificador de Clase o de variable
						token = new Token("PR_idClase",lexema,nroLinea,nroColAux);
					} else {
						token = new Token("PR_idMetVar",lexema,nroLinea,nroColAux);
					}
					nroColAux= nroCol;	
					
				} else if (Helper.esDigito(actual)) { // Me fijo si comienza con un digito
					while(Helper.esDigito(actual)) {
						lexema += actual;
						actual = consumirChar();
					}
					if(Helper.esLetra(actual)) {   // Si luego del digito vienen letras es error
						if(flag == 0) {
							flag= 1;
							error= 1;
							consumirLinea();
						}
					}	
					token = new Token("Entero_Literal",lexema,nroLinea,nroColAux);
					nroColAux= nroCol;		
				} else switch (actual)  { // Uso un switch para ver que comienza con un simbolo
					case EOF:
					case '{' : 
					case '}' :
					case '(' : 
					case ')' : 
					case '.' : 
					case ',' : 
					case ';' :
					case '+' : 
					case '-' : 
					case '*' : 
					case '%' : 
						lexema += actual;
						token = new Token(tabla.obtenerTipo(lexema),lexema,nroLinea,nroColAux);
						actual = consumirChar();
						nroColAux= nroCol;
						break;
					
						// Casos especiales con un '=' a continuacion (>=, <=, ==, !=)
					case '>' :  
					case '<' : 
					case '!' : 
					case '=' : 
						lexema += actual;
						actual = consumirChar();
						if (actual=='=') { 
							lexema += actual;
							token = new Token(tabla.obtenerTipo(lexema),lexema,nroLinea,nroColAux);
							actual = consumirChar();
						} else {
							token = new Token(tabla.obtenerTipo(lexema),lexema,nroLinea,nroColAux);
						}
						nroColAux= nroCol;
						break;
					
					// Cortocircuitos
					case '|' : // si no hay otro | o & es error
					case '&' :
						lexema += actual;
						char aux = actual; // guardo el | o &
						auxiliar= aux;
						actual= consumirCharEspecial();
						//actual = consumirChar();
						if (actual != aux) {
							nroCol--;
							if(flag != 1) {
								flag= 1;
								error= 2;
								agregarBlanco();
								consumirLinea();			
							}
							
						} else {
							agregarChar();
							lexema += actual;
							token = new Token(tabla.obtenerTipo(lexema),lexema,nroLinea,nroColAux);
							actual = consumirChar();
						}
						nroColAux= nroCol;
						break;
					
						// Literales caracteres y Strings
					case '"' : // necesita cerrar comillas
						actual = '-'; // me salteo las comillas actuales.
						while (!Helper.esComillas(actual) && !Helper.esEOF(actual) && !Helper.esEnter(actual)) {
							actual = consumirCharEspecial();		
							if(Helper.esEOF(actual) || Helper.esEnter(actual)) {
									auxiliar=' ';
									nroCol++;
								}
							else {
									auxiliar= actual;
									agregarChar();
									lexema += actual;
							}
							 		
						}
						if (Helper.esEOF(actual) || Helper.esEnter(actual)) {
							if(flag != 1) {
								flag= 1;
								error= 6;
								manejadorErrores();
							}		
						} else {
							lexema = lexema.substring(0, lexema.length()-1); // saco las comillas del final
							token = new Token("String_Literal",lexema,nroLinea,nroColAux);
							actual = consumirChar();
						}
						nroColAux= nroCol;
						break;
					
					case '\'' : //es apróstrofo
						actual = consumirChar();
						if (Helper.esApostrofo(actual) || Helper.esEOF(actual)) {   
							if(flag != 1) {
								flag= 1;
								error= 4;
								auxiliar= actual;
								consumirLinea();
							}
						} else if (actual=='\\') { // es barra invertida
							actual = consumirChar();
							if (Helper.esEOF(actual) || Helper.esEnter(actual)) {
								if(flag != 1) {
									flag= 1;
									error= 4;
									auxiliar= actual;
									consumirLinea();
								}		
							}
						} 
						if (actual=='n' || actual=='t') {
							lexema = "\\" + actual;
						} else {
							lexema += "" + actual;
						}
						nroColAux= nroCol;
						actual= consumirCharEspecial(); // casos 'x', '\x' , '\''
						if (Helper.esApostrofo(actual)) {
							agregarChar();
							token = new Token("Caracter_Literal",lexema,nroLinea,nroColAux);
						} else {
							if(flag != 1) {
								flag= 1;
								error= 4;
								//auxError= actual;
								if(Helper.esSeparador(actual) || Helper.esEOF(actual)) {
									auxiliar=' ';
									agregarBlanco();
								}
								else {
									auxiliar= actual;
									agregarChar();
								}
								consumirLinea();
							}
						}
						actual = consumirChar();
						nroColAux= nroCol;
						break;		
					
					// Caso '/': Puede ser operador, comentario simple o multilinea
					case '/' : // necesito ver si es comentario
						lexema += actual;
						actual = consumirChar();
						if (actual=='/') { // Es comentario simple
							do {
								actual = consumirChar();
								revisarEnter();
							} while (!Helper.esEnter(actual) && !Helper.esEOF(actual));
							if (Helper.esEOF(actual)) {
								lexema = "" + actual;
								token = new Token(tabla.obtenerTipo(lexema),lexema,nroLinea,nroColAux);
							} else { // Es un enter, ya consumi el comentario.
								actual = consumirChar();
								token = this.getToken(); 
							}
						} else if (actual=='*') { // Es comentario multilinea
							char anterior;
							actual = consumirCharEspecial();
							if (Helper.esEOF(actual)) {
								if(flag != 1) {
									flag= 1;
									error= 5;
									auxiliar=' ';
									agregarBlanco();
									consumirLinea();
								}
							} else { 
								revisarEnter(); 
							}
							do {
								if(!Helper.esEnter(actual))
									agregarChar();
								anterior = actual;
								actual = consumirCharEspecial();
								revisarEnter();
							} while (!((anterior=='*')&&(actual=='/')) && !Helper.esEOF(actual));
							if (Helper.esEOF(actual)) {
								if(flag != 1) {
									flag= 1;
									error= 5;
									auxiliar=' ';
									agregarBlanco();
									consumirLinea();
								}
							} else { // Termine de consumir el comentario
								actual = consumirChar();
								token = this.getToken(); 
							}
						} else { // Es barra simple
							token = new Token(tabla.obtenerTipo(lexema),lexema,nroLinea,nroColAux);
						}
						nroColAux= nroCol;
						break;
					default: 
						if(flag == 0) {
							flag= 1;
							error= 3;
							consumirLinea();
						}
					} 	
				return token;
		
		
	}
	
	// Dependiendo el error que haya arrojo la excepcion determinada
	private void manejadorErrores() throws ExcepcionLexica{
		switch (error) {
		case 1:
			throw new ExcepcionIdentificadorMalFormado(nroLinea,linea,actual,nroCol);
		case 2:
			throw new ExcepcionOperadorInvalido(nroLinea,linea,auxiliar,nroCol);
		case 3:
			throw new ExcepcionCharInvalido(nroLinea,linea,actual,nroCol);
		case 4:
			throw new ExcepcionCharMalFormado(nroLinea,linea,auxiliar,nroCol);	
		case 5:	
			throw new ExcepcionComentarioMalFormado(nroLinea,linea,auxiliar,nroCol);
		case 6:		
			throw new ExcepcionStringMalFormado(nroLinea,linea,auxiliar,nroCol);	
		}
		
	}
	
	
	// Busca un caracter en el buffer de lectura y lo devuelvo
	// Devulve el entero -1 si se llego a EOF
	private char consumirChar() {
		try {
			char A=(char) buffer.read();	
			linea+=""+A;                   //agrego cada char a la linea por si esta el error
			mirarColumnas(A);
			return A;
		} catch (IOException e) {
			System.out.println("Error");
		}
		return EOF;
	}
	
	// Revisa si el caracter actual es un Enter para incrementar el numero de linea
	private void revisarEnter() throws ExcepcionLexica {
		if (Helper.esEnter(actual)) { 
			if(flag==1) {
				manejadorErrores();
			}	
			nroLinea++;
			linea="";  //debo vaciar la linea ya que encontre un enter 
			nroCol=0;
			nroColAux=1;
		}
	}
	
	// Si encontre un error, guardo toda la linea donde se encuentra este
	private void consumirLinea() throws ExcepcionLexica {
		mirarColumnas(actual);
		try {
			auxError=(char) buffer.read();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		while(!Helper.esEnter(auxError) && (auxError!=EOF)) {
			try {
				linea+=""+auxError; 
				auxError=(char) buffer.read();	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		manejadorErrores();
	}
	
	// Agrego el caracter actual a la linea
	private void agregarChar() {
		linea+=""+actual;
	}
	
	// Agrego un espacio en blanco a la linea
	private void agregarBlanco() {
		linea+=" ";
	}
	
	// Es igual a consumirChar pero no agrego el char a la linea
	private char consumirCharEspecial() {
			try {
				char A=(char) buffer.read();	
				mirarColumnas(A);                 
				return A;
			} catch (IOException e) {
				System.out.println("Error");
			}
			return EOF;
	}
	
	//Si A es distinto de un enter incremento las columnas
	private void mirarColumnas(char A) {
		if (A!='\n') { 
			nroCol++;
		}
	}

}
