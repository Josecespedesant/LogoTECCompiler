package com.tec.compiladores.interprete.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Primero implements ASTNode {
	private String id;
	
	public Primero(String id) {
		super();
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle) {
		List<ASTNode> lista = (List<ASTNode>) symbolTable.get(id);
		List<Float> lista2 = new ArrayList<Float>();
		
		for(int i = 0; i<lista.size(); i++) {
			lista2.add((Float) lista.get(i).execute(symbolTable, null));
		}
		return lista2.get(0);
	}

}
