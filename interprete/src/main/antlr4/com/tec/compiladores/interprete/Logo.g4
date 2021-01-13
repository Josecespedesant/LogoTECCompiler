grammar Logo;
//GRAMATICAS LIBRES DE CONTEXTO
@parser::header{
	import java.util.Map;
	import java.util.HashMap; 
	import java.util.Random;
	import java.util.ArrayList;
	import com.tec.compiladores.interprete.ast.*;
}
@parser::members{
	Map<String, Object> symbolTable = new HashMap<String, Object>();
	List<ASTNode> body = new ArrayList<ASTNode>();
	public Turtle turtle;
}

/* Gramatica de un programa.
* Tiene que tener un comentario en la primera linea.
* Un programa puede tener procedimientos, sentencias, llamadas o comentarios. 
* Entre cada una de ellas puede haber N cantidad de espacios*/
program: 
	comentario
	NEWLINE*
	(procedimiento {body.add($procedimiento.node);} | sentence {body.add($sentence.node);}| comentario)*
	NEWLINE*
	{for (ASTNode n : body){n.execute(symbolTable, turtle);}}
	;

//Gramática de un comentario, comienza con dos barras inclinadas y termina en endline
comentario:  NEWLINE* '//' ~( '\r' | '\n' )* NEWLINE*;

mostrable returns [ASTNode node]:
	sentence {$node = $sentence.node;}
	|opera {$node = $opera.node;} 
	|comparar {$node = $comparar.node;}
	|ID {$node = new VarRef($ID.text);}
;

listable returns [ASTNode node]:
	opera {$node = $opera.node;}
	|lista {$node = $lista.node;}
	;
		
/* Gramática de una sentencia
 * Puede ser cualquiera de las siguientes sentencias, y puede terminar con un endline
 */
sentence returns [ASTNode node]: NEWLINE* ( s1 = inicializacion {$node = $s1.node;} | s2 = asignacion {$node = $s2.node;} | s3 = muestra {$node = $s3.node;} |
	s4 = incremento {$node = $s4.node;}| avanza {$node = $avanza.node;}| retrocede {$node = $retrocede.node;}| girder {$node = $girder.node;} | girizq {$node = $girizq.node;}| 
	ponpos {$node = $ponpos.node;} | ponrumbo {$node = $ponrumbo.node;} | ponx {$node = $ponx.node;} | pony {$node = $pony.node;} | pnclrlapiz {$node = $pnclrlapiz.node;} | espera {$node = $espera.node;}| s15 = ejecuta {$node = $s15.node;} | s16 = repite {$node = $s16.node;} 
	| si {$node = $si.node;}| sisino {$node = $sisino.node;}| hazhasta {$node = $hazhasta.node;} | hasta {$node = $hasta.node;} |
	hazmientras {$node = $hazmientras.node;} | mientras {$node = $mientras.node;}|  elegir {$node = $elegir.node;} | cuenta {$node = $cuenta.node;} | ultimo {$node = $ultimo.node;} | 
	elemento {$node = $elemento.node;}| primero {$node = $primero.node;}| OCULTATORTUGA {$node = new OcultaTortuga();} | APARECETORTUGA {$node = new ApareceTortuga();} | llamada {$node = $llamada.node;} |
	RUMBO {$node = new Rumbo();}| ZOOMFIT {$node = new ZoomFit();} | GOMA {$node = new Goma();} | BAJALAPIZ {$node = new Baja();} | SUBELAPIZ {$node = new Sube();} | CENTRO {$node = new Centro();} | BORRAPANTALLA {$node = new BorraPantalla();}| repite {$node = $repite.node;}) NEWLINE* ;
	
 
asignacion returns [ASTNode node]: HAZ ID listable 
	{$node = new Asignacion($ID.text, $listable.node);};
inicializacion returns [ASTNode node]: INIC ID ASSIGN listable
	{$node = new Inicializacion($ID.text, $listable.node);};	
muestra returns [ASTNode node]: MUESTRA mostrable {$node = new Muestra($mostrable.node);};
incremento returns [ASTNode node]: INC ID {$node = new Incremento($ID.text);} | INC ID listable {$node = new Incremento2($ID.text, $listable.node);};

avanza returns [ASTNode node]: AVANZA opera {$node = new Avanza($opera.node);} | AVANZA ID {$node = new AvanzaID($ID.text);};
retrocede returns [ASTNode node]: RETROCEDE opera {$node = new Retrocede($opera.node);} | RETROCEDE ID {$node = new RetrocedeID($ID.text);};
girder returns [ASTNode node]: GIRADERECHA opera {$node = new GirDer($opera.node);} | GIRADERECHA ID {$node = new GirDerID($ID.text);};
girizq returns [ASTNode node]: GIRAIZQUIERDA opera {$node = new GirIzq($opera.node);} | GIRAIZQUIERDA ID {$node = new GirIzqID($ID.text);};
ponpos returns [ASTNode node]: PONPOS t1 = opera t2 = opera {$node = new PonPos($t1.node,$t2.node);}
								| PONPOS t3 = ID t4 = ID {$node = new PonPos1($t3.text, $t4.text);}
								| PONPOS t5 = opera t6 = ID {$node = new PonPos2($t5.node,$t6.text);}
								| PONPOS t7 = ID t8 = opera {$node = new PonPos3($t7.text,$t8.node);};
ponrumbo returns [ASTNode node]: PONRUMBO opera {$node = new PonRumbo($opera.node);} | PONRUMBO ID {$node = new PonRumboID($ID.text);};
ponx returns [ASTNode node]: PONX opera {$node = new PonX($opera.node);} | PONRUMBO ID {$node = new PonXID($ID.text);};
pony returns [ASTNode node]: PONY opera {$node = new PonY($opera.node);} | PONY ID {$node = new PonYID($ID.text);};
pnclrlapiz returns [ASTNode node]: PONCOLORLAPIZ COLOR {$node = new PonColorLapiz($COLOR.text);};
espera returns [ASTNode node]: ESPERA opera {$node = new Espera($opera.node);} | ESPERA ID {$node = new EsperaID($ID.text);};

/* Expresión regular de un procedimiento
 * Debe tener la palabra clave para, seguida de un identificador que debe comenzar con minuscula con un maximo de 10 caracteres
 * cuantos espacios quiera, puede o no tener parametros, puede tener sentencias o llamadas a otros procedimientos
 * cuantas lineas quiera pero al final debe tener la palabra clave fin
 */
procedimiento returns [ASTNode node]:
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	{List <String> paramList = new ArrayList<String>();}
	{Map<String, Object> symbolTableLocal = symbolTable;}
	
	(PARA procName = ID (PAR_OPEN param = ID {paramList.add($param.text);}  
										(COMMA paramn = ID {paramList.add($paramn.text);})* PAR_CLOSE)?
	NEWLINE*
	(t1 = sentence {body.add($t1.node);} | comentario | llamada {body.add($llamada.node);})*
	NEWLINE*
	FIN) NEWLINE* 
	{$node = new Procedimiento($procName.text, body, paramList, symbolTableLocal);}
	;
	
llamada returns [ASTNode node]:
	{List <ASTNode> paramList = new ArrayList<ASTNode>();}
	procName=ID PAR_OPEN (param = listable {paramList.add($param.node);} (COMMA paramn = listable {paramList.add($paramn.node);})*)? PAR_CLOSE
	{$node = new Llamada($procName.text, paramList);}
	;

ejecuta returns [ASTNode node]: EJECUTA
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	PAR_SQ_OPEN t1=sentence {body.add($t1.node);} (COMMA t2=sentence {body.add($t2.node);})* PAR_SQ_CLOSE
	{$node = new Execute(body);};

repite returns [ASTNode node]: REPITE INTEGER 
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	PAR_SQ_OPEN t1=sentence {body.add($t1.node);} (COMMA t2=sentence {body.add($t2.node);})* PAR_SQ_CLOSE
	{$node = new Repite(Integer.parseInt($INTEGER.text), body);};
si returns [ASTNode node]: 
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	SI PAR_OPEN comparar PAR_CLOSE 
	PAR_SQ_OPEN t1=sentence {body.add($t1.node);} (COMMA t2=sentence {body.add($t2.node);})* PAR_SQ_CLOSE
	{$node = new Si($comparar.node, body);};
sisino returns [ASTNode node]:
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	{List <ASTNode> elsebody = new ArrayList<ASTNode>();}	 
	SISINO PAR_OPEN comparar PAR_CLOSE 
	PAR_SQ_OPEN t1=sentence {body.add($t1.node);} (COMMA t2=sentence {body.add($t2.node);})* PAR_SQ_CLOSE
	PAR_SQ_OPEN t1=sentence {elsebody.add($t1.node);} (COMMA t2=sentence {elsebody.add($t2.node);})* PAR_SQ_CLOSE
	{$node = new SiSiNo($comparar.node, body, elsebody);};
hazhasta returns [ASTNode node]: 
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	HAZHASTA 
	PAR_SQ_OPEN t1=sentence {body.add($t1.node);} (COMMA t2=sentence {body.add($t2.node);})* PAR_SQ_CLOSE
	PAR_OPEN comparar PAR_CLOSE
	{$node = new HazHasta($comparar.node, body);};
hasta returns [ASTNode node]: 
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	HASTA PAR_OPEN comparar PAR_CLOSE 
	PAR_SQ_OPEN t1=sentence {body.add($t1.node);} (COMMA t2=sentence {body.add($t2.node);})* PAR_SQ_CLOSE
	{$node = new Hasta($comparar.node, body);};
hazmientras returns [ASTNode node]: 
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	HAZMIENTRAS 
	PAR_SQ_OPEN t1=sentence {body.add($t1.node);} (COMMA t2=sentence {body.add($t2.node);})* PAR_SQ_CLOSE
	PAR_OPEN comparar PAR_CLOSE
	{$node = new HazMientras($comparar.node, body);};
mientras returns [ASTNode node]: 
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	MIENTRAS PAR_OPEN comparar PAR_CLOSE 
	PAR_SQ_OPEN t1=sentence {body.add($t1.node);} (COMMA t2=sentence {body.add($t2.node);})* PAR_SQ_CLOSE
	{$node = new Mientras($comparar.node, body);};
	
/* Gramática de una comparación
 * Compara dos operaciones, identificadores, u expresion en general mediante un operador de comparación (VAN EN CONDICIONALES, RETORNAN UN VALOR BOOLEANO)
 */
comparar returns [ASTNode node]:
	t1 = opera GT t2 = opera {$node = new MayorQue($t1.node,$t2.node);}
	|t3 = ID GT t4 = opera {$node = new MayorQue(new VarRef($t3.text),$t4.node);}
	|t5 = opera GT t6 = ID {$node = new MayorQue($t5.node,new VarRef($t6.text));}
	|t7 = ID GT t8 = ID {$node = new MayorQue(new VarRef($t7.text),new VarRef($t8.text));}
	
	|t9 = opera LT t10 = opera {$node = new MenorQue($t9.node,$t10.node);}
	|t11 = ID LT t12 = opera {$node = new MenorQue(new VarRef($t11.text),$t12.node);}
	|t13 = opera LT t14 = ID {$node = new MenorQue($t13.node,new VarRef($t14.text));}
	|t15 = ID LT t16 = ID {$node = new MenorQue(new VarRef($t15.text),new VarRef($t16.text));}
	
	|t17 = opera GEQ t18 = opera {$node = new MayorIgualQue($t17.node,$t18.node);}
	|t19 = ID GEQ t20 = opera {$node = new MayorIgualQue(new VarRef($t19.text),$t20.node);}
	|t21 = opera GEQ t22 = ID {$node = new MayorIgualQue($t21.node,new VarRef($t22.text));}
	|t23 = ID GEQ t24 = ID {$node = new MayorIgualQue(new VarRef($t23.text),new VarRef($t24.text));}

	|t25 = opera EQ t26 = opera {$node = new Iguales($t25.node,$t26.node);}
	|t27 = ID EQ t28 = opera {$node = new Iguales(new VarRef($t27.text),$t28.node);}
	|t29 = opera EQ t30 = ID {$node = new Iguales($t29.node,new VarRef($t30.text));}
	|t31 = ID EQ t32 = ID {$node = new Iguales(new VarRef($t31.text),new VarRef($t32.text));}
	
	|t33 = opera LEQ t34 = opera {$node = new MenorIgualQue($t33.node,$t34.node);}
	|t35 = ID LEQ t36 = opera {$node = new MenorIgualQue(new VarRef($t35.text),$t36.node);}
	|t37 = opera LEQ t38 = ID {$node = new MenorIgualQue($t37.node,new VarRef($t38.text));}
	|t39 = ID LEQ t40 = ID {$node = new MenorIgualQue(new VarRef($t39.text),new VarRef($t40.text));}
	
	|t41 = opera NEQ t42 = opera {$node = new NoIguales($t41.node,$t42.node);}
	|t43 = ID NEQ t44 = opera {$node = new NoIguales(new VarRef($t43.text),$t44.node);}
	|t45 = opera NEQ t46 = ID {$node = new NoIguales($t45.node,new VarRef($t46.text));}
	|t47 = ID NEQ t48 = ID {$node = new NoIguales(new VarRef($t47.text),new VarRef($t48.text));}
	
	|iguales {$node = $iguales.node;}
	|y {$node = $y.node;} 
	|o {$node = $o.node;}
	|mayorque {$node = $mayorque.node;} 
	|menorque {$node = $menorque.node;}
	|BOOLEAN {$node = new Constant(Boolean.parseBoolean($BOOLEAN.text));}|	
	; 
//Operadores de comparación (PUEDEN IR EN CONDICIONALES, RETORNAN UN VALOR BOOLEANO)
iguales returns [ASTNode node]: 
	IGUALES t1 = expression t2 = expression {$node = new Iguales($t1.node, $t2.node);}
	|IGUALES t3 = ID t4 = expression {$node = new Iguales(new VarRef($t3.text), $t4.node);}
	|IGUALES t5 = expression t6 = ID {$node = new Iguales($t5.node, new VarRef($t6.text));}
	|IGUALES t7 = ID t8 = ID {$node = new Iguales(new VarRef($t7.text), new VarRef($t8.text));}
	;
	
y returns [ASTNode node]: Y PAR_OPEN t1 = comparar PAR_CLOSE PAR_OPEN t2 = comparar  PAR_CLOSE 
		{$node = new Y($t1.node, $t2.node);};
o returns [ASTNode node]: O PAR_OPEN t1 = comparar PAR_CLOSE PAR_OPEN t2 = comparar PAR_CLOSE
		{$node = new O($t1.node, $t2.node);};
		
		
mayorque returns [ASTNode node]: MAYORQUE t1= opera t2=opera  {$node = new MayorQue($t1.node, $t2.node);}
                        		|MAYORQUE t3= ID t4=opera  {$node = new MayorQue(new VarRef($t3.text), $t4.node);}
                        		|MAYORQUE t5= opera t6=ID  {$node = new MayorQue($t5.node, new VarRef($t6.text));}
                        		|MAYORQUE t7= ID t8=ID  {$node = new MayorQue(new VarRef($t7.text), new VarRef($t8.text));};


menorque returns [ASTNode node]: MENORQUE t1= opera t2=opera  {$node = new MenorQue($t1.node, $t2.node);}
                        		|MENORQUE t3= ID t4=opera  {$node = new MenorQue(new VarRef($t3.text), $t4.node);}
                        		|MENORQUE t5= opera t6=ID  {$node = new MenorQue($t5.node, new VarRef($t6.text));}
                        		|MENORQUE t7= ID t8=ID  {$node = new MenorQue(new VarRef($t7.text), new VarRef($t8.text));};
	
// OPERACIONES NUMERICAS, DEVUELVEN UN NUEMRO
opera returns [ASTNode node]
	:
	t1 = expression {$node = $t1.node;}
	|t2 = diferencia {$node = $t2.node;}
	|t3 = azar {$node = $t3.node;}
	|t4 = menos {$node = $t4.node;}
	|t5 = producto {$node = $t5.node;}
	|t6 = potencia {$node = $t6.node;}
	|t7 = division {$node = $t7.node;}
	|t8 = resto {$node = $t8.node;}
	|t9 = suma {$node = $t9.node;}
	|t15 = redondea {$node = $t15.node;} 
	|t16 = seno {$node = $t16.node;}
	|t17 = coseno {$node = $t17.node;}
	;	
seno returns [ASTNode node]:
	SENO ID {$node = new Seno(new VarRef($ID.text));}| SENO opera {$node = new Seno($opera.node);};	
	
coseno returns [ASTNode node]:
	COSENO ID {$node = new Coseno(new VarRef($ID.text));}| COSENO opera {$node = new Coseno($opera.node);};	


expression returns [ASTNode node]:
	t1 = factor {$node = $t1.node;}
	(PLUS t2 = factor{$node = new Addition($node, $t2.node);}
		|MINUS t2 = factor{$node = new Substraction($node, $t2.node);}
	)*;
factor returns [ASTNode node]:
	t1 = term {$node = $t1.node;}
	(MULT t2 = term {$node = new Multiplication($node, $t2.node);}
		|DIV t2 = term {$node = new Division($node, $t2.node);}
	)*;
term returns [ASTNode node]:
	ID {$node = new VarRef($ID.text);}|
	INTEGER {$node = new Constant(Float.parseFloat($INTEGER.text));}|
	FLOAT {$node = new Constant(Float.parseFloat($FLOAT.text));}|
	PAR_OPEN opera {$node = $opera.node;} PAR_CLOSE;

//OPERACIONES ALGEBRAICAS, DEVUELVEN UN NUMERO

//Suma entre uno o más números
suma returns [ASTNode node]: SUMA PAR_OPEN t1=opera COMMA t2=opera {$node = new Addition($t1.node, $t2.node);} 
	(COMMA t3=opera {$node = new Addition($node, $t3.node);} )* PAR_CLOSE;
//Devuelve diferencia entre los n números (minimo 2)
diferencia returns [ASTNode node]: DIFERENCIA PAR_OPEN t1=opera COMMA t2=opera {$node = new Substraction($t1.node, $t2.node);} 
	(COMMA t3=opera {$node = new Substraction($node, $t3.node);} )* PAR_CLOSE;
//Devuelve el producto de los n números (minimo 2)
producto returns [ASTNode node]: PRODUCTO PAR_OPEN t1=opera COMMA t2=opera {$node = new Multiplication($t1.node, $t2.node);} 
	(COMMA t3=opera {$node = new Multiplication($node, $t3.node);} )* PAR_CLOSE;
//Genera un numero random entre 0 y el valor parámetro
azar returns [ASTNode node]: AZAR PAR_OPEN t1 = opera {$node = new Azar($t1.node);} PAR_CLOSE;
//Cambia el signo de un resultado
menos returns [ASTNode node]: MENOS PAR_OPEN t1 = opera {$node = new Minus($t1.node);} PAR_CLOSE;
//Eleva el primer parametro al segundo
potencia returns [ASTNode node]: POTENCIA PAR_OPEN base= opera COMMA power = opera {$node = new Power($base.node, $power.node);} PAR_CLOSE;
//Divide el primer numero entre el segundo div entera
division returns [ASTNode node]: DIVISION PAR_OPEN num = opera COMMA den = opera {$node = new Division($num.node, $den.node);} PAR_CLOSE;
//Residuo entre el primer número y el segundo
resto returns [ASTNode node]: RESTO PAR_OPEN t1 = opera COMMA t2 = opera {$node = new Residue($t1.node, $t2.node);} PAR_CLOSE;
//Redonde al número más cercano
redondea returns [ASTNode node]: REDONDEA t1 = opera {$node = new Round($t1.node);};

/* Expresión regular de una lista
 * Puede tener identificadores o numeros, tiene que estar separados por espacio coma espacio
 */
lista returns [ASTNode node]: 
	{List<ASTNode> list = new ArrayList<ASTNode>();}
	PAR_SQ_OPEN t1 = opera {list.add($t1.node);} (COMMA t2 = opera {list.add($t2.node);})* PAR_SQ_CLOSE 
	{$node = new ListaLogo(list);}
	|
	{List<ASTNode> list = new ArrayList<ASTNode>();} 
	PAR_SQ_OPEN PAR_SQ_CLOSE {$node = new ListaLogo(list);};
	
//Expresiones regulares de operaciones con listas
//Elige un elemento random de la lista
elegir returns [ASTNode node]: ELEGIR ID {$node = new Elegir($ID.text);} | ELEGIR lista {$node = new Elegir2($lista.node);};
//Cuenta el número de elementos de la lista
cuenta returns [ASTNode node]: CUENTA ID {$node = new Cuenta($ID.text);}| CUENTA lista {$node = new Cuenta2($lista.node);};
//Devuelve el ultimo elemento de la lista
ultimo returns [ASTNode node]: ULTIMO ID {$node = new Ultimo($ID.text);} | ULTIMO lista {$node = new Ultimo2($lista.node);};
//Devuelve el elemento n de la lista
elemento returns [ASTNode node]: ELEMENTO INTEGER ID {$node = new Elemento($ID.text, Integer.parseInt($INTEGER.text));} | ELEMENTO INTEGER lista {$node = new Elemento2($lista.node, Integer.parseInt($INTEGER.text));};
//Devuelve el primer elemento de la lista
primero returns[ASTNode node]: PRIMERO ID {$node = new Primero($ID.text);} | PRIMERO lista {$node = new Primero2($lista.node);};

//LISTA DE TOKENS	*TERMINALES*
PARA: 'para';
FIN: 'fin';
BOOLEAN: 'true' | 'false';
COMMA: ',';
HAZ: 'haz';
INIC: 'inic';
INC: 'inc';
AVANZA: 'AV'|'avanza';
RETROCEDE: 'RE'|'retrocede';
GIRADERECHA: 'GD'|'giraderecha';
GIRAIZQUIERDA: 'GI' | 'giraizquierda';
OCULTATORTUGA: 'OT' | 'ocultatortuga';
APARECETORTUGA: 'AT' 'aparecetortuga';
PONPOS: 'ponPos' | 'ponXY';
PONRUMBO: 'ponRumbo';
RUMBO: 'rumbo';
MUESTRA: 'muestra';
PONX: 'ponX';
PONY: 'ponY';
GOMA: 'goma';
BAJALAPIZ: 'BL';
SUBELAPIZ: 'SL';
PONCOLORLAPIZ: 'PCL';
CENTRO: 'centro';
ESPERA: 'espera';
EJECUTA: 'ejecuta';
REPITE: 'repite';
SI: 'si';
SISINO: 'sisino';
HAZHASTA: 'haz.hasta';
HASTA: 'hasta';
HAZMIENTRAS: 'haz.mientras';
MIENTRAS: 'mientras';
IGUALES: 'iguales?';
ZOOMFIT: 'zoomfit';
Y: 'y';
O: 'o';
MAYORQUE: 'mayorQue?';
MENORQUE: 'menorQue?';
REDONDEA: 'redondea';
DIFERENCIA: 'diferencia';
AZAR: 'azar';
MENOS: 'menos';
PRODUCTO: 'producto';
POTENCIA: 'potencia';
DIVISION: 'division';
RESTO: 'resto';
SUMA: 'suma';
ELEGIR: 'elegir';
CUENTA: 'cuenta';
ULTIMO: 'ultimo';
ELEMENTO: 'elemento';
PRIMERO: 'primero';
BORRAPANTALLA: 'borraPantalla';
COLOR: 'white' | 'blue' | 'brown' | 'cyan' | 'grey' | 'yellow' | 'black' | 'red' | 'green'
		;
SENO: 'sen';
COSENO: 'cos';
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
POINT: '.';

GT: '>';
LT: '<';
GEQ: '>=';
LEQ: '<=';
EQ: '==';
NEQ: '!=';

ASSIGN: '=';

ID: [a-z][a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?;


PAR_OPEN: '(';
PAR_CLOSE: ')';
PAR_SQ_OPEN: '[';
PAR_SQ_CLOSE: ']';


INTEGER: MINUS?[0-9]+;
FLOAT: MINUS? POINT [0-9]+ | MINUS? [0-9]+ POINT [0-9]*;
NEWLINE: '\n';

WS: [ \t\r]+ -> skip;