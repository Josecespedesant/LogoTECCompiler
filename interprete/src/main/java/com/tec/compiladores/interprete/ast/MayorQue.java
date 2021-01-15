package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class MayorQue implements ASTNode {
	private ASTNode op1, op2;	

	public MayorQue(ASTNode op1, ASTNode op2) {
		super();
		this.op1 = op1;
		this.op2 = op2;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		return (float) op1.execute(symbolTable, turtle, consola) > (float) op2.execute(symbolTable, turtle, consola);
	}

}
