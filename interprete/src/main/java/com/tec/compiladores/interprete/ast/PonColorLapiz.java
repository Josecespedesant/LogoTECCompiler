package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class PonColorLapiz implements ASTNode {
	private String color;

	public PonColorLapiz(String color) {
		super();
		this.color = color;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		turtle.penColor(color);
		return null;
	}

}
