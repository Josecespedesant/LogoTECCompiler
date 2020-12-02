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
}

/* Gramatica de un programa.
* Tiene que tener un comentario en la primera linea.
* Un programa puede tener procedimientos, sentencias, llamadas o comentarios. 
* Entre cada una de ellas puede haber N cantidad de espacios*/
program: 
	{
		List<ASTNode> body = new ArrayList<ASTNode>();
		Map<String, Object> symbolTable = new HashMap<String, Object>();
	}
	comentario
	NEWLINE*
	(procedimiento | sentence {body.add($sentence.node);}| llamada | comentario)*
	NEWLINE*
	{
		for (ASTNode n : body){
			n.execute(symbolTable);
		}
	}
	;

//Gramática de un comentario, comienza con dos barras inclinadas y termina en endline
comentario:  NEWLINE* '//' ~( '\r' | '\n' )* NEWLINE*;

/* Gramática de una llamada.
* Tiene que tener un identificador del procedimiento seguido de los parámetros de dicho procedimiento -si tiene- 
* Dichos parámetros están representados por paréntesis cuadrados.
*/
llamada: ID paramscall?;
paramscall: PAR_SQ_OPEN ((ID|INTEGER|FLOAT) COMMA)*(ID|INTEGER|FLOAT) PAR_SQ_CLOSE
		| PAR_SQ_OPEN PAR_SQ_CLOSE;
		
/* Gramática de una sentencia
 * Puede ser cualquiera de las siguientes sentencias, y puede terminar con un endline
 */
sentence returns [ASTNode node]: NEWLINE* ( s1 = inicializacion {$node = $s1.node;} | s2 = asignacion {$node = $s2.node;} | s3 = muestra {$node = $s3.node;} |
	incremento | avanza | retrocede | girder | girizq| 
	ponpos | ponrumbo | ponx | pony | pnclrlapiz | espera | ejecuta | repite | si {$node = $si.node;}| sisino {$node = $sisino.node;}| hazhasta | hasta |
	hazmientras | mientras | elegir | cuenta | ultimo | elemento | primero | OCULTATORTUGA | APARECETORTUGA |
	RUMBO | GOMA | BAJALAPIZ | SUBELAPIZ | CENTRO | BORRAPANTALLA) NEWLINE* ;

//Distintas sentencias del lenguaje
asignacion returns [ASTNode node]: HAZ ID opera 
	{$node = new Asignacion($ID.text, $opera.node);}
	;

inicializacion returns [ASTNode node]: INIC ID ASSIGN opera
	{$node = new Inicializacion($ID.text, $opera.node);}
	;	
						
muestra returns [ASTNode node]: MUESTRA expression {$node = new Muestra($expression.node);}
;
 	
incremento: INC ID | INC ID opera;

avanza: AVANZA opera ;
retrocede: RETROCEDE opera;
girder: GIRADERECHA opera;
girizq: GIRAIZQUIERDA opera;
ponpos: PONPOS opera opera | PONXY opera opera;
ponrumbo: PONRUMBO opera;
ponx: PONX opera;
pony: PONY opera;
pnclrlapiz: PONCOLORLAPIZ COLOR;
espera: ESPERA opera;
ejecuta: EJECUTA orden;
repite: REPITE INTEGER orden;

si returns [ASTNode node]: 
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	SI PAR_OPEN expression PAR_CLOSE 
	PAR_SQ_OPEN (s1 = sentence {body.add($s1.node);} )* PAR_SQ_CLOSE 
	{$node = new Si($expression.node, body);}
	;
	
sisino returns [ASTNode node]:
	{List <ASTNode> body = new ArrayList<ASTNode>();}
	{List <ASTNode> elsebody = new ArrayList<ASTNode>();}	 
	SISINO PAR_OPEN expression PAR_CLOSE 
	PAR_SQ_OPEN (s1 = sentence {body.add($s1.node);})* PAR_SQ_CLOSE
	PAR_SQ_OPEN (s2 = sentence {elsebody.add($s2.node);})* PAR_SQ_CLOSE  
	{$node = new SiSiNo($expression.node, body, elsebody);}
	;

hazhasta: HAZHASTA orden PAR_OPEN expression PAR_CLOSE;
hasta: HASTA PAR_OPEN expression PAR_CLOSE orden;
hazmientras: HAZMIENTRAS orden PAR_OPEN expression PAR_CLOSE;
mientras: MIENTRAS PAR_OPEN expression PAR_CLOSE orden;

//Operadores de comparación
iguales returns [ASTNode node]: IGUALES t1 = opera t2 = opera ;
y returns [ASTNode node]: Y PAR_OPEN t1 = comparar PAR_CLOSE PAR_OPEN t2 = comparar  PAR_CLOSE ; //Falta
o returns [ASTNode node]: O PAR_OPEN comparar PAR_CLOSE PAR_OPEN comparar PAR_CLOSE; //Falta
mayorque returns [ASTNode node]: MAYORQUE t1= opera t2=opera  ;
menorque returns [ASTNode node]: MENORQUE t1 = opera t2= opera;
redondea returns [ASTNode node]: REDONDEA t1 = opera ;

/* Gramática de una orden
 * Una orden está compuesta por dos paréntesis cuadrados donde, dentro contiene ninguna o varias sentencias
 */
orden: PAR_SQ_OPEN sentence* PAR_SQ_CLOSE;

/* Gramática de una comparación
 * Compara dos operaciones, identificadores, u expresion en general mediante un operador de comparación
 */
comparar returns [ASTNode node]
	: comparar (GT | LT | GEQ | LEQ | EQ | NEQ) comparar
	| t1 = opera 
	;
// Un operador puede ser un identificador, un número positivo o negativo, etc.
opera returns [ASTNode node]
	:
	PAR_OPEN? (
	|t1 = expression {$node = $t1.node;}
	|t2 = diferencia {$node = $t2.node;}
	|t3 = azar {$node = $t3.node;}
	|t4 = menos {$node = $t4.node;}
	|t5 = producto {$node = $t5.node;}
	|t6 = potencia {$node = $t6.node;}
	|t7 = division {$node = $t7.node;}
	|t8 = resto {$node = $t8.node;}
	|t9 = suma {$node = $t9.node;}
	|t10 = iguales 
	|t11 = y 
	|t12 = o 
	|t13 = mayorque  
	|t14 = menorque 
	|t15 = redondea  
	) PAR_CLOSE?
	;	
	
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
	BOOLEAN {$node = new Constant(Boolean.parseBoolean($BOOLEAN.text));}|
	ID {$node = new VarRef($ID.text);}|
	INTEGER {$node = new Constant(Integer.parseInt($INTEGER.text));}|
	FLOAT {$node = new Constant(Float.parseFloat($FLOAT.text));}|
	PAR_OPEN expression {$node = $expression.node;} PAR_CLOSE
	;
	

//Suma entre uno o más números
suma returns [ASTNode node]: SUMA PAR_OPEN t1=opera COMMA t2=opera {$node = new Addition($t1.node, $t2.node);} 
	(COMMA t3=opera {$node = new Addition($node, $t3.node);} )* PAR_CLOSE
	;
//Devuelve diferencia entre los n números (minimo 2)
diferencia returns [ASTNode node]: DIFERENCIA PAR_OPEN t1=opera COMMA t2=opera {$node = new Substraction($t1.node, $t2.node);} 
	(COMMA t3=opera {$node = new Substraction($node, $t3.node);} )* PAR_CLOSE
	;
	
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


/* Expresión regular de una lista
 * Puede tener identificadores o numeros, tiene que estar separados por espacio coma espacio
 */
lista: PAR_SQ_OPEN term (COMMA term)* PAR_SQ_CLOSE | PAR_SQ_OPEN PAR_SQ_CLOSE;
//Expresiones regulares de operaciones con listas
elegir: ELEGIR lista;
cuenta: CUENTA lista;
ultimo: ULTIMO lista;
elemento: ELEMENTO INTEGER lista;
primero: PRIMERO lista;

/* Expresión regular de un procedimiento
 * Debe tener la palabra clave para, seguida de un identificador que debe comenzar con minuscula con un maximo de 10 caracteres
 * cuantos espacios quiera, puede o no tener parametros, puede tener sentencias o llamadas a otros procedimientos
 * cuantas lineas quiera pero al final debe tener la palabra clave fin
 */
procedimiento:
	(PARA ID params?
	NEWLINE*
	(sentence | llamada | comentario)*
	NEWLINE*
	FIN) NEWLINE* 
	;
/* Expresión regular de los parámetros 
 * Comienza con paréntesis cuadrados, seguido de al menos un identificador, de haber más tienen que ser separados por comas
 */
params: PAR_SQ_OPEN ID (COMMA ID)* PAR_SQ_CLOSE | PAR_SQ_OPEN PAR_SQ_CLOSE ;	
	
//TOKENS	
PARA: 'para';
FIN: 'fin';
BOOLEAN: 'true' | 'false';
COMMA: ',';
HAZ: 'haz';
INIC: 'inic';
INC: 'inc';
AVANZA: 'AV';
RETROCEDE: 'RE';
GIRADERECHA: 'GD';
GIRAIZQUIERDA: 'GI';
OCULTATORTUGA: 'OT';
APARECETORTUGA: 'AT';
PONPOS: 'ponPos';
PONXY: 'ponXY';
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
COLOR: 'blanco' | 'azul' | 'marron' | 'cian' | 'gris' | 'amarillo' | 'negro' | 'rojo' | 'verde'
		;
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

PAR_OPEN: '(';
PAR_CLOSE: ')';
PAR_SQ_OPEN: '[';
PAR_SQ_CLOSE: ']';

ID: [a-z][a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?[a-zA-Z0-9_&-@]?;

INTEGER: MINUS?[0-9]+;
FLOAT: MINUS? POINT [0-9]+ | MINUS? [0-9]+ POINT [0-9]*;
NEWLINE: '\n';

WS: [ \t\r]+ -> skip;