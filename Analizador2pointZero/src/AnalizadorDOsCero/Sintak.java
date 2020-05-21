package AnalizadorDOsCero;

import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

public class Sintak {
	
	ArrayList<String> lex = new ArrayList<String>();          	// Lo que arroja el analisis lexico
	ArrayList<String> elexe = new ArrayList<String>();			// Fila de entrantes
	ArrayList<String> terminales = new ArrayList<String>();		// Columna de terminales
	Stack<String> pila = new Stack<String>();
	String MensajeDeError = "";
	String MensajeDePila = "";
	String [][] tabla1;
	int cerra = 0 ;
	int linea = 0 ;
	boolean errP = false;
	boolean vuelta = false;
	
	//Este metodo llena la fila y columna en los arrays creados para ahorrarnos bucles de b�squeda
	public void llenarFyC() {
		for (int i = 0; i < tabla1.length; i++) {
			terminales.add(tabla1[i][0]);
		}
		for (int i = 0; i < tabla1[0].length; i++) {
			elexe.add(tabla1[0][i]);
		}
	}
	
	//Este es el constructor que recibe todo el pedo y inicia lo esensial
	public Sintak() {
		vuelta = false;
		tablas t = new tablas();
		tabla1 = t.tabla1;
		llenarFyC();
		pila.push("finale");
		pila.push("E");
	}
	
	//Revisa si es aceptado
	public boolean aceptado() {
		if (pila.isEmpty() && MensajeDeError.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	//Este es el �nico metodo que se llama
	public boolean AS(String lexema, int line) {
		linea = line;
		if (vuelta) {
			MensajeDePila = "";
		}else {
			MensajeDePila += pila+"\n";
			vuelta = true;
		}
		lex.add(lexema);
		if(lexema.equals("abP")) {
			cerra ++;
		}
		procesoApilAndDesapil(lex.size()-1);
		if(lexema.equals("ciP")) {
			if (cerra > 0) {
				cerra --;
			}
		}
		return errP;
	}
	
	//Este nos va a servir para llamarlo y mediante recursivida poder llenar hasta que se desapile y concuerde y retorne a AS
	public void procesoApilAndDesapil (int pivote) {
		if(pila.isEmpty()) {
			MensajeDeError += "Error de Sintaxis: "+lex.get(pivote)+" despu�s de "+ lex.get(pivote-1)+" en la l�nea "+ linea+"\n";errP = false;
			pila.push(" ");
		}
		if (terminales.contains(pila.peek()) && elexe.contains(lex.get(pivote))) {
			apila(terminales.indexOf(pila.peek()), elexe.indexOf(lex.get(pivote)),pivote);
		}
	}
	
	//Aqui apila hasta lo indicado en procesoApilAndDesapil()
	public void apila(int i, int j, int pivote) {
		String interseccion = tabla1[i][j];
		if (interseccion.equals(" ")) {
			if (pivote > 0) {
				MensajeDeError += "Error de Sintaxis: "+lex.get(pivote)+" despu�s de "+ lex.get(pivote-1)+" en la l�nea "+ linea +"\n" ; 	
			}else {
				MensajeDeError += "Error de Sintaxis: "+lex.get(pivote)+" al inicio de la l�nea 1 \n" ; 
			}
			errP = false;
			MensajeDePila += pila+"\n";
		}else {
			String[] interseccionArray = interseccion.split(" ");
			if (!(lex.get(pivote).equals("ciP") && cerra == 0)) {
				pila.pop();
				for (int k = interseccionArray.length; k > 0; k--) {
					pila.push(interseccionArray[k - 1]);
				}
				if (pila.peek().equalsIgnoreCase("�")) {
					pila.pop();
				}
				if (pila.peek().equalsIgnoreCase(lex.get(pivote))) {
					MensajeDePila += pila+"\n";
					pila.pop();
					MensajeDePila += pila+"\n";
					errP = true;
				} else {
					MensajeDePila += pila+"\n";
					procesoApilAndDesapil(pivote);
				}
			}else {
				MensajeDePila += pila+"\n";
				System.out.println("s");
			}
		}
	}
}