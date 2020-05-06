package AnalizadorDOsCero;

import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

public class ASintancticoTabla1 {
	
	ArrayList<String> lex = new ArrayList<String>();          	// Lo que arroja el analisis lexico
	ArrayList<String> elexe = new ArrayList<String>();			// Fila de entrantes
	ArrayList<String> terminales = new ArrayList<String>();		// Columna de terminales
	Stack<String> pila = new Stack<String>();
	
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
	public ASintancticoTabla1(ArrayList<String> lexemas) {
		lex = lexemas;
		llenarFyC();
		pila.push("finale");
		pila.push("E");
	}
	
	//Este es el único metodo que se llama
	public void AS() {
		for (int i = 0; i < lex.size(); i++) {
			procesoApilAndDesapil(i);
		}
		if (pila.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Se acepta");
		}else {
			JOptionPane.showMessageDialog(null, pila);
		}
	}
	
	//Este nos va a servir para llamarlo y mediante recursivida poder llenar hasta que se desapile y concuerde y retorne a AS
	public void procesoApilAndDesapil (int pivote) {
		if (terminales.contains(pila.peek()) && elexe.contains(lex.get(pivote))) {
			apila(terminales.indexOf(pila.peek()), elexe.indexOf(lex.get(pivote)),pivote);
		}
	}
	
	//Aqui apila hasta lo indicado en procesoApilAndDesapil()
	public void apila(int i, int j, int pivote) {

		String interseccion = tabla1[i][j];
		String[] interseccionArray = interseccion.split(" ");
		pila.pop();
		for (int k = interseccionArray.length; k > 0; k--) {
			pila.push(interseccionArray[k - 1]);
		}
		if (pila.peek().equalsIgnoreCase("ç")) {
			pila.pop();
		}
		if (pila.peek().equalsIgnoreCase(lex.get(pivote))) {
			System.out.println(pila);
			pila.pop();
			System.out.println(pila);
		} else {
			System.out.println(pila);
			procesoApilAndDesapil(pivote);
		}

	}
}