package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class GirDerID implements ASTNode {
	private String id;

	public GirDerID(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float f1 = (float) symbolTable.get(id);
		turtle.right(f1);
		return null;
	}

}
