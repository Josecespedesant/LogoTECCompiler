package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class ApareceTortuga implements ASTNode {

	public ApareceTortuga() {
		super();
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		turtle.show();
		return null;
	}

}
