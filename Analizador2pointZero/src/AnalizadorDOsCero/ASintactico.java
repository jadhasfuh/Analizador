package AnalizadorDOsCero;

import java.util.ArrayList;
import java.util.Stack;

public class ASintactico {
	String caracterE= "";
	int posE=0;
	int indi=0 , col=0, x,y;
	Stack<String> error = new Stack<String>();
	Stack<String> let = new Stack<String>();
	Stack<String> entrada = new Stack<String>();//cadena de entrada
	Stack<String> pila = new Stack<String>();//pila de jale
	String mensaje="", acomodo2[], acomodo[],noter,comp,tran,tabla[][]; /*= {{" ","identificador","id_ent", "op_sum", "op_mult", "op_res", "op_div", "abP","ciP", "puntcoma","del_id"},
						{"E", "T E'","T E'","","","","","T E'","","",""},
						{"E'", "","","op_sum T E'","% ","op_res T E'","% ","","% ","% ","del_id T E'"},
						{"T", "F T'","F T'","","","","","F T'","","",""},
						{"T'", "","","% ","op_mult F T'","% ","op_div F T'","","% ","% ","% "},
						{"F","identificador ","id_ent ","","","","","abP E ciP","","",""}};*/
	Tablabirigilla t;
	public ASintactico () {
		pila.clear();
		pila.add("puntcoma");
		pila.add("E");
		puntero=0;
		caracterE= "";
		posE=0;
		b=false;
		t=new Tablabirigilla();
		tabla=t.retornaTabla();
	}
	/*public static void main(String[] args) {
		System.out.println("Prueba sintacticos");
	}*/
	public void llenado() {

		acomodo2=new String[entrada.size()];
		System.out.println("tamaño de la cadena: "+entrada.size());
		acomodo();
	}
	int puntero=0;
	boolean b=false;
	int n=0;
	public void Buscar() {
		if (puntero<acomodo2.length && !pila.empty()) {
			n++;
			y=0;
			x=1;
			System.out.println("veces "+(n));
			System.out.println("cadena de entrada: "+ acomodo2[puntero]);
			comp=acomodo2[puntero];
			String aux;
			tran = pila.peek();
			//terminales
			System.out.println("pila de entrada "+ tran);
				for (col = 0; col < tabla[0].length; col++)
					if (comp.equals(tabla[0][col]))
						y = col;
				//no hay nada
				// No terminales
				for (indi = 0; indi < tabla.length; indi++)
					if (tran.equals(tabla[indi][0]))
						x = indi;
				if( y==0 || x==0) {
					noter="";
				}else {
					noter = tabla[x][y];
				}
				System.out.println("noterminal "+noter);
				System.out.println("pos x "+ x +"pos y "+ y);
				if ((noter.equals("") || noter.equals("Erro ")) && !(acomodo2[puntero].equals(tran))) {
					System.out.println("Guarda error del terminal "+acomodo2[puntero]);
					error.push(acomodo2[puntero]);
					System.out.println("punta: "+puntero);
					letraE(puntero);
					puntero=acomodo2.length;
				}
				else
				{
					alreves_volteado();
					aux = pila.peek();
					System.out.println("ultimo digito de la pilona "+pila.peek());
					
					if(aux.equalsIgnoreCase(acomodo2[puntero])) {
						puntero++;
						pila.pop();
					}
					else if (aux.equals("%")) {
							pila.pop();
							aux="";
						}
				}
				Buscar();
			}	
			// componentes
			else {
			if(pila.isEmpty()) {
				b=true;
				System.out.println("Aceptacion");
			}else {
				b=false;
				System.out.println("No termino");
			}
			Verificar();
		}
		
	}

	public void alreves_volteado() {
			pila.pop();
			if((noter.equals("") || noter.equals("Erro ")) && acomodo2[puntero].equals(tran)) {
				pila.push(tran);
			}else {
				String a[] = noter.split(" ");
				for (int ind = a.length-1; ind != -1; ind--)
				{
					System.out.println("terminal "+a[ind]);
					pila.push(a[ind]);
				}
			}
			
	}
	public void Verificar()
	{
		if(b==true && error.isEmpty())//no hay nada
		{
			mensaje+="Sintaxis correcta :'D\n";
			
		}
		else {
			mensaje+="Error de sintaxis\nFaltantes en la pila: \n";	
		while (!pila.isEmpty()) {
			mensaje+=" "+ pila.peek()+"\n";
			pila.pop();
		}
		mensaje+="\nError producido por token ";
		while (!error.isEmpty()) {
			mensaje+=" "+ error.peek()+"\n";
			error.pop();
		}
		mensaje+="\n Error encontrado cerca de: "+returnE();
		mensaje+="\n Posicion: "+(numE()+1);
		}
		}
	public void letraE(int n) {
		caracterE=acomodo[n];
		posE=n;
	}
	//Error
	public String returnE() {
		return caracterE;
	}
	public int numE() {
		return posE;
	}
	//Acomoda las lineas de entrada
	public void acomodo() {
	 acomodo2 =new String[entrada.size()];
			for (int i = acomodo2.length-1;  i!=-1; i--) {
				acomodo2[i]=entrada.peek();
				entrada.pop();
			}
			for (int i = 0; i < acomodo2.length; i++) {
				System.out.println("entrada :"+acomodo2[i]);
			}
	}
	public void letras() {
		 acomodo =new String[let.size()];
				for (int i = acomodo.length-1;  i!=-1; i--) {
					acomodo[i]=let.peek();
					let.pop();
				}
		}



}
