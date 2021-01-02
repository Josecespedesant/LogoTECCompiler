package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Retrocede implements ASTNode {
	private ASTNode opera;

	public Retrocede(ASTNode opera) {
		super();
		this.opera = opera;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float f1 = Float.parseFloat(String.valueOf(opera.execute(symbolTable, turtle)));
		turtle.backward(f1);
		return null;
	}

}
