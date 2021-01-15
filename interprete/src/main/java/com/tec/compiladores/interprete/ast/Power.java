package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Power implements ASTNode {
	private ASTNode base, power;
	
	public Power(ASTNode base, ASTNode power) {
		super();
		this.base = base;
		this.power = power;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float f1 = Float.parseFloat(String.valueOf(base.execute(symbolTable, turtle, consola)));
		float f2 = Float.parseFloat(String.valueOf(power.execute(symbolTable, turtle, consola)));
		return (float) Math.pow(f1, f2);
	}

}
