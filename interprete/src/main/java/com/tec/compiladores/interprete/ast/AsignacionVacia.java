package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class AsignacionVacia implements ASTNode {
	private String name;
	
	
	public AsignacionVacia(String name) {
		super();
		this.name = name;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		symbolTable.put(name, new Object());
		return null;
	}

}
