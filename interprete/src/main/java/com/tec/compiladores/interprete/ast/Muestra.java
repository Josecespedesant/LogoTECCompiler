package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Muestra implements ASTNode {
	private ASTNode data;

	public Muestra(ASTNode data) {
		super();
		this.data = data;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		if(consola!=null) {
			consola.append("\n");
			consola.append(data.execute(symbolTable, turtle, consola).toString());
		}
		return null;
	}

}
