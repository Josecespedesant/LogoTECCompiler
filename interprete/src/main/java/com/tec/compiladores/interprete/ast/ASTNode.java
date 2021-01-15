package com.tec.compiladores.interprete.ast;

import java.util.Map;

import javax.swing.JTextArea;

public interface ASTNode {
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola);
}
