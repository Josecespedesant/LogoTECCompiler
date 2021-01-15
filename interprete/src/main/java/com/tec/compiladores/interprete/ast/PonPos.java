package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class PonPos implements ASTNode {
	private ASTNode x, y;

	public PonPos(ASTNode x, ASTNode y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float x1 = Float.parseFloat(String.valueOf(x.execute(symbolTable, turtle, consola)));
		float y1 = Float.parseFloat(String.valueOf(y.execute(symbolTable, turtle, consola)));
		turtle.setPosition(x1, y1);
		return null;
	}

}
