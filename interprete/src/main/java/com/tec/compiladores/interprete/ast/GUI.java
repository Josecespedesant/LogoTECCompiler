package com.tec.compiladores.interprete.ast;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.maven.plugin.logging.Log;

import com.tec.compiladores.interprete.LogoCustomVisitor;
import com.tec.compiladores.interprete.LogoLexer;
import com.tec.compiladores.interprete.LogoParser;
import com.tec.compiladores.interprete.ThrowingErrorListener;

public class GUI {


    private static JPanel interfaz;
	private static JButton carg;
	private static JButton comp;
	private static JButton ejec;
	private static JButton print;
	private static JButton clear;
	private static JTextArea area;
	private static JFrame window2;
	private static Boolean flag = true;
	private static Turtle turtle = new Turtle();
	private static LogoParser parser;
	private static ParseTree tree;
	private static JTextArea consola;
    
	
	
	public static void main(String[] args) {
		
    	carg = new JButton("Cargar");
    	clear = new JButton("Limpiar");
    	comp = new JButton("Compillar");
    	ejec = new JButton("Ejecutar");
    	print = new JButton("Imprimir");
    	area = new JTextArea("Introducir código...");
    	consola = new JTextArea("Consola");
    	
		String program = "test/test.logo";
		JScrollPane scroll1 = new JScrollPane(area);
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JScrollPane scroll2 = new JScrollPane(consola);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
    	area.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(flag) {
					area.selectAll();			
				}		
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				flag = false;
				return;
			}
    	});
    	
    	JPanel panel2 = new JPanel();
    	panel2.setLayout(new FlowLayout());
    	
    	panel2.add(carg); panel2.add(comp);panel2.add(ejec);panel2.add(print); panel2.add(clear);
    	
    	
    	Border border = BorderFactory.createLineBorder(Color.black);
    	area.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    	
    	Border border2 = BorderFactory.createLineBorder(Color.black);
    	consola.setBorder(BorderFactory.createCompoundBorder(border2, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    	
    	
    	consola.setEditable(false);
    	interfaz = new JPanel();
    	interfaz.setLayout(new BoxLayout(interfaz, BoxLayout.Y_AXIS));
    	interfaz.add(scroll1);
    	interfaz.add(scroll2);
    	interfaz.add(panel2);
    	
        window2 = new JFrame("LogoIDE");
        window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window2.setSize(500,600);
        window2.add(interfaz);
        window2.setVisible(true);
    	
        
        clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				consola.setText("");
				
			}
        	
        });
        
        carg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Logo files", "logo");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(interfaz);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					
					String path = chooser.getSelectedFile().getPath();
					area.append("\n");
					
					try {
						area.read(new BufferedReader(new FileReader(path)), null);
						
					} catch (FileNotFoundException e1) {
						consola.append("\n");
    					consola.append(e1.getMessage());
						e1.printStackTrace();
					} catch (IOException e1) {
						consola.append("\n");
    					consola.append(e1.getMessage());
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
        	
        });
        
        print.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog frame = new JDialog();
		        JPanel panel = new JPanel();
		        if(parser!=null) {
		        	TreeViewer viewr = new TreeViewer(Arrays.asList(
			                parser.getRuleNames()),tree);
			        viewr.setScale(1.5);//scale a little
			        panel.add(viewr);
			        frame.add(panel);
			        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			        frame.setSize(1000,500);
			        frame.setResizable(true);
			        frame.setVisible(true);
					LogoCustomVisitor visitor = new LogoCustomVisitor();
					visitor.visit(tree);
					JScrollPane jScrollPane = new JScrollPane(panel);
					jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					frame.getContentPane().add(jScrollPane);

		        }else {
		        	consola.append("\nPor favor ejecute su programa.");
		        }
		        				
			}
        	
        });
        
        comp.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent me) throws ParseCancellationException{
				
				File fold=new File(program);
				fold.delete();
				File fnew=new File(program);
				String source = area.getText();
				
				try {
				    FileWriter f2 = new FileWriter(fnew, false);
				    f2.write(source);
				    f2.close();
				} catch (IOException e) {
					consola.append("\n");
					consola.append(e.getMessage());
				}           
				
				
				LogoLexer lexer = null;
				try {
					lexer = new LogoLexer(new ANTLRFileStream(program));
					lexer.removeErrorListeners();
					lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
					
				} catch (ParseCancellationException e) {
					consola.append("\n");
					consola.append(e.getMessage());
					e.printStackTrace();
					
				} catch (IOException a) {
					consola.append("\n");
					consola.append(a.getMessage());
					a.printStackTrace();
				}
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				try {
					Turtle taux = new Turtle("a");
					
					parser = new LogoParser(tokens);
					parser.removeErrorListeners();
					parser.addErrorListener(ThrowingErrorListener.INSTANCE);
					parser.consola = consola;
					
					parser.turtle = taux;

					tree = parser.program(); 
					taux.clear();
					
				} catch (ParseCancellationException e) {
					consola.append("\n");
					consola.append(e.getMessage());
					e.printStackTrace();
				}
				
				

			}
        	
        });
        
        ejec.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent me) {
        		new Thread() {
        			public void run(){
        				File fold=new File(program);
        				fold.delete();
        				File fnew=new File(program);
        				String source = area.getText();
        				
        				try {
        				    FileWriter f2 = new FileWriter(fnew, false);
        				    f2.write(source);
        				    f2.close();
        				} catch (IOException e) {
        					consola.setText("");
	    					consola.setText(e.getMessage());
        				    e.printStackTrace();
        				}           
        				
        				turtle.clear();
        				turtle.up();
        				turtle.home();
        				turtle.down();
        				LogoLexer lexer = null;
						try {
							lexer = new LogoLexer(new ANTLRFileStream(program));
							lexer.removeErrorListeners();
							lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
						} catch (ParseCancellationException e) {
							consola.setText("");
	    					consola.setText(e.getMessage());
							e.printStackTrace();
						} catch (IOException e) {
							consola.setText("");
	    					consola.setText(e.getMessage());
	    					e.printStackTrace();
						}
					
						CommonTokenStream tokens = new CommonTokenStream(lexer);
	        			
	        			
	        			try {
	        				parser = new LogoParser(tokens);
		        			parser.removeErrorListeners();
		        			parser.addErrorListener(ThrowingErrorListener.INSTANCE);
		        			parser.consola = consola;
		        			parser.turtle = turtle;

	    					tree = parser.program(); 
	    				} catch (ParseCancellationException e) {
	    					consola.setText("");
	    					consola.setText(e.getMessage());
	    					e.printStackTrace();
	    				}
	        
        			}
        		}.start();
        	}
        });
        

	}

}
