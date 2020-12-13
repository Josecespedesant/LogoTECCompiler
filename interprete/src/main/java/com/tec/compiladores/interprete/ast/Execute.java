package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

public class Execute implements ASTNode {
	private List<ASTNode> body;
	
	public Execute(List<ASTNode> body) {
		super();
		this.body = body;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		for(ASTNode n : body) {
			n.execute(symbolTable);
		}
		return null;
	}

}
