package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

public class Si implements ASTNode {
	private ASTNode condition;
	private List<ASTNode> body;
	
	public Si(ASTNode condition, List<ASTNode> body) {
		super();
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		if((boolean) condition.execute(symbolTable)) {
			for(ASTNode n : body) {
				n.execute(symbolTable);
			}
		}
		return null;
	}

}
