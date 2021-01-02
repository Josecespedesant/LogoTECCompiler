package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Division implements ASTNode {
	private ASTNode operand1, operand2;

	public Division(ASTNode operand1, ASTNode operand2) {
		super();
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float f1 = Float.parseFloat(String.valueOf(operand1.execute(symbolTable, null)));
		float f2 = Float.parseFloat(String.valueOf(operand2.execute(symbolTable, null)));
		
		if (f2 == 0){
			System.out.println("Division by 0 undefined\n");
			return null;
		}
		return f1 / f2;
	}

}
