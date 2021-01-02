package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class BorraPantalla implements ASTNode {

	public BorraPantalla() {
		super();
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		turtle.clear();
		return null;
	}

}
