package com.tec.compiladores.interprete.ast;

public class Muestra implements ASTNode {
	private ASTNode data;

	public Muestra(ASTNode data) {
		super();
		this.data = data;
	}

	@Override
	public Object execute() {
		System.out.println(data.execute());
		return null;
	}

}
