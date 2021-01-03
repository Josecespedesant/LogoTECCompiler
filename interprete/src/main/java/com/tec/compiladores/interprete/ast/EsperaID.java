package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class EsperaID implements ASTNode {
	private String string;

	public EsperaID(String string) {
		super();
		this.string = string;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float f1 = (float) symbolTable.get(string) / 60;
		try {
			Thread.sleep((long) f1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
