package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

public class Primero2 implements ASTNode {
	private ASTNode node;
	
	public Primero2(ASTNode node) {
		super();
		this.node = node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		return ((List<ASTNode>) node.execute(symbolTable, turtle, consola)).get(0).execute(symbolTable, turtle, consola);
	}

}
