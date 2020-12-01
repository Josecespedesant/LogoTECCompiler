grammar Logo;
//GRAMATICAS LIBRES DE CONTEXTO
@parser::header{
	import java.util.Map;
	import java.util.HashMap; 
	import java.util.Random;
}

@parser::members{
	Map<String, Object> symbolTable = new HashMap<String, Object>();
}
/* Gramatica de un programa.
* Tiene que tener un comentario en la primera linea.
* Un programa puede tener procedimientos, sentencias, llamadas o comentarios. 
* Entre cada una de ellas puede haber N cantidad de espacios*/
program: comentario
	NEWLINE*
	(procedimiento | sentence | llamada | comentario)*
	NEWLINE*;

//Gramática de un comentario, comienza con dos barras inclinadas y termina en endline
comentario:  '//' ~( '\r' | '\n' )*;

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
sentence: (inicializacion | asignacion | muestra | incremento | avanza | retrocede | girder | girizq
	| ponpos | ponrumbo | ponx | pony | pnclrlapiz | espera | ejecuta | repite | si | sisino | hazhasta | hasta |
	hazmientras | mientras | elegir | cuenta | ultimo | elemento | primero | OCULTATORTUGA | APARECETORTUGA |
	RUMBO | GOMA | BAJALAPIZ | SUBELAPIZ | CENTRO | BORRAPANTALLA) NEWLINE* ;

//Distintas sentencias del lenguaje
asignacion: (HAZ ID opera | HAZ ID opera) {symbolTable.put($ID.text,$opera.value);};
inicializacion: INIC ID ASSIGN opera | HAZ ID ASSIGN opera;
muestra: MUESTRA ID {System.out.println(symbolTable.get($ID.text));}|MUESTRA opera {System.out.println($opera.value);}; 	
incremento: INC ID | INC ID opera;
avanza: AVANZA opera {System.out.println($opera.value);};
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

si: SI PAR_OPEN expression PAR_CLOSE orden;
sisino: SISINO PAR_OPEN expression PAR_CLOSE orden orden;
hazhasta: HAZHASTA orden PAR_OPEN expression PAR_CLOSE;
hasta: HASTA PAR_OPEN expression PAR_CLOSE orden;
hazmientras: HAZMIENTRAS orden PAR_OPEN expression PAR_CLOSE;
mientras: MIENTRAS PAR_OPEN expression PAR_CLOSE orden;

//Operadores de comparación
iguales returns [Object value]: IGUALES t1 = opera t2 = opera {$value = $t1.value.equals($t2.value); };
y returns [Object value]: Y t1 = comparar t2 = comparar {$value = (Boolean) $t1.value && (Boolean) $t2.value;} ; //Falta
o returns [Object value]: Y PAR_OPEN comparar PAR_CLOSE PAR_OPEN comparar PAR_CLOSE; //Falta
mayorque returns [Object value]: MAYORQUE t1= opera t2=opera {$value = (int) $t1.value > (int) $t2.value;} ;
menorque returns [Object value]: MENORQUE t1 = opera t2= opera{$value = (int) $t1.value < (int)$t2.value;};
redondea returns [Object value]: REDONDEA t1 = opera {$value = (int) Math.round( (Float) $t1.value);};

/* Gramática de una orden
 * Una orden está compuesta por dos paréntesis cuadrados donde, dentro contiene ninguna o varias sentencias
 */
orden: PAR_SQ_OPEN sentence* PAR_SQ_CLOSE;

/* Gramática de una comparación
 * Compara dos operaciones, identificadores, u expresion en general mediante un operador de comparación
 */
comparar returns [Object value]
	: comparar (GT | LT | GEQ | LEQ | EQ | NEQ) comparar
	| t1 = opera {$value = $t1.value;}
	;
// Un operador puede ser un identificador, un número positivo o negativo, etc.
opera returns [Object value]
	:
	PAR_OPEN? (
	ID {$value = $ID.text;}
	|BOOLEAN {$value = Boolean.parseBoolean($BOOLEAN.text);}
	|FLOAT {$value = Float.parseFloat($FLOAT.text);}
	|INTEGER {$value = (float) Integer.parseInt($INTEGER.text);}
	|t1=expression {$value = (float)$t1.value;}
	|t2 = diferencia {$value = $t2.value;}
	|t3 = azar {$value = (int) $t3.value;}
	|t4 = menos {$value = $t4.value;}
	|t5 = producto {$value = $t5.value;}
	|t6 = potencia {$value = $t6.value;}
	|t7 = division {$value = $t7.value;}
	|t8 = resto {$value = $t8.value;}
	|t9 = suma {$value = $t9.value;}
	|t10 = iguales {$value = (Boolean) $t10.value;}
	|t11 = y {$value = (Boolean) $t11.value;}
	|t12 = o {$value = (Boolean) $t12.value;}
	|t13 = mayorque {$value = (Boolean) $t13.value;} 
	|t14 = menorque {$value = (Boolean) $t14.value;}
	|t15 = redondea {$value = (int) $t15.value;} 
	) PAR_CLOSE?
	;	
	
expression returns [Object value]:
	t1 = factor {$value = (float)$t1.value;}
	(PLUS t2 = factor{$value = (float) $value + (float) $t2.value;}
		|MINUS t2 = factor{$value = (float) $value - (float) $t2.value;}
	)*;

factor returns [Object value]:
	t1 = term {$value = (float) $t1.value;}
	(MULT t2 = term {$value = (float)$value * (float) $t2.value;}
		|DIV t2 = term {$value = (float)$value / (float) $t2.value;}
	)*;

term returns [Object value]:
	BOOLEAN {$value = Boolean.parseBoolean($BOOLEAN.text);}|
	ID {$value = (float) symbolTable.get($ID.text);} |
	INTEGER {$value = (float) Integer.parseInt($INTEGER.text);}|
	FLOAT {$value = Float.parseFloat($FLOAT.text);}|
	PAR_OPEN expression PAR_CLOSE
	;
	

//Suma entre uno o más números
suma returns [Object value]: SUMA PAR_OPEN t1=opera COMMA t2=opera {$value = (float)$t1.value + (float)$t2.value;} 
	(COMMA t3=opera {$value = (float)$value + (float)$t3.value;} )* PAR_CLOSE
	;
//Devuelve diferencia entre los n números (minimo 2)
diferencia returns [Object value]: DIFERENCIA PAR_OPEN t1=opera COMMA t2=opera {$value = (float)$t1.value - (float)$t2.value;} 
	(COMMA t3=opera {$value = (float)$value - (float)$t3.value;} )* PAR_CLOSE
	;
	
//Genera un numero random entre 0 y el valor parámetro
azar returns [Object value]: AZAR PAR_OPEN t1 = opera {$value =  0 + (int)(Math.random() * (((int)$t1.value - 0) + 1));} PAR_CLOSE;

//Cambia el signo de un resultado
menos returns [Object value]: MENOS PAR_OPEN t1 = opera {$value = (float)$t1.value * -1;} PAR_CLOSE;

//Devuelve el producto de los n números (minimo 2)
producto returns [Object value]: PRODUCTO PAR_OPEN t1=opera COMMA t2=opera {$value = (float)$t1.value * (float)$t2.value;} 
	(COMMA t3=opera {$value = (float)$value * (float)$t3.value;} )* PAR_CLOSE;

//Eleva el primer parametro al segundo
potencia returns [Object value]: POTENCIA PAR_OPEN base= opera COMMA power = opera {$value = (float) Math.pow((float)$base.value, (float)$power.value);} PAR_CLOSE;

//Divide el primer numero entre el segundo div entera
division returns [Object value]: DIVISION PAR_OPEN num = opera COMMA den = opera {$value = (float) $num.value / (float) $den.value;} PAR_CLOSE;

//Residuo entre el primer número y el segundo
resto returns [Object value]: RESTO PAR_OPEN t1 = opera COMMA t2 = opera {$value = (float) $t1.value % (float) $t2.value;} PAR_OPEN;


/* Expresión regular de una lista
 * Puede tener identificadores o numeros, tiene que estar separados por espacio coma espacio
 */
lista: PAR_SQ_OPEN (ID|INTEGER|FLOAT) (COMMA (ID|INTEGER|FLOAT))* PAR_SQ_CLOSE | PAR_SQ_OPEN PAR_SQ_CLOSE;
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
	(sentence | llamada)*
	NEWLINE*
	FIN) NEWLINE* 
	;
/* Expresión regular de los parámetros 
 * Comienza con paréntesis cuadrados, seguido de al menos un identificador, de haber más tienen que ser separados por comas
 */
params: PAR_SQ_OPEN ID (COMMA ID)* PAR_SQ_CLOSE;	
	
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