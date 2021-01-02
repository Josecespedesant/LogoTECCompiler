package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Sube implements ASTNode {

	public Sube() {
		super();
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		turtle.up();
		return null;
	}

}
