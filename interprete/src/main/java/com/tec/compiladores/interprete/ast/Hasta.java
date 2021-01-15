package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

public class Hasta implements ASTNode {
	private ASTNode condition;
	private List<ASTNode> body;
	
	public Hasta(ASTNode condition, List<ASTNode> body) {
		super();
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		boolean flag = true;
		while(flag) {
			for(ASTNode n : body) {
				if((boolean)condition.execute(symbolTable, turtle, consola) == true) {
					flag = false;
				}
				n.execute(symbolTable, turtle, consola);
			}
		}
		return null;
	}

}
