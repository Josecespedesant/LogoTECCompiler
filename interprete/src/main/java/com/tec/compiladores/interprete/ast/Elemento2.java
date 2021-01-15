package com.tec.compiladores.interprete.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

public class Elemento2 implements ASTNode {
	private int index;
	private ASTNode node;
	
	public Elemento2(ASTNode node, int index) {
		super();
		this.index = index;
		this.node = node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		return ((List<ASTNode>) node.execute(symbolTable, turtle, consola)).get(index).execute(symbolTable, turtle, consola);
	}

}
