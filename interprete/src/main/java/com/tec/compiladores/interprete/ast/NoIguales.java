package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class NoIguales implements ASTNode {
	private ASTNode operand1, operand2;

	public NoIguales(ASTNode operand1, ASTNode operand2) {
		super();
		this.operand1 = operand1;
		this.operand2 = operand2;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		Object t1 = operand1.execute(symbolTable, turtle, consola);
		Object t2 = operand2.execute(symbolTable, turtle, consola);
		return !t1.equals(t2);
	}

}
