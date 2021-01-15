package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

public class Execute implements ASTNode {
	private List<ASTNode> body;
	
	public Execute(List<ASTNode> body) {
		super();
		this.body = body;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		for(ASTNode n : body) {
			n.execute(symbolTable, turtle, consola);
		}
		return null;
	}

}
