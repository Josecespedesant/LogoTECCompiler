package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Y implements ASTNode {
	private ASTNode op1, op2;	

	public Y(ASTNode op1, ASTNode op2) {
		super();
		this.op1 = op1;
		this.op2 = op2;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		return (Boolean) op1.execute(symbolTable) && (Boolean) op2.execute(symbolTable);
	}

}
