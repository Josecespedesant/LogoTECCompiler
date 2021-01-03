package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Iguales implements ASTNode {
	private ASTNode operand1, operand2;

	public Iguales(ASTNode operand1, ASTNode operand2) {
		super();
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		Object t1 = operand1.execute(symbolTable, turtle);
		Object t2 = operand2.execute(symbolTable, turtle);
		return t1.equals(t2);
	}

}
