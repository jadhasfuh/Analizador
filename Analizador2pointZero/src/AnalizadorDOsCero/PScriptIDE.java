package AnalizadorDOsCero;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PScriptIDE implements KeyListener, MouseWheelListener, MouseListener {

	
	/*
	 * Declaracion de variables y elementos que sirven como globales
	*/
	private JFrame frmPstudio;
	JTextArea areaTrabajo;
	JTextPane lineas;
	TextArea at;
	JTextField ct;
	FileDialog fd;
	JScrollPane sp, sp2,sp3,sp2s;
	JLabel etru;
	String reservada; //inica la palabra reservada del metodo
	HighlightPainter colorin;
	ArrayList<String> tokens = new ArrayList<String>();
	static String salida; 
	int pos = 0, pos2 = 0, fin = 0, fin2 = 0;
	private JTextPane consola, consolaS;
	
	/*
	 * M�todo main, y creacion de un frame utilizando la clase creada en WinBuild
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					PScriptIDE window = new PScriptIDE();
					window.frmPstudio.setLocationRelativeTo(null);
					window.frmPstudio.setVisible(true);
			}
		});
	}

	/*
	 * Esta es la clase, sin embargo construye la ventana por separado en un m�todo
	*/
	public PScriptIDE() {
		initialize();
	}
	
	/*
	 * Aqu� se crea nuestro frame
	*/
	private void initialize() {
		frmPstudio = new JFrame();
		frmPstudio.setResizable(false);
		frmPstudio.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmPstudio.setTitle("PStudio");
		frmPstudio.setBounds(100, 100, 1128, 737);
		frmPstudio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar barraM = new JMenuBar();
		JMenu archivo = new JMenu("Archivo");
		JMenu opciones = new JMenu("Opciones");
		JMenu run = new JMenu("Run");
	
		barraM.setBackground(Color.LIGHT_GRAY);

		/*
		 * Bot�n Abrir en el menu y su actionPerformed, este sirve para abrir documentos
		*/
		JMenuItem abrir = new JMenuItem("Abrir");
		archivo.add(abrir);
		abrir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser seleccionado = new JFileChooser();
				File archiv;
				Buscador b = new Buscador();
				FileNameExtensionFilter fil= new FileNameExtensionFilter("PAU","pau");
				seleccionado.setFileFilter(fil);
				seleccionado.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				if (seleccionado.showDialog(frmPstudio, "Abrir") == JFileChooser.APPROVE_OPTION) {
					archiv = seleccionado.getSelectedFile();
					if (archiv.canRead()) {
						if (archiv.getName().endsWith("pau")) {
							String contenido = b.AbrirTexto(archiv);
							areaTrabajo.setText(contenido);
							nlineaTextArea();
							ct.setText(""+seleccionado.getSelectedFile().getAbsolutePath());
							reservada = seleccionado.getSelectedFile().getName();
							String []palabraid=reservada.split("\\.");
						}
					}
				}
			}
		});
		
		/*
		 * Bot�n Grabar en el menu y su actionPerformed, este sirve para guardar documentos existentes
		*/
		JMenuItem grabar = new JMenuItem("Guardar");
		archivo.add(grabar);
		grabar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String ruta=ct.getText();
				if(ruta.compareTo("")!=0) {
					String contenido=areaTrabajo.getText();
					Grabar(ruta,contenido);
				}else {
					JOptionPane.showMessageDialog(null, "No hay una ruta especifica para el archivo");
				}
			}
		});
		
		
		/*
		 * Bot�n Guardar como... en el menu y su actionPerformed, este sirve para crear documentos nuevos
		*/
		JMenuItem gua = new JMenuItem("Guardar como..");
		archivo.add(gua);
		gua.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser seleccionado = new JFileChooser();
				
				FileNameExtensionFilter fil= new FileNameExtensionFilter("PAU","pau");
				seleccionado.setFileFilter(fil);
				File archiv;
				Buscador b = new Buscador();
				if (seleccionado.showDialog(null, "Guardar como") == JFileChooser.APPROVE_OPTION) {
					archiv = seleccionado.getSelectedFile();
					if(archiv.getName().endsWith("pau")) {
						String conf=b.guardar(archiv, areaTrabajo.getText());
						if(conf!=null) {
							JOptionPane.showMessageDialog(null, conf);
							areaTrabajo.setText("");
						}else {
							JOptionPane.showMessageDialog(null, "Formato invalido");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Formato no valido");
					}
				}
			}
		});
		
		/*
		 * Cambia el color del editor (Area de Texto)
		*/
		JMenuItem editor = new JMenuItem("Color de Editor");
		opciones.add(editor);
		editor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				colorBack();
			}
		});
		
		/*
		 * Cambia el color de la fuente
		*/
		JMenuItem fuente = new JMenuItem("Color de Fuente");
		opciones.add(fuente);
		fuente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				colorFont();
			}
		});
		
		/*
		 * Aqu� mandamos a llamar a la clase de an�lisis lexico, con el bot�n run analisis
		*/
		JMenuItem ana = new JMenuItem("Compilar");
		ana.setIcon(new ImageIcon(PScriptIDE.class.getResource("/AnalizadorDOsCero/img/play.png")));
		run.add(ana);		
		
		ana.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!(ct.getText().length() < 1)) {

					tokens.clear();
					Lexer lexer = new Lexer();
					lexer.LexerL(ct.getText(), areaTrabajo);
					String error = "";

					Sintak sintax = new Sintak();
					Highlighter lexH = areaTrabajo.getHighlighter();
					Highlighter sinH = areaTrabajo.getHighlighter();
					lexH.removeAllHighlights();
					sinH.removeAllHighlights();

					// Se borra lo que tiene la consola
					consola.setText("");
					consolaS.setText("");
					consola.setText(consola.getText() + "An�lisis L�xico\n");
					consola.setText(consola.getText() + "-----------------\n");
					consola.setText(consolaS.getText() + "An�lisis Sint�ctico\n");
					consola.setText(consolaS.getText() + "-----------------\n");

					while (!lexer.isExausthed()) { // Este es equivalente al HASNEXT
						if (lexer.currentLexema() != null) {
							tokens.add(lexer.currentToken() + ""); // Solo para comprobar
							if (tokens.get(tokens.size() - 1).equalsIgnoreCase("error")) {
								try {
									pos = areaTrabajo.getLineStartOffset(lexer.lene - 1);
									fin = areaTrabajo.getLineEndOffset(lexer.lene - 1);
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
								try {
									if (pos > 0)
										lexH.addHighlight(pos, fin, DefaultHighlighter.DefaultPainter);
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
								if (!error.contains(" en linea " + lexer.lene + "\n"))
									error += ("Error l�xico: " + lexer.currentLexema() + " en linea " + lexer.lene
											+ "\n");
							} else {
								if (!sintax.AS(lexer.currentToken() + "", lexer.lene)) {
									consola.setText(consola.getText() + lexer.currentLexema() + "     "
											+ lexer.currentToken() + "\n"); // Luego se imprime
									consolaS.setText(consolaS.getText() + sintax.MensajeDePila);
									try {
										pos2 = areaTrabajo.getLineStartOffset(lexer.lene - 1);
										fin2 = areaTrabajo.getLineEndOffset(lexer.lene - 1);

									} catch (BadLocationException e1) {
										e1.printStackTrace();
									}
									try {
										if (pos2 > 0)
											sinH.addHighlight(pos2, fin2,
													new DefaultHighlighter.DefaultHighlightPainter(
															new Color(255, 150, 0)));
									} catch (BadLocationException e1) {
										e1.printStackTrace();
									}
								} else {
									consola.setText(consola.getText() + lexer.currentLexema() + "     "
											+ lexer.currentToken() + "\n"); // Luego se imprime
									consolaS.setText(consolaS.getText() + sintax.MensajeDePila);
								}
							}
						}
						lexer.siguiente();// Avanza
					}
					if (lexer.isSuccessful() && !tokens.contains("error")) {
						consola.setText(consola.getText() + "-----------------\n");
						consola.setText(consola.getText() + "An�lisis L�xico finalizado correctamente\n");
						consola.setText(consola.getText() + "-----------------\n");
					} else {
						consola.setText(consola.getText() + "-----------------\n");
						consola.setText(consola.getText() + "An�lisis L�xico finalizado con errores\n");
						consola.setText(consola.getText() + "-----------------\n");
						consola.setText(consola.getText() + error + "\n"); // Imprime los errores
						consola.setText(consola.getText() + lexer.mensajeError() + "\n");
						consola.setText(consola.getText() + "-----------------\n");
					}
					if (sintax.aceptado()) {
						consolaS.setText(consolaS.getText() + "-----------------\n");
						consolaS.setText(consolaS.getText() + "An�lisis Sint�ctico finalizado correctamente\n");
						consolaS.setText(consolaS.getText() + "-----------------\n");
					} else {
						consolaS.setText(consolaS.getText() + "-----------------\n");
						consolaS.setText(consolaS.getText() + "An�lisis Sint�ctico finalizado con errores\n");
						consolaS.setText(consolaS.getText() + "-----------------\n");
						consolaS.setText(consolaS.getText() + sintax.MensajeDeError + "\n"); // Imprime los errores
						consolaS.setText(consolaS.getText() + "-----------------\n");
					}
				} else {
					JOptionPane.showMessageDialog(null, "No hay archivos abiertos");
				}
			}
		});

		barraM.add(archivo);
		barraM.add(opciones);
		barraM.add(run);
		frmPstudio.setJMenuBar(barraM);
		frmPstudio.getContentPane().setLayout(null);

		areaTrabajo = new JTextArea();
		areaTrabajo.setForeground(Color.DARK_GRAY);
		areaTrabajo.setFont(new Font("Tahoma", Font.PLAIN, 19));
		areaTrabajo.setBounds(42, 0, 1076, 353);
		areaTrabajo.addKeyListener(this);
		areaTrabajo.addMouseWheelListener(this);
		areaTrabajo.addMouseListener(this);

		sp = new JScrollPane(areaTrabajo);
		sp.setBounds(42, 0, 1065, 353);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp.getVerticalScrollBar().setBackground(SystemColor.control);
		sp.setBackground(Color.white);
		sp.setOpaque(true);

		frmPstudio.getContentPane().add(sp);

		lineas = new JTextPane();
		lineas.setEditable(false);
		lineas.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lineas.setBackground(SystemColor.controlHighlight);
		lineas.setBounds(0, 0, 40, 353);
		sp3 = new JScrollPane(lineas);
		sp3.setBounds(0, 0, 40, 353);
		sp3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		sp3.getVerticalScrollBar().setBackground(SystemColor.control);
		sp3.setBackground(Color.white);
		sp3.setOpaque(true);

		frmPstudio.getContentPane().add(sp3);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.control);
		panel.setBounds(0, 665, 1110, 43);
		etru = new JLabel("Ruta del archivo");
		ct = new JTextField(40);
		ct.setEnabled(false);
		panel.add(etru);
		panel.add(ct);
		frmPstudio.getContentPane().add(panel);

		consola = new JTextPane();
		consola.setForeground(Color.WHITE);
		consola.setBackground(Color.DARK_GRAY);
		consola.setFont(new Font("Tahoma", Font.PLAIN, 14));
		consola.setBounds(0, 355, 552, 309);

		consolaS = new JTextPane();
		consolaS.setForeground(Color.WHITE);
		consolaS.setBackground(Color.GRAY);
		consolaS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		consolaS.setBounds(553, 355, 552, 309);

		sp2s = new JScrollPane(consolaS);
		sp2s.setBounds(553, 355, 552, 309);
		sp2s.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp2s.getVerticalScrollBar().setBackground(SystemColor.control);
		sp2s.setBackground(Color.white);
		sp2s.setOpaque(true);

		sp2 = new JScrollPane(consola);
		sp2.setBounds(0, 355, 552, 309);
		sp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp2.getVerticalScrollBar().setBackground(SystemColor.control);
		sp2.setBackground(Color.white);
		sp2.setOpaque(true);
		frmPstudio.getContentPane().add(sp2);
		frmPstudio.getContentPane().add(sp2s);

		frmPstudio.getContentPane().add(sp2);

		sp.getViewport().addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				sp3.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getValue());
			}
		});
	}

	/*
	 * La clase grabar llamada desde guardar
	*/
	public void Grabar(String r, String c) {
		 FileWriter F;
		 try {
			 F=new FileWriter(r);
			 F.write(c);
			 F.close();
		 }catch(IOException e) {
			 JOptionPane.showMessageDialog(null, "Error de escritura");
		 }
	 }
	
	/*
	 * La clase colorBack llamada desde color de fondo
	*/
	public void colorBack() {
        //presenta el dialogo de selecci�n de colores
        Color color = JColorChooser.showDialog(null, "Selecci�n de Color", Color.black);
        if (color != null) {    //si un color fue seleccionado
            //se establece como color del fuente y cursor
            areaTrabajo.setBackground(color);
        }
    }

	/*
	 * La clase colorFont llamada desde color de fuente
	*/
	public void colorFont() {
        //presenta el dialogo de selecci�n de colores
        Color color = JColorChooser.showDialog(null, "Selecci�n de Color", Color.black);
        if (color != null) {    //si un color fue seleccionado
            //se establece como color del fuente y cursor
            areaTrabajo.setForeground(color);
        }
    }
	
	
	/*
	 * Eventos key, para el n�mero de lineas en el Area de Texto L�neas
	*/
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void keyPressed(KeyEvent e) {
		nlineaTextArea();
	}
	
	public void nlineaTextArea() {
		lineas.setText("");
		for (int i = 0; i < areaTrabajo.getLineCount(); i++) {
			lineas.setText(lineas.getText()+(i+1)+"\n");
		}
		sp3.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getValue());
	}
	public void keyReleased(KeyEvent e) {
		
	}
	
	/*
	 * Se da movimiento a ambos scroll con los mismos valores
	*/
	public void mouseWheelMoved(MouseWheelEvent e) {
	      //indica el valor del mouse
	       int notches = e.getWheelRotation();
	       //arriba
	       if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
	           sp.getVerticalScrollBar().getUnitIncrement(1);
	       } else { //scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL lo bloquea
	    	   sp.getVerticalScrollBar().getBlockIncrement(1);
	       }
	}

	/*
	 * Esta parte dar� movimiento a los scrolls haciendo uso del MouseScroll, para una mejor experiencia
	*/
	public void mouseClicked(MouseEvent arg0) {
		sp3.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getValue());
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {
		sp3.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getValue());
	}
	public void mouseReleased(MouseEvent arg0) {
		sp3.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getValue());
	}
}