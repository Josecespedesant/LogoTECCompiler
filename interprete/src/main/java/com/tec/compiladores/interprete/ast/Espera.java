package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Espera implements ASTNode {
	private ASTNode opera;

	public Espera(ASTNode opera) {
		super();
		this.opera = opera;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float f1 = Float.parseFloat(String.valueOf(opera.execute(symbolTable, turtle, consola))) / 60;
		try {
			Thread.sleep((long) f1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
