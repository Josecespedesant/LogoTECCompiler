package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class RetrocedeID implements ASTNode {
	private String id;

	public RetrocedeID(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float f1 = (float) symbolTable.get(id);
		turtle.backward(f1);
		return null;
	}

}
