package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

public class Llamada implements ASTNode {
	private String id;
	
	public Llamada(String id) {
		super();
		this.id = id;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		@SuppressWarnings("unchecked")
		List <ASTNode> body = (List<ASTNode>) symbolTable.get(id);
		for(ASTNode n : body) {
			n.execute(symbolTable);
		}
		return null;
	}

}
