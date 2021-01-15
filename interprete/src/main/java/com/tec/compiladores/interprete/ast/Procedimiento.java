package com.tec.compiladores.interprete.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

public class Procedimiento implements ASTNode {
	private String name;
	private List<Object> aux;
	List<ASTNode> body;
	List<String> params;
	Map<String, Object> symbolTableLocal;
	
	public Procedimiento(String name, List<ASTNode> body, List<String> params, Map<String, Object> symbolTableLocal) {
		super();
		this.name = name;
		this.body = body;
		this.params = params;
		this.symbolTableLocal = symbolTableLocal;
		this.aux = new ArrayList<Object>();
	}

	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		for(String s : params) {
			symbolTableLocal.put(s, new Object());
		}
		
		this.aux.add(body);
		this.aux.add(params);
		this.aux.add(symbolTableLocal);
		
		symbolTable.put(name, aux);
		return null;
	}

}
