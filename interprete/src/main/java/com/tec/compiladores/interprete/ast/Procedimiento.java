package com.tec.compiladores.interprete.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Procedimiento implements ASTNode {
	private String name;
	private List<Object> aux;
	List<ASTNode> body;
	List<String> params;
	
	
	public Procedimiento(String name, List<ASTNode> body, List<String> params) {
		super();
		this.name = name;
		this.body = body;
		this.params = params;
		this.aux = new ArrayList<Object>();
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		for(String s : params) {
			symbolTable.put(s, new Object());
		}
		this.aux.add(body);
		this.aux.add(params);
		symbolTable.put(name, aux);
		return null;
	}

}
