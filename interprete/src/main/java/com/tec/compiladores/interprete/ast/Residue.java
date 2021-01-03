package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Residue implements ASTNode {
	private ASTNode operand1, operand2;

	public Residue(ASTNode operand1, ASTNode operand2) {
		super();
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float f1 = Float.parseFloat(String.valueOf(operand1.execute(symbolTable, turtle)));
		float f2 = Float.parseFloat(String.valueOf(operand2.execute(symbolTable, turtle)));
		return f1%f2;
	}

}
