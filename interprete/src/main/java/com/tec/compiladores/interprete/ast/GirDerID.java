package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class GirDerID implements ASTNode {
	private String id;

	public GirDerID(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		float f1 = (float) symbolTable.get(id);
		turtle.right(f1);
		return null;
	}

}
