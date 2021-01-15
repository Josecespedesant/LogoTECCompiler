package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Nada implements ASTNode {
	
	
	public Nada() {
		super();
	}


	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		return null;
	}

}
