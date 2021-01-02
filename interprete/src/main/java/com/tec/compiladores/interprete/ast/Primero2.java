package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

public class Primero2 implements ASTNode {
	private ASTNode node;
	
	public Primero2(ASTNode node) {
		super();
		this.node = node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		return ((List<ASTNode>) node.execute(symbolTable, null)).get(0).execute(symbolTable, null);
	}

}
