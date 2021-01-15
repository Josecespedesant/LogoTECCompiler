package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Azar implements ASTNode {
	private int randNum;
	private ASTNode max;	

	public Azar(ASTNode max) {
		super();
		this.randNum = 0;
		this.max = max;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		randNum =  0 + (int)(Math.random() * (((int)max.execute(symbolTable, turtle, consola) - 0) + 1));
		return randNum;
	}

}
