package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Round implements ASTNode {
	ASTNode num;
	public Round(ASTNode num) {
		super();
		this.num = num;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		return (float) Math.round((float)num.execute(symbolTable));
	}

}
