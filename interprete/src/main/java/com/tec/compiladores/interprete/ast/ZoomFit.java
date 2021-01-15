package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public class ZoomFit implements ASTNode {

	public ZoomFit() {
		super();
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		turtle.zoomFit();
		return null;
	}

}
