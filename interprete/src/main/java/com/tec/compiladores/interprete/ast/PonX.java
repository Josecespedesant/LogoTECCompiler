package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class PonX implements ASTNode {
	private ASTNode opera;

	public PonX(ASTNode opera) {
		super();
		this.opera = opera;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float f1 = Float.parseFloat(String.valueOf(opera.execute(symbolTable, turtle, consola)));
		turtle.setPosition(f1, turtle.getY());
		return null;
	}

}
