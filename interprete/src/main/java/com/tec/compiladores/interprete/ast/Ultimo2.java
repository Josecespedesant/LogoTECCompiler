package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

public class Ultimo2 implements ASTNode {
	private ASTNode node;
	
	public Ultimo2(ASTNode node) {
		super();
		this.node = node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		int max = ((List<ASTNode>) node.execute(symbolTable, turtle)).size() - 1;
		return ((List<ASTNode>) node.execute(symbolTable, turtle)).get(max).execute(symbolTable, turtle);
	}

}
