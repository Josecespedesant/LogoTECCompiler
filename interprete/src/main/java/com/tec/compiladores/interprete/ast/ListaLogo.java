package com.tec.compiladores.interprete.ast;

import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

public class ListaLogo implements ASTNode {
	private List<ASTNode> lista;

	public ListaLogo(List<ASTNode> lista) {
		super();
		this.lista = lista;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable, Turtle turtle, JTextArea consola) {
		for(ASTNode n : lista) {
			n.execute(symbolTable, turtle, consola);
		}
		return lista;
	}

}
