
package com.tec.compiladores.interprete;
import java.io.IOException;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import com.tec.compiladores.interprete.ast.Turtle;
public class Main {

	private static final String EXTENSION = "logo";
	

	public static void main(String[] args) throws IOException {
		String program = args.length > 1 ? args[1] : "test/test." + EXTENSION;

		System.out.println("Interpreting file " + program);

		LogoLexer lexer = new LogoLexer(new ANTLRFileStream(program));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LogoParser parser = new LogoParser(tokens);
		ParseTree tree = parser.program(); 
		//LogoParser.ProgramContext tree = parser.program();
		System.out.println(tree.toStringTree(parser));
		

		JFrame frame = new JFrame("Antlr AST");
        JPanel panel = new JPanel();
        TreeViewer viewr = new TreeViewer(Arrays.asList(
                parser.getRuleNames()),tree);
        viewr.setScale(1.5);//scale a little
        panel.add(viewr);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,500);
        frame.setVisible(true);
		LogoCustomVisitor visitor = new LogoCustomVisitor();
		visitor.visit(tree);

		System.out.println("Interpretation finished");

	}

}
