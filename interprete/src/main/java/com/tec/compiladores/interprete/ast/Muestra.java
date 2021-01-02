package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Muestra implements ASTNode {
	private ASTNode data;

	public Muestra(ASTNode data) {
		super();
		this.data = data;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		System.out.println(data.execute(symbolTable, null));
		return null;
	}

}
