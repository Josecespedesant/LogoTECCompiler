package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class PonColorLapiz implements ASTNode {
	private String color;

	public PonColorLapiz(String color) {
		super();
		this.color = color;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		turtle.penColor(color);
		return null;
	}

}
