package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class PonYID implements ASTNode {
	private String id;

	public PonYID(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float f1 = (float) symbolTable.get(id);
		turtle.setPosition(turtle.getX(), f1);
		return null;
	}

}
