package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Goma implements ASTNode {

	public Goma() {
		super();
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		turtle.penColor("white");
		return null;
	}

}
