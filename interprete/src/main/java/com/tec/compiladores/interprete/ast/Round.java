package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Round implements ASTNode {
	ASTNode num;
	public Round(ASTNode num) {
		super();
		this.num = num;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		return (float) Math.round((float)num.execute(symbolTable, turtle, consola));
	}

}
