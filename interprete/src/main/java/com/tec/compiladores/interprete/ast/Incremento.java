package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Incremento implements ASTNode {
	private String name;
	
	public Incremento(String name) {
		super();	
		this.name = name;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		float inc = (float) ((float) symbolTable.get(name) + 1);
		symbolTable.put(name, inc);
		return null;
	}

}
