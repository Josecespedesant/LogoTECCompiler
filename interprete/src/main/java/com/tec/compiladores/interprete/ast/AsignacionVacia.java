package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class AsignacionVacia implements ASTNode {
	private String name;
	
	
	public AsignacionVacia(String name) {
		super();
		this.name = name;
	}


	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		symbolTable.put(name, new Object());
		return null;
	}

}
