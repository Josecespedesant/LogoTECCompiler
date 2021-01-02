package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class OcultaTortuga implements ASTNode {

	public OcultaTortuga() {
		super();
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		turtle.hide();
		return null;
	}

}
