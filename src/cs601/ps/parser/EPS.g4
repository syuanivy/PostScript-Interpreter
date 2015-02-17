grammar EPS;

@header {
package cs601.ps.parser;
import cs601.ps.PSInterpreter;
import cs601.ps.objects.*;
}

@parser::members {
    protected PSInterpreter interpreter;
   	public EPSParser(TokenStream input, PSInterpreter interpreter) {
   		this(input);
   		this.interpreter = interpreter;
    }
}

file : exec ;

/** Immediate execution mode; e.g., "40 60 add 2 div" executes right away. */
exec returns [int n = 0]
    :   (   instruction {interpreter.execute($instruction.o); $n++;}
        |   '=='        {interpreter.popAndPrint();}
        )*
    ;

instruction returns [PSObject o]
    :   ID              {$o = interpreter.lookup($ID.text);} // look it up
    |   value           {$o = $value.v;}
    ;

value returns [PSObject v]
    :   INT             {$v=new PSInt($INT.text);}
    |   STRING          {$v=new PSString($STRING.text);}
    |   REAL            {$v=new PSReal($REAL.text);}
    |   SYM             {$v=new PSNameLiteral($SYM.text);}
    |   array           {$v=$array.a;}
    |   procedure       {$v=$procedure.p;}
    |   'true'          {$v=new PSBoolean(true);}
    |   'false'         {$v=new PSBoolean(false);}
    ;

array returns [PSArray a]
    :   '[' exec ']'    {$a = interpreter.popAsArray($exec.n);}
    ;

/** defer execution, just collect of the symbols; e.g., "{40 60 add 2 div}" */
procedure returns [PSDeferredCodeBlock p]
locals [List<PSObject> code = new ArrayList<>()]
    :   '{'
        (   ID          {$code.add(new PSOperatorName($ID.text));}
        |   value       {$code.add($value.v);}
        )*
        '}'
        {$p = new PSDeferredCodeBlock(new PSProcedure($code));}
    ;


STRING: '(' .*? ')' ;

SYM :   '/' ID ;

INT	:   [+-]? [0-9]+ ;

REAL:	INT ('.' [0-9]*) ;

ID  :   [_a-zA-Z] [_a-zA-Z\-]* ;

WS  :   [ \t\r\n]+ -> skip ;

CMT : '%' .*? '\n' -> skip ;
