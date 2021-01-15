package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class PonPos1 implements ASTNode {
	private String x, y;

	public PonPos1(String x, String y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float x1 = (float) symbolTable.get(x);
		float y1 = (float) symbolTable.get(y);
		turtle.setPosition(x1, y1);
		return null;
	}

}
