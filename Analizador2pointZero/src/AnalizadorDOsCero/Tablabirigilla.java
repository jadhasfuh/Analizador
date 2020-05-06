package AnalizadorDOsCero;

public class Tablabirigilla {
	String tabla1[][], tabla2[][];

	public Tablabirigilla() {
		// TODO Auto-generated constructor stub
		String tabla1[][]= {{" ","inicio","finale","si","identificador","id_ent","abP","ciP","op_sum","op_res","op_mult","op_div","verdadero","falso","$","puntcoma","op_may","op_min","op_mayi","op_mini","op_negacion","op_comp","del_id","hacer","mientras","op_asig"},
							{"E","","","H G'","T E'","T E'","T E'","","","","","","T E'","T E'","Erro ","Erro ","","","","","","","","H G'","",""},
							{"E'","","% ","","","","","% ","op_sum T E'","op_res T E'","% ","% ","","","% ","% ","% ","% ","% ","% ","% ","% ","% ","","","op_asig T E'"},
							{"T","","","","F T'","F T'","F T'","Erro ","Erro ","Erro ","% ","% ","F T'","F T'","Erro ","Erro ","","","","","","","","","","Erro "},
							{"T'","","% ","","","","","% ","% ","% ","op_mult F T'","op_div F T'","","","% ","% ","op_may F T'","op_min F T'","op_mayi F T'","op_mini F T'","op_negacion F T'","op_comp F T'","del_id F T'","","","% "},
							{"F","","","","identificador ","id_ent ","abP E ciP","","","","","","verdadero ","falso ","Erro ","Erro ","","","","","","","","","","Erro "},
							{"G","H G'","Erro ","","","","","","","","","","","","Erro ","Erro ","","","","","","","","","","Erro "},
							{"G'","H G'","% ","","","","","","","","","","","","% ","% ","","","","","","","","%","%","Erro "},
							{"H","I H'","Erro","I H'","","","","","","","","","","","Erro ","Erro ","","","","","","","","I H'","",""},
							{"H'","% ","% ","","","","abP E ciP","","","","","","","","% ","% ","","","","","","","","% ","% ","Erro "},
							{"I","inicio E puntcoma finale","Erro ","si ","","","","","","","","","","","Erro ","Erro ","","","","","","","","hacer G mientras E puntcoma","Erro ","Erro "},
							};
		System.out.println("/nTABLA CARGADA ");
		tabla2=tabla1;
		String tabla2[][]= {{" ","$","puntcoma","crear","clase","funcion","id_func","id_clase","inicio","finale","principal","identificador"},
							{"B","","","crear B ","clase id_clase inicio C finale ","","","","","",""},
							{"B'","","","","","","","","","",""},
							{"C","","","crear X'","CC'","CC'","","","% ","% ","","CC'"},
							{"C'","","","","","","","","","","","X;"},
							{"X","","","","","","","","","","","identificador = L"},
							{"X'","","","","","funcion id_func inicio C finale ","","","","",""},
							{"L","","","","","","","","","",""},
							{"L'","","","","","","","","","",""},
							{"L'","","","","","","","","","",""},
							{"R","","","","","","","","","",""},
							{"R''","","","","","","","","","",""},
							{"E","","","","","","","","","",""},
							{"E'","","","","","","","","","",""},
							{"T","","","","","","","","","",""},
							{"T'"},
							{"F"},
							};
		String tabla3[][]= {{"","$","puntcoma","crear","clase","funcion","principal","inicio","finale","si","identificador","ent","dec","cart","cad","id_ent","id_dec","id_cad","id_cart","verdadero","falso","sino","op_asig","del_id","bool","abP","ciP","op_sum","op_res","op_mult","op_div","op_may","op_min","op_mayi","op_mini","op_negacion","op_comp","&","!!","||"},
							{"S","","% ","crear Q' ","","","","",""},
							{"Q'","","","clase id_clase inicio Q finale ","","","","","","",""},
							{"Q","% ","% ","Q S' crear ","","","","% ","","","Q S'"},	
							{"S'","","","","funcion id_func inicio V finale ","principal inicio V finale ","","","","ass puntcoma ","ass puntcoma ","ass puntcoma ","ass puntcoma ","ass puntcoma ","ass puntcoma ",""},
							{"ass", "% ","% ","","","","","","","","identificador op_asig Z","ent identificador = Z","dec identificador op_asig Z","cart identificador op_asig Z","cad identificador op_asig Z","Erro ","Erro ","Erro ","Erro ","Erro ","Erro ","","","bool identificador op_asig Z","","","","","","","","","","","","","","",""},
							{"Z","","","","","","","","","","M Z'","","","","","M Z'","M Z'","M Z'","M Z'","M Z'"},
							{"Z'","% ","% ","","","","","","","",""},
							{"M","","","","","","","","","","N M'","","","","","N M'","N M'","N M'","N M'","N M'"},
							{"M'","% ","% ","","","","","","","",""},
							{"N","","","","","","","","","","identificador ","","","","","id_ent","id_dec","id_cad","id_cart","verdadero","falso"},
							{"V","","","","","","","","","sents ","V v","","Erro ","sents "},
							{"v","% ","% ","","","funcion id_func inicio","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""},
							{"sents","","","","","","","","","sent sents ","sent sents ","Erro ","sent sents"},
							{"sent","","","","","","","","","si L inicio sents op finale ","A puntcoma ","A puntcoma ","A puntcoma",""},
							{"A","","","","","","","",""," ","identificador op_asig L","ent F","dec F","","","","","",""},
							{"L","","","","","","","","","","R L' ","","","L R'","L R'","L R'","L R'","Erro ","L R'"},
							{"L'","% ","% ","","","","","","","","% ","","","","","","","% "},
							{"R","% ","% ","","","","","","","","E R' ","","","E R'","E R'","E R'","","E R'"},
							{"R'","% ","% ","","","","","","","","","","","","","","",""},
							{"E","","","","","","","","","","T E'","","","T E'","T E'","T E'","","T E'"},
							{"E'","% ","% ","","","","","","","","","","","","","","","op_asig T E'"},
							{"T","","","","","","","","","","F T'","","","F T'","F T'","","% "},
							{"T'","% ","% ","","","","","","","","% ","","","",""},
							{"F","","","","","","","","","","identificador ","","","litcad ","litcar ","falso ","verdadero "},
							{"op","","","","","","","","% ","","","","","","","","","sino inicio sents finale ","op_asig"},
							{""}};
	}
	public String [][] retornaTabla (){
		return tabla2;
	}
}
