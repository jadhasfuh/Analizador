package AnalizadorDOsCero;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Lexer {
	
    private StringBuilder entrada = new StringBuilder();
    private Tokens token;
    private String lexema;
    private boolean detener = false;
    private String mensajeError = "";
    private Set<Character> espaciosBlanco = new HashSet<Character>();
    ArrayList<String> lineas = new ArrayList<String>();
    int nlinea = 0;
    int lene = 0;
 
    public void LexerL(String filePath, JTextArea ta) {
    	
    	try {
    		BufferedReader in = new BufferedReader(new FileReader(filePath));
    		String line = null;
    		while ((line = in.readLine()) != null) {
    			if (!line.equals("")) {
    				lineas.add(line.trim());     //anade a lista de lineas 
				}
    		}
    	}catch (IOException e) {
    		detener = true;
    		mensajeError += "Error en lectura de archivo: " + filePath;
    		return;
		}
    
        espaciosBlanco.add('\r');
        espaciosBlanco.add('\n');
        espaciosBlanco.add((char) 8);
        espaciosBlanco.add((char) 9);
        espaciosBlanco.add((char) 11);
        espaciosBlanco.add((char) 12);
        espaciosBlanco.add((char) 32);
        siguiente();
    }

    public void siguiente() {
        if (detener) {
            return;
        }
        if (lineas.get(nlinea).length() == 0) {
        	if (lineas.size() == nlinea+1) {
    			detener = true;
    			return;
    		}
			nlinea++;
		}
        if (!(lineas.size() == nlinea)) {
        	ignoraEspacios();
            if (findNextToken()) {
                return;
            }
		}        
        detener = true;
        if (lineas.get(nlinea).length() > 0) {
        	detener = true;
        	return;
		}
    }

    private void ignoraEspacios() {
        int charsAeliminar = 0;
        while(espaciosBlanco.contains(lineas.get(nlinea).charAt(charsAeliminar))) {
        	charsAeliminar++;
        }
        if (charsAeliminar > 0) {
        	lineas.set(nlinea, lineas.get(nlinea).substring(charsAeliminar,lineas.get(nlinea).length()));
        }
    }

    private boolean findNextToken() { 
    	String[] split = lineas.get(nlinea).split(" ");	 
        for (Tokens t : Tokens.values()) {
            int end = t.endOfMatch(split[0]);
            if (end != -1) {
                token = t;
                lexema = lineas.get(nlinea).substring(0,end);
                lineas.set(nlinea, lineas.get(nlinea).substring(end,lineas.get(nlinea).length()));
                lene = nlinea+1;
                return true;
            }
        }
        return false;
    }

    public Tokens currentToken() {
        return token;
    }
    public String currentLexema() {
        return lexema;
    }

    public boolean isSuccessful() {
        return mensajeError.isEmpty();
    }

    public String mensajeError() {
        return mensajeError;
    }

    public boolean isExausthed() {
        return detener;
    }
}

