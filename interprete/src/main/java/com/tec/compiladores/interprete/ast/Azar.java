package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Azar implements ASTNode {
	private int randNum;
	private ASTNode max;	

	public Azar(ASTNode max) {
		super();
		this.randNum = 0;
		this.max = max;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		randNum =  0 + (int)(Math.random() * (((int)max.execute(symbolTable) - 0) + 1));
		return randNum;
	}

}
