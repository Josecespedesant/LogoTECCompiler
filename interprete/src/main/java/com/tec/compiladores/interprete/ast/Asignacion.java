package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Asignacion implements ASTNode {
	private String name;
	private ASTNode expression;
	
	
	public Asignacion(String name, ASTNode expression) {
		super();
		this.name = name;
		this.expression = expression;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		symbolTable.put(name, expression.execute(symbolTable));
		return null;
	}

}
