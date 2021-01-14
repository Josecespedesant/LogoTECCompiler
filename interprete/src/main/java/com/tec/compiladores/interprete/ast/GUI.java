package com.tec.compiladores.interprete.ast;

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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	private static JTextArea area;
	private static JScrollPane pane;
	private static JFrame window2;
	private static Boolean flag = true;
	private static Turtle turtle = new Turtle();
	private static LogoParser parser;
	private static ParseTree tree;
    
	
	
	public static void main(String[] args) {
		
    	carg = new JButton("Cargar");
    	comp = new JButton("Compillar");
    	ejec = new JButton("Ejecutar");
    	print = new JButton("Imprimir");
    	area = new JTextArea("Introducir código...");
    	
		String program = "test/test.logo";

		
		
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
    	pane = new JScrollPane(area);
    	pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    	
    	JPanel panel2 = new JPanel();
    	panel2.setLayout(new FlowLayout());
    	
    	panel2.add(carg); panel2.add(comp);panel2.add(ejec);panel2.add(print);
    	
    	interfaz = new JPanel();
    	interfaz.setLayout(new BoxLayout(interfaz, BoxLayout.Y_AXIS));
    	interfaz.add(area);
    	interfaz.add(panel2);
    	
        window2 = new JFrame("LogoIDE");
        window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window2.setSize(500,500);
        window2.add(interfaz);
        window2.setVisible(true);
    	
        carg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Logo files", "logo");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(interfaz);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					
					String path = chooser.getSelectedFile().getPath();
					area.setText("");
					
					try {
						area.read(new BufferedReader(new FileReader(path)), null);
					
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
        	
        });
        
        print.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = new JFrame("Antlr AST");
		        JPanel panel = new JPanel();
		        TreeViewer viewr = new TreeViewer(Arrays.asList(
		                parser.getRuleNames()),tree);
		        viewr.setScale(1.5);//scale a little
		        panel.add(viewr);
		        frame.add(panel);
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setSize(1000,500);
		        frame.setResizable(true);
		        frame.setVisible(true);
				LogoCustomVisitor visitor = new LogoCustomVisitor();
				visitor.visit(tree);
				JScrollPane jScrollPane = new JScrollPane(panel);
				jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				frame.getContentPane().add(jScrollPane);
				
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
				    e.printStackTrace();
				}           
				
				Turtle taux = new Turtle("");
				
				LogoLexer lexer = null;
				try {
					lexer = new LogoLexer(new ANTLRFileStream(program));
					lexer.removeErrorListeners();
					lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
					
				} catch (ParseCancellationException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					
				} catch (IOException a) {
					System.out.println(a.getMessage());
					a.printStackTrace();
				}
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				try {
					parser = new LogoParser(tokens);
					parser.removeErrorListeners();
					parser.addErrorListener(ThrowingErrorListener.INSTANCE);
					parser.turtle = taux;

					tree = parser.program(); 
					
				} catch (ParseCancellationException e) {
					System.out.println(e.getMessage());
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
							System.out.println(e.getMessage());
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						CommonTokenStream tokens = new CommonTokenStream(lexer);
	        			
	        			
	        			try {
	        				parser = new LogoParser(tokens);
		        			parser.removeErrorListeners();
		        			parser.addErrorListener(ThrowingErrorListener.INSTANCE);
		        			parser.turtle = turtle;

	    					tree = parser.program(); 
	    				} catch (ParseCancellationException e) {
	    					System.out.println(e.getMessage());
	    					e.printStackTrace();
	    				}
	        			

	        			

	        		
        				

        			}
        		}.start();
        	}
        });
        

	}

}
