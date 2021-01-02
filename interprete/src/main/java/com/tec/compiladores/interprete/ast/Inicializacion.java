package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Inicializacion implements ASTNode {
	private String name;
	private ASTNode expression;
	
	public Inicializacion(String name, ASTNode expression) {
		super();
		this.name = name;
		this.expression = expression;
	}
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		symbolTable.put(name, expression.execute(symbolTable, null));
		return null;
	}

}
