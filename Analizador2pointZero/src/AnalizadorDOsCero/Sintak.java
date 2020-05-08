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
	boolean errP = false; 
	
	String [][] tabla1 = 
		{
				{" " ,"identificador","id_ent","abP"  ,"ciP","op_sum"  ,"op_res","op_mult","op_div","id_cad","id_cart","finale"	},
				{"E" ,"T E’"		 ,"T E’"  ,"T E’" ," "  ," "	   ," "	    ," "	  ," "	   ,"T E’"	,"T E’"	  ," "		},
				{"E’"," "			 ," "	  ," "	  ,"ç"  ,"op_sum T E’","op_res T E’","ç"	  ,"ç"	   ," "	 	," "	  ,"ç"		},	
				{"T" ,"F T’"		 ,"F T’"  ,"F T’" ," "  ," "	   ," "	    ," "	  ," "	   ,"F T’"	,"F T’"	  ," "		},
				{"T’"," "			 ," "	  ," "	  ,"ç"  ,"ç"	   ,"ç"		,"op_mult F T’" ,"op_div F T’"," "	 	," "	  ,"ç"		},
				{"F" ,"identificador","id_ent","abP E ciP"," "," "     ," "		," "	  ," "	   ,"id_cad","id_cart"," "		}
		};
	
	//Este metodo llena la fila y columna en los arrays creados para ahorrarnos bucles de búsqueda
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
	
	//Este es el único metodo que se llama
	public boolean AS(String lexema) {
		MensajeDePila = "";
		lex.add(lexema);
		procesoApilAndDesapil(lex.size()-1);
		return errP;
	}
	
	//Este nos va a servir para llamarlo y mediante recursivida poder llenar hasta que se desapile y concuerde y retorne a AS
	public void procesoApilAndDesapil (int pivote) {
		if(pila.isEmpty()) {
			MensajeDeError += "Error de Sintaxis: "+lex.get(pivote)+" después de "+ lex.get(pivote-1)+"\n";errP = false;
			pila.push(" ");
		}
		if (terminales.contains(pila.peek()) && elexe.contains(lex.get(pivote))) {
			apila(terminales.indexOf(pila.peek()), elexe.indexOf(lex.get(pivote)),pivote);
		}
	}
	
	//Aqui apila hasta lo indicado en procesoApilAndDesapil()
	public void apila(int i, int j, int pivote) {
		String interseccion = tabla1[i][j];
		if (interseccion == " ") {
			MensajeDeError += "Error de Sintaxis: "+lex.get(pivote)+" después de "+ lex.get(pivote-1)+"\n" ; errP = false;
		}else {
			String[] interseccionArray = interseccion.split(" ");
			pila.pop();
			for (int k = interseccionArray.length; k > 0; k--) {
				pila.push(interseccionArray[k - 1]);
			}
			if (pila.peek().equalsIgnoreCase("ç")) {
				pila.pop();
			}
			if (pila.peek().equalsIgnoreCase(lex.get(pivote))) {
				MensajeDePila += pila+"\n";
				pila.pop();
				MensajeDePila += pila+"\n";errP = true;
			} else {
				MensajeDePila += pila+"\n";
				procesoApilAndDesapil(pivote);
			}
		}
	}
}