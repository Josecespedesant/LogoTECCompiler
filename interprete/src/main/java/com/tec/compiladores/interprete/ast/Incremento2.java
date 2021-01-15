package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Incremento2 implements ASTNode {
	private String name;
	private ASTNode operand;
	
	public Incremento2(String name, ASTNode operand) {
		super();	
		this.name = name;
		this.operand = operand;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float inc = (float) ((float) symbolTable.get(name) + (float) Float.parseFloat(String.valueOf(operand.execute(symbolTable, turtle, consola))));
		symbolTable.put(name, inc);
		return null;
	}

}
