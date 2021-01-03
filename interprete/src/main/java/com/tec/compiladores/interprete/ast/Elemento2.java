package com.tec.compiladores.interprete.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elemento2 implements ASTNode {
	private int index;
	private ASTNode node;
	
	public Elemento2(ASTNode node, int index) {
		super();
		this.index = index;
		this.node = node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		return ((List<ASTNode>) node.execute(symbolTable, null)).get(index).execute(symbolTable, turtle);
	}

}
