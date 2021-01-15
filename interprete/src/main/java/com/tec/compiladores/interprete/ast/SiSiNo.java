package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

public class SiSiNo implements ASTNode {
	private ASTNode condition;
	private List<ASTNode> body;
	private List<ASTNode> elsebody;
	
	public SiSiNo(ASTNode condition, List<ASTNode> body, List<ASTNode> elsebody) {
		super();
		this.condition = condition;
		this.body = body;
		this.elsebody = elsebody;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		if((boolean) condition.execute(symbolTable, turtle, consola)) {
			for(ASTNode n : body) {
				n.execute(symbolTable, turtle, consola);
			}
		}else {
			for(ASTNode n : elsebody) {
				n.execute(symbolTable, turtle, consola);
			}
		}
		return null;
	}

}
