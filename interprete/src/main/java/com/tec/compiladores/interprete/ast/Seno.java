package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Seno implements ASTNode {
	private ASTNode operand1;

	public Seno(ASTNode operand1) {
		super();
		this.operand1 = operand1;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float f1 = Float.parseFloat(String.valueOf(operand1.execute(symbolTable, turtle, consola)));
		double b = Math.toRadians(f1);
		return Math.sin(b);
	}

}
