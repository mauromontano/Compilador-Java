package Modulos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	
	/*

	public static void main(String[] args) throws IOException{
		Token token = null;
		BufferedWriter bw = null;
		try {				
			if (args.length == 0 || args.length > 2) {
				System.out.println("Cantidad inv�lida de argumentos");
				System.out.println("Modo de uso: java Etapa1 <Entrada> [Salida]");
			} else {
				BufferedReader br = new BufferedReader(new FileReader(args[0]));
				AnalizadorLexico alex = new AnalizadorLexico(br);
				if (args.length == 1) {
					// Salida por pantalla con formato de 32 caracteres para el TIPO y LEXEMA y 8 caracteres para LINEA
					//System.out.println("("+token.getNombre(),token.getLexema(),token.getLinea()+")");
					//System.out.format("%-32s%-32s%-8s %n", "TIPO", "LEXEMA", "LINEA");
					do {
						token = alex.getToken();
						System.out.format("("+token.getNombre()+","+token.getLexema()+","+token.getLinea()+")\n");
					} while (token.getNombre()!="EOF");
				} else {
					// Salida por archivo
					File file = new File(args[1]);
					if (!file.exists()) {
						 file.createNewFile();
					}
					FileWriter fw = new FileWriter(file);
					bw = new BufferedWriter(fw);
					//Salida por archivo con formato de 32 caracteres para el TIPO y LEXEMA y 8 caracteres para LINEA 
					//bw.write(String.format("%-32s%-32s%-8s %n", "TIPO", "LEXEMA", "LINEA"));
					do {
						token = alex.getToken();
						//bw.write(String.format("%-32s%-32s%-8d %n", token.getNombre(),token.getLexema(),token.getLinea()));
						System.out.format("("+token.getNombre()+","+token.getLexema()+","+token.getLinea()+")\n");
					} while (token.getNombre()!="EOF");
					bw.close();
				}
				System.out.println("An�lisis l�xico oper� de forma exitosa");
			}
		} catch (IOException e1) {
			System.out.println("Error de archivos. Revisar que los datos de entrada/salida esten correctos."); 
		} catch (ExcepcionLexica e2) {
			System.out.println("No se pudo completar el an�lisis l�xico");
		} catch (Exception e3) {
			System.out.println("Se produjo un error");
		} finally {
			if (bw!=null) 
				bw.close();
		}
	}
	*/
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException{
		
		try {				
			if (args.length == 0 || args.length > 2) {
				System.out.println("Cantidad invalida de argumentos");
				System.out.println("Modo de uso: java -jar AnalizadorSintactico.jar <Entrada>");
			} else {
				BufferedReader br = new BufferedReader(new FileReader(args[0]));
				AnalizadorLexico alex = new AnalizadorLexico(br);
				AnalizadorSintactico asin = new AnalizadorSintactico(alex);
				
			}
		
		} catch (IOException e1) {
			System.out.println("Error de archivos. Revisar que los datos de entrada sean correctos."); 
		} catch (Exception e3) {
			System.out.println("Se produjo un error");
		} 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

