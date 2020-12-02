package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Minus implements ASTNode {
	ASTNode operand;
	
	
	
	public Minus(ASTNode operand) {
		super();
		this.operand = operand;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		return (float) operand.execute(symbolTable) * -1;
	}

}
