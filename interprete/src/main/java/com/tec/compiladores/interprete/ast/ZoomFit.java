package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class ZoomFit implements ASTNode {

	public ZoomFit() {
		super();
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		turtle.zoomFit();
		return null;
	}

}
