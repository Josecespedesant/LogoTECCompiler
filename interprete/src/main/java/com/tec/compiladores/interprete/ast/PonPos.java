package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class PonPos implements ASTNode {
	private ASTNode x, y;

	public PonPos(ASTNode x, ASTNode y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float x1 = Float.parseFloat(String.valueOf(x.execute(symbolTable, turtle)));
		float y1 = Float.parseFloat(String.valueOf(y.execute(symbolTable, turtle)));
		turtle.setPosition(x1, y1);
		return null;
	}

}
