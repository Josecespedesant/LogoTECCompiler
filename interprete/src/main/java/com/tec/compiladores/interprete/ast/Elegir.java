package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

public class Elegir implements ASTNode {
	private int randNum;
	private List<ASTNode> lista;
	
	@SuppressWarnings("unchecked")
	public Elegir(ASTNode lista) {
		super();
		this.randNum = 0;
		this.lista = (List<ASTNode>) lista;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		int len = lista.size();
		randNum =  0 + (int)(Math.random() * (((int) - 0) + 1));
		return lista.get(randNum);
	}

}
