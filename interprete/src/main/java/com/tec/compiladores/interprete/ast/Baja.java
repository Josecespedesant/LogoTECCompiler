package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Baja implements ASTNode {

	public Baja() {
		super();
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		turtle.down();
		return null;
	}

}
