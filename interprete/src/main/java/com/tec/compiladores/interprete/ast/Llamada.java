package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

public class Llamada implements ASTNode {
	private String id;
	private List<ASTNode> paramscall;
	
	public Llamada(String id, List<ASTNode> paramscall) {
		super();
		this.id = id;
		this.paramscall = paramscall;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		List <ASTNode> body = (List<ASTNode>) ((List<ASTNode>) symbolTable.get(id)).get(0);
		List <String> parameters = (List<String>) ((List<ASTNode>) symbolTable.get(id)).get(1);
		
		for(int i = 0; i<parameters.size();i++) {
			symbolTable.put(parameters.get(i), paramscall.get(i).execute(symbolTable));
		}
		
		for(ASTNode n : body) {
			n.execute(symbolTable);
		}
		return null;
	}

}
