package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

public class Repite implements ASTNode {
	private int times;
	private List<ASTNode> body;
	
	public Repite(int times , List<ASTNode> body) {
		super();
		this.times = times;
		this.body = body;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		for(int i=0; i<times; i++) {
			for(ASTNode n : body) {
				n.execute(symbolTable, turtle, consola);
			}
		}
		return null;
	}

}
