package AnalizadorDOsCero;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Lexer {
	
    private StringBuilder entrada = new StringBuilder();
    private Token7point0 token;
    private String lexema;
    private boolean detener = false;
    private String mensajeError = "";
    private Set<Character> espaciosBlanco = new HashSet<Character>();
    public Lexer() {
    	
	}

    public  void LexerL(String filePath) {
        try (Stream<String> st = Files.lines(Paths.get(filePath))) {
            st.forEach(entrada::append);
        } catch (IOException ex) {
        	detener = true;
        	mensajeError = "Error en lectura de archivo: " + filePath;
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

        if (entrada.length() == 0) {
        	detener = true;
            return;
        }

        ignoraEspacios();

        if (findNextToken()) {
            return;
        }

        detener = true;

        if (entrada.length() > 0) {
        	mensajeError = "Error Léxico: '" + entrada.charAt(0) + "'";
        }
    }

    private void ignoraEspacios() {
        int charsAeliminar = 0;

        while (espaciosBlanco.contains(entrada.charAt(charsAeliminar))) {
        	charsAeliminar++;
        	System.out.println("Chars: "+charsAeliminar);
        }

        if (charsAeliminar > 0) {
        	entrada.delete(0, charsAeliminar);
        }
    }

    private boolean findNextToken() {
        for (Token7point0 t : Token7point0.values()) {
            int end = t.endOfMatch(entrada.toString());

            if (end != -1) {
                token = t;
                lexema = entrada.substring(0, end);
                entrada.delete(0, end);
                return true;
            }
        }

        return false;
    }

    public Token7point0 currentToken() {
        return token;
    }
    private String lex;
    public String LexemaToken(String token) {
    	Token7point0 t1=Token7point0.valueOf(token);
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








