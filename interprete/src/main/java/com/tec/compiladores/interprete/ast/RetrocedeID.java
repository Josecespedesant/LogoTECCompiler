package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class RetrocedeID implements ASTNode {
	private String id;

	public RetrocedeID(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		if(symbolTable.get(id)!=null) {
			float f1 = (float) symbolTable.get(id);
			turtle.backward(f1);	
			
		}else {
			consola.setText("Variable not found");
		}
		return null;
	}

}
