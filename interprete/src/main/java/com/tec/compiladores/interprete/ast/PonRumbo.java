package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class PonRumbo implements ASTNode {
	private ASTNode opera;

	public PonRumbo(ASTNode opera) {
		super();
		this.opera = opera;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float f1 = Float.parseFloat(String.valueOf(opera.execute(symbolTable, turtle, consola)));
		turtle.setDirection(f1);
		return null;
	}

}
