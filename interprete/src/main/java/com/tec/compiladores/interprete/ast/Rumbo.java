package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Rumbo implements ASTNode {

	public Rumbo() {
		super();
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		return turtle.getDirection();
	}

}
