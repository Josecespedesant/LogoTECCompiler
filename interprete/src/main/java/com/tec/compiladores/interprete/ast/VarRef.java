package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class VarRef implements ASTNode {

	private String name;
	
	public VarRef(String name) {
		super();
		this.name = name;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		if(symbolTable.get(name)!=null) {
			return symbolTable.get(name);
		}
		else {
			consola.setText("Variable not found");
			return null;
		}
		
	}

}
