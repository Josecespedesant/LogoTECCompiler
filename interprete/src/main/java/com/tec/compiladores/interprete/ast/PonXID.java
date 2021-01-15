package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class PonXID implements ASTNode {
	private String id;

	public PonXID(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float f1 = (float) symbolTable.get(id);
		turtle.setPosition(f1, turtle.getY());
		return null;
	}

}
