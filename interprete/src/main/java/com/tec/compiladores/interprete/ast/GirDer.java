package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class GirDer implements ASTNode {
	private ASTNode opera;

	public GirDer(ASTNode opera) {
		super();
		this.opera = opera;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float f1 = Float.parseFloat(String.valueOf(opera.execute(symbolTable, turtle)));
		turtle.right(f1);
		return null;
	}

}
