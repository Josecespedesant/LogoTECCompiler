package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Avanza implements ASTNode {
	private ASTNode opera;

	public Avanza(ASTNode opera) {
		super();
		this.opera = opera;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float f1 = Float.parseFloat(String.valueOf(opera.execute(symbolTable, turtle)));
		turtle.forward(f1);
		return null;
	}

}
