
package com.tec.compiladores.interprete;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

	private static final String EXTENSION = "logo";

	public static void main(String[] args) throws IOException {
		String program = args.length > 1 ? args[1] : "test/test." + EXTENSION;

		System.out.println("Interpreting file " + program);

		LogoLexer lexer = new LogoLexer(new ANTLRFileStream(program));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LogoParser parser = new LogoParser(tokens);

		LogoParser.ProgramContext tree = parser.program();

		LogoCustomVisitor visitor = new LogoCustomVisitor();
		visitor.visit(tree);

		System.out.println("Interpretation finished");

	}

}
