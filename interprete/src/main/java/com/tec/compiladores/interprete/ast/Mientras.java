package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

public class Mientras implements ASTNode {
	private ASTNode condition;
	private List<ASTNode> body;
	
	public Mientras(ASTNode condition, List<ASTNode> body) {
		super();
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		while((boolean)condition.execute(symbolTable, turtle, consola)) {
			for(ASTNode n : body) {
				n.execute(symbolTable, turtle, consola);
			}
		}
		return null;
	}

}
