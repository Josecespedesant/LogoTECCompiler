package com.tec.compiladores.interprete.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elegir2 implements ASTNode {
	private int randNum;
	private ASTNode node;
	
	public Elegir2(ASTNode node) {
		super();
		this.randNum = 0;
		this.node = node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		int max = ((List<ASTNode>) node.execute(symbolTable, turtle)).size() - 1;
		randNum =  0 + (int)(Math.random() * ((max - 0) + 1));
		return ((List<ASTNode>) node.execute(symbolTable, turtle)).get(randNum).execute(symbolTable, turtle);
	}

}
