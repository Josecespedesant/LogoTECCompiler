package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Addition implements ASTNode {
	private ASTNode operand1, operand2;

	public Addition(ASTNode operand1, ASTNode operand2) {
		super();
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float f1 = Float.parseFloat(String.valueOf(operand1.execute(symbolTable, turtle, consola)));
		float f2 = Float.parseFloat(String.valueOf(operand2.execute(symbolTable, turtle, consola)));
		return f1+f2;
	}

}
