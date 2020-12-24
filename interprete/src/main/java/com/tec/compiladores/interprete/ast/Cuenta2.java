package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

public class Cuenta2 implements ASTNode {
	private ASTNode node;
	
	public Cuenta2(ASTNode node) {
		super();
		this.node = node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> symbolTable) {
		return ((List<ASTNode>) node.execute(symbolTable)).size();
	}

}
