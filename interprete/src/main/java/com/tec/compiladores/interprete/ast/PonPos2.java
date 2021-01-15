package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class PonPos2 implements ASTNode {
	private ASTNode x;
	private String y;

	public PonPos2(ASTNode x, String y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float x1 = Float.parseFloat(String.valueOf(x.execute(symbolTable, turtle, consola)));
		float y1 = (float) symbolTable.get(y);
		turtle.setPosition(x1, y1);
		return null;
	}

}
