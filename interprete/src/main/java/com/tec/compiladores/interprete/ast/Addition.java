package com.tec.compiladores.interprete.ast;

public class Addition implements ASTNode {
	private ASTNode operand1, operand2;

	public Addition(ASTNode operand1, ASTNode operand2) {
		super();
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	
	@Override
	public Object execute() {
		return (int) operand1.execute() + (int) operand2.execute();
	}

}
