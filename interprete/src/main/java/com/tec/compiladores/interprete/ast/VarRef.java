package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class VarRef implements ASTNode {

	private String name;
	
	public VarRef(String name) {
		super();
		this.name = name;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		return symbolTable.get(name);
	}

}
