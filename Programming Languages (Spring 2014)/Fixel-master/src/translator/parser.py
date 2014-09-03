import sys
import lexer
import ply.yacc as yacc

"""
Node object for tree generation
"""
class Node(object):
    def __init__(self, value, *children):
        self.value = value
        self.children = children

    def __str__(self):
        return self.traverse(1)

    def traverse(self, i):
        s = repr(self.value)
        indent = "\n" + i*' |'
        for child in self.children:
            #print children
            if len(child.value) > 0:
                s += indent + child.traverse(i+1)
        return s

"""
Error handling in the parser
"""
class ParsingError(Exception):
     def __init__(self, value):
         self.value = value
     def __str__(self):
         return repr(self.value)

"""
Creates node leaves for a program node
"""
def p_program(p):
    """
    program : statement_list
            | statement_list translation_unit

    """
    if len(p) == 2:
        p[0] = Node('program', p[1])
    else:
        p[0] = Node('program', p[1], p[2])

"""
Creates node leaves for a block definition
"""
def p_block(p):
    """
    block : ':' NEWLINE INDENT statement_list DEDENT
    """
    p[0] = Node('block', Node(p[1]), Node(p[2]), Node(p[3]), p[4], Node(p[5]))

"""
Creates node leaves for a function definition 
"""
def p_function_definition(p):
    """
    function_definition   : ID parameter_declaration block
    """
    id_node = Node(p[1])
    p[0] = Node('function_definition', id_node, p[2], p[3])

"""
Creates node leaves for a translation unit
"""
def p_translation_unit(p):
    """
    translation_unit    : function_definition
                        | translation_unit function_definition
    """
    if len(p) == 2:
        p[0] = Node('translation_unit', p[1])
    else:
        p[0] = Node('translation_unit', p[1], p[2])

"""
Creates node leaf for a parameter declaration
(typically for a function)
"""
def p_parameter_declaration(p):
    """
    parameter_declaration   : variable
                            | parameter_declaration ',' variable
    """
    if len(p) == 2:
        p[0] = Node('parameter_declaration', p[1])
    else:
        p[0] = Node('parameter_declaration', p[1], Node(p[2]), p[3])

"""
Creates the node leaf for an epsilon production
"""
def p_parameter_declaration_eps(p):
    """
    parameter_declaration   : epsilon
    """
    p[0] = p[1]

"""
Constructs the node leaves for a statement list
"""
def p_statement_list(p):
    """
    statement_list  : statement
                    | statement_list statement
    """
    if len(p) == 3:
        p[0] = Node('statement_list', p[1], p[2])
    else:
        p[0] = Node('statement_list', p[1])

"""
Constructs node leaf for a statement production
"""
def p_statement(p):
    """
    statement   : expression_statement
                | selection_statement
                | iteration_statement
                | return_statement
    """
    p[0] = Node('statement', p[1])

"""
Constructs node leaf for a return statement
"""
def p_return_statement(p):
    """
    return_statement    : RETURN expression_statement
    """
    p[0] = Node('return_statement', Node(p[1]), p[2])

"""
Constructs node leaf for a full expression statement
"""
def p_expression_statement(p):
    """
    expression_statement    : expression NEWLINE
    """
    p[0] = Node('expression_statement', p[1], Node(p[2]))

"""
Constructs a node leaf for an if-else block
"""
def p_selection_statement(p):
    """
    selection_statement : IF expression block
                        | IF expression block ELSE block
    """
    if_node = Node(p[1])
    if len(p) == 4:
        p[0] = Node('selection_statement', if_node, p[2], p[3])
    else:
        p[0] = Node('selection_statement', if_node, p[2], p[3], Node(p[4]), p[5])

"""
Constructs a node leaf for iteration statements
i.e. for, forp, while
"""
def p_iteration_statement(p):
    """
    iteration_statement     : FOR variable IN variable block
                            | FORP variable IN variable block
                            | WHILE expression block
    """
    if len(p) == 4:
        p[0] = Node('iteration_statement', Node(p[1]), p[2], p[3])
    else:
        forp_node = Node(p[1])
        #id_node = Node(p[2])
        in_node = Node(p[3])
        p[0] = Node('iteration_statement', forp_node, p[2], in_node, p[4], p[5])

"""
Constructs a node leaf for an expression
"""
def p_expression(p):
    """
    expression  : assignment_expression
    """
    p[0] = Node('expression', p[1])

"""
Constructs a node leaf for an assignment expression
"""
def p_assignment_expression(p):
    """
    assignment_expression   : variable_expression '=' assignment_expression
                            | logical_OR_expression
    """
    if len(p) == 2:
        p[0] = Node('assignment_expression', p[1])
    else:
        p[0] = Node('assignment_expression', p[1], Node(p[2]), p[3])

"""
Constructs a node leaf for logical OR expression production
"""
def p_logical_OR_expression(p):
    """
    logical_OR_expression   : logical_AND_expression
                            | logical_OR_expression OR logical_AND_expression
    """
    if len(p) == 2:
        p[0] = Node('logical_OR_expression', p[1])
    else:
        p[0] = Node('logical_OR_expression', p[1], Node(p[2]), p[3])
"""
Constructs a node leaf for logical AND expression production
"""
def p_logical_AND_expression(p):
    """
    logical_AND_expression   : equality_expression
                            | logical_AND_expression AND equality_expression
    """
    if len(p) == 2:
        p[0] = Node('logical_AND_expression', p[1])
    else:
        p[0] = Node('logical_AND_expression', p[1], Node(p[2]), p[3])

"""
Constructs a node leaf for an equality expression
"""
def p_equality_expression(p):
    """
    equality_expression   : relational_expression
                          | equality_expression DUBEQUAL relational_expression
                          | equality_expression NEQUAL relational_expression
    """
    if len(p) == 2:
        p[0] = Node('equality_expression', p[1])
    else:
        p[0] = Node('equality_expression', p[1], Node(p[2]), p[3])

"""
Constructs a node leaf for a relational expression
"""
def p_relational_expression(p):
    """
    relational_expression   : additive_expression
                            | relational_expression '<' additive_expression
                            | relational_expression '>' additive_expression
                            | relational_expression LESSTHANEQ additive_expression
                            | relational_expression GREATERTHANEQ additive_expression
    """
    if len(p) == 2:
        p[0] = Node('relational_expression', p[1])
    else:
        p[0] = Node('relational_expression', p[1], Node(p[2]), p[3])

"""
Constructs a node leaf for an additive expression
"""
def p_additive_expression(p):
    """
    additive_expression : multiplicative_expression 
                        | additive_expression '+' multiplicative_expression 
                        | additive_expression '-' multiplicative_expression
    """
    if len(p) == 2:
        p[0] = Node('additive_expression', p[1])
    else:
        p[0] = Node('additive_expression', p[1], Node(p[2]), p[3])

"""
Constructs a node leaf for a multiplicative expression
"""
def p_multiplicative_expression(p):
    """
    multiplicative_expression   : logical_NOT_expression
                                | multiplicative_expression '*' logical_NOT_expression
                                | multiplicative_expression '/' logical_NOT_expression
    """
    if len(p) == 2:
        p[0] = Node('multiplicative_expression', p[1])
    else:
        p[0] = Node('multiplicative_expression', p[1], Node(p[2]), p[3])

"""
Constructs a node leaf for a logical NOT expression
"""
def p_logical_NOT_expression(p):
    """
    logical_NOT_expression  : intermediate_expression
                            | NOT logical_NOT_expression
    """
    if len(p) == 2:
        p[0] = Node('logical_NOT_expression', p[1])
    else:
        p[0] = Node('logical_NOT_expression', Node(p[1]), p[2])

"""
Constructs a node leaf for intermediate expressions
"""
def p_intermediate_expression(p):
    """
    intermediate_expression     : primary_expression
                                | function_expression
    """
    p[0] = Node('intermediate_expression', p[1])

"""
Constructs a node leaf for a primary expression token
tokens are: string, number, true, false
"""
def p_primary_expression_token(p):
    """
    primary_expression  : STRING
                        | NUMBER
                        | TRUE
                        | FALSE
    """
    p[0] = Node('primary_expression', Node(p[1]))

"""
Construct a node leaf for primary expressions
"""
def p_primary_expression(p):
    """
    primary_expression  : variable_access_expression
                        | '[' parameters ']'
                        | '(' expression ')'
    """
    if len(p) == 2:
        p[0] = Node('primary_expression', p[1])
    else: 
        p[0] = Node('primary_expression', Node(p[1]), p[2], Node(p[3]))

"""
Construct a node leaf for function expressions
"""
def p_function_expression(p):
    """
    function_expression : '#' ID parameters
    """
    hashtag = Node(p[1])
    iden = Node(p[2])
    p[0] = Node('function_expression', hashtag, iden, p[3])

"""
Constructs a node leaf for parameters
"""
def p_parameters(p):
    """
    parameters : primary_expression
               | parameters ',' primary_expression
    """
    if len(p) == 2:
        p[0] = Node('parameters', p[1])
    else:
        p[0] = Node('parameters', p[1], Node(p[2]), p[3])

"""
Constructs a node leaf for the epsilon production of parameters
"""
def p_parameters_eps(p):
    """
    parameters  : epsilon
    """
    p[0] = p[1]

"""
Constructs a node leaf for a variable access expression
"""
def p_variable_access_expression(p):
	"""
	variable_access_expression : variable_expression
	                           | variable_access_expression '.' ID
	"""
	if len(p) == 2:
		p[0] = Node('variable_access_expression', p[1])
	elif len(p) == 4:
		p[0] = Node('variable_access_expression', p[1], Node(p[2]), Node(p[3]))

"""
Constructs a node leaf for a variable expression
"""
def p_variable_expression(p):
	"""
	variable_expression : variable
						| variable_expression '[' parameters ']'
	"""
	if len(p) == 2:
		p[0] = Node('variable_expression', p[1])
	else:
		p[0] = Node('variable_expression', p[1], Node(p[2]), p[3], Node(p[4]))

"""
Constructs a node leaf for a variable
"""
def p_variable(p):
	"""
	variable : '@' ID
	"""
	at = Node(p[1])
	iden = Node(p[2])
	p[0] = Node('variable', at, iden)

"""
Constructs a node leaf for the epsilon production
"""
def p_epsilon(p):
    """
    epsilon :
    """
    p[0] = Node('')

"""
Produces a parsing erorr message
"""
def p_error(p):
    if p is not None:
        sys.stderr.write('syntax error for ' + p.type + ' ' + p.value + ' at ' + str(p.lexpos) + '\n')
        raise ParsingError('Parsing error - review the syntax error(s) above')

tokens = lexer.tokens

"""
Method to run the parser
"""
def get_yacc():
    return yacc.yacc()
