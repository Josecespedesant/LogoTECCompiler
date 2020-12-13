package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

public class Procedimiento implements ASTNode {
	private String name;
	private List<ASTNode> body;
	
	
	public Procedimiento(String name, List<ASTNode> body) {
		super();
		this.name = name;
		this.body = body;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable) {
		symbolTable.put(name, body);
		return null;
	}

}
