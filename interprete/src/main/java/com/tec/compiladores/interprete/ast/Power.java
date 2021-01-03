package com.tec.compiladores.interprete.ast;

import java.util.Map;

public class Power implements ASTNode {
	private ASTNode base, power;
	
	public Power(ASTNode base, ASTNode power) {
		super();
		this.base = base;
		this.power = power;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		float f1 = Float.parseFloat(String.valueOf(base.execute(symbolTable, turtle)));
		float f2 = Float.parseFloat(String.valueOf(power.execute(symbolTable, turtle)));
		return (float) Math.pow(f1, f2);
	}

}
