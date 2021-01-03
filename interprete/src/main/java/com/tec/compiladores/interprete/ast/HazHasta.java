package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

public class HazHasta implements ASTNode {
	private ASTNode condition;
	private List<ASTNode> body;
	
	public HazHasta(ASTNode condition, List<ASTNode> body) {
		super();
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		boolean flag = true;
		do {
			for(ASTNode n : body) {
				if((boolean)condition.execute(symbolTable, turtle) == true) {
					flag = false;
				}
				n.execute(symbolTable, turtle);
			}
		} while ( flag );
		
		return null;
	}

}
