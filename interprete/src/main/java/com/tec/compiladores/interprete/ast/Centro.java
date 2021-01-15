package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class Centro implements ASTNode {

	public Centro() {
		super();
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		turtle.up();
		turtle.setPosition(0, 0);
		turtle.down();
		return null;
	}

}
