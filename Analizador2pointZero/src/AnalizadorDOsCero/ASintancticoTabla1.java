package AnalizadorDOsCero;

import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

public class ASintancticoTabla1 {
	
	ArrayList<String> lex = new ArrayList<String>();
	ArrayList<String> elexe = new ArrayList<String>();
	ArrayList<String> terminales = new ArrayList<String>();
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
	
	public void llenarFyC() {
		for (int i = 0; i < tabla1.length; i++) {
			terminales.add(tabla1[i][0]);
		}
		for (int i = 0; i < tabla1[0].length; i++) {
			elexe.add(tabla1[0][i]);
		}
	}
	
	public ASintancticoTabla1(ArrayList<String> lexemas) {
		lex = lexemas;
		llenarFyC();
		pila.push("finale");
		pila.push("E");
	}
	
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
	
	public void procesoApilAndDesapil (int pivote) {
		if (terminales.contains(pila.peek()) && elexe.contains(lex.get(pivote))) {
			apila(terminales.indexOf(pila.peek()), elexe.indexOf(lex.get(pivote)),pivote);
		}
	}
	
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