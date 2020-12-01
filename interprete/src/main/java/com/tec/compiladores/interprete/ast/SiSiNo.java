package com.tec.compiladores.interprete.ast;

import java.util.List;

public class SiSiNo implements ASTNode {
	private ASTNode condition;
	private List<ASTNode> body;
	
	public SiSiNo(ASTNode condition, List<ASTNode> body) {
		super();
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Object execute() {
		if((boolean) condition.execute()) {
			for(ASTNode n : body) {
				n.execute();
			}
		}
		return null;
	}

}
