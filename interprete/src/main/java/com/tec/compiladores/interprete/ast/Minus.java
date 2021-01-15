package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Minus implements ASTNode {
	ASTNode operand;
	
	
	
	public Minus(ASTNode operand) {
		super();
		this.operand = operand;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		return (float) operand.execute(symbolTable, turtle, consola) * -1;
	}

}
