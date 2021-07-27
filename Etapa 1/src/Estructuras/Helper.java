package Estructuras;

public class Helper {
	
	public static boolean esDigito(char chr) {
		return (chr>='0' && chr<='9');
	}

	public static boolean esLetra(char chr) {
		return ((chr>='a' && chr<='z') || (chr>='A' && chr<='Z'));
	}
	
	public static boolean esMayuscula(char chr) {
		return (chr>='A' && chr<='Z');
	}
	
	public static boolean esGuionBajo(char chr) {
		return (chr=='_');
	}
	
	public static boolean esSeparador(char chr) {
		return (chr==' ') || (chr=='\n') || (chr=='\t')||(chr=='\r');
	}
	
	public static boolean esEnter(char chr) {
		return (chr=='\n');
	
	}
	
	public static boolean esIdentificador(char chr) {
		return esLetra(chr) || esGuionBajo(chr) || esDigito(chr);
	}
	
	public static boolean esComillas(char chr) {
		return (chr=='"');
	}
	
	public static boolean esApostrofo(char chr) {
		return (chr=='\'');
	} 
	
	public static boolean esEOF(char chr) {
		return (chr==(char)-1);
	}
	
}
