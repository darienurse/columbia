# Language Reference Manual

## Introduction
This Language Reference manual provides the syntax details of the Fixel language which facilitates image editing and makes writing the code for image modification easier.  This manual begins with a description of lexical conventions, gives information about the syntax used in Fixel programs, and then provides a grammar.

## Lexical Conventions
This section describes the lexical conventions used in Fixel.  This includes detailing the identifiers, keywords, and reserved characters.

### Comments
Fixel uses a double forward slash `//` to denote a single line comment.  A single line comment refers to the area from the end of the double slash until a newline character is seen.  Fixel does not support multiline comments.  Therefore, each line of a comment must begin with the double forward slash.

### Identifiers 
Identifiers are used to represent a primitive, object, or a function.  All identifiers must start with either a letter or an underscore.  However, in the rest of the name, it may contain letters, numbers, and underscores.  On the other hand, reserved characters as well as spaces, and any other whitespace are prohibited.  All identifiers are case sensitive.

### Keywords
Certain identifiers are reserved and can only be used as keywords.  Their usage is prohibited in other contexts.  A list of keywords is shown below:

|    |  |
|--------|-------|
| for    | while |
| if     | else  |
| return | in    |
| true   | false |
| and    | forp  |
| not    |       |


### Reserved Characters
Certain characters are reserved due to their necessity to the grammar.  The following is a list of these characters:

|     |      |      |      |
|-----|------|------|------|
| `+` | `-`  | `*`  | `/`  |
| `(` | `)`  | `[`  | `]`  |
| `:` | `//` | `,`  | `"`  |
| `#` | `@`  | `<`  | `>`  |
| `=` | `!=` | `>=` | `<=` |

### Forbidden Words
Certain identifiers are forbidden since they correspond to keywords in python.  A list of forbidden keywords is shown below:

|        |        |          |         |
|--------|--------|----------|---------|
| del    | from   | as       | elif    |
| global | with   | assert   | pass    |
| True   | False  | yield    | break   |
| execpt | import | print    | class   |
| exec   | raise  | continue | finally |
| is     | def    | lambda   | try     |

## Types
### Basic Types
Fixel accounts for several different types of primitives.  They are described below.

+ **Integer**:
Integer constants follow the same rules as they would in Python.  Therefore they are implemented using a long in C resulting in at least 32 bits of precision.

+ **Boolean**: A boolean refers to a value that can take either true or false.

+ **String**: A string in Fixel, like a string in Python, refers to an ordered list of characters.  Additionally, this sequence of characters that are strung together is preceded and followed by double quotes.

### Derived Types
+ **Image**: The Image type is used to refer to the images passed as program arguments when the program is run.  Fixel built in functions operate on these image types. Images have height and width properties, and allow individual pixels to be both read and written.

+ **Color**: Color is a wrapper for a 3 integer tuple.  It contains exactly 3 integers each of which correspond to the red, green, and blue (RGB) values of a color respectively. Colors support basic arithmatic operations between with numbers and other colors.

+ **Pixel**: Accessible only from a forp loop, a pixel is a wrapper around a color, an x and a y coordinate. Assigning a color to a pixel variable updates the pixel of the Image that the pixel is associated with.

### Constants
+ **Character Constants**: This refers to a sequence of characters of length 1 or more with an unchangeable value.  Certain character constants are necessarily represented with a corresponding escape sequence.  The list is shown below.

|                 |      |
|-----------------|------|
| newline         | `\n` |
| horizontal tab  | `\t` |
| carriage return | `\r` |
| backslash       | `\\` |
| double quote    | `!=` |

+ **Boolean Constants**: Boolean constants will be either true or false.

## Scope
Variables that share scope are required to have unique names.  A variable’s identifier will have scope beginning from where it is declared and persist until the end of the function it is declared in.

### Global Scope
All functions have global scope, and can be called from anywhere in the program.

### Function Scope
Variables that are declared inside functions, will have a scope that lasts for the duration of the function and will expire when the function is exited. The implicit image and list variables created from the program arguments have scope throughout the entirety of the main function.

## Expressions
### Intermediate Expression
Intermediate expressions can be a primary expression or a function expression.

	intermediate-expression:
		primary-expression
		function-expression

### Primary Expressions
Primary expressions can be variables, string literals, boolean and number primitive literals, list creation, and parenthesized expressions. All variable names are preceded by the `@` symbol. Variable properties can be accessed with the `.` operator and list or image sequenced values can be accessed with brackets.

	primary-expression:
		variable-access-expression
		STRING 
		NUMBER
		TRUE
		FALSE
		'[' parameters ']’
		'(' expression ')’

	variable-access-expression:
		variable-expression
		variable-access-expression '.' ID

	variable-expression:
		variable
		variable_expression '[' parameters ']'

	variable:
		'@' ID

### Function Calls
A function call starts with the `#` symbol, followed by the function name and an optional comma separated argument list.

	function-expression:
		'#' ID parameters

	parameters:
		primary-expression
		parameters ',' primary-expression
		epsilon

### Logical NOT operator
The logical NOT operator is left-associative and includes the keyword `not`. This operator and the multiplicative operators have the highest priority.

	logical-NOT-expression:
		primary-expression
		'not' logical-NOT-expression

Unlike other operators, the logical NOT operator is unary. If the operand evaluates to true or a boolean equivalent, the logical NOT expression yields a boolean with value false, or vice-versa.

### Multiplicative Operators
Multiplicative operators are left-associative and include the symbols * and /. These are the operators and logical NOT operator have the highest priority.

	multiplicative-expression:
		logical-NOT-expression
		multiplicative-expression '*' logical-NOT-expression
		multiplicative-expression '/' logical-NOT-expression

The * (multiplication) operator yields the product of its arguments. If one operator is a number and the other a sequence, sequence repetition is performed; a negative repetition factor yields an empty sequence. If both are colors then a new color is formed where each value is the product of the two corresponding values of the operands. If one is a color and the other a number, then a new color is formed by multiplying each field of the color by the number.

The / (division) operator yield the quotient of its arguments. Division by zero raises the ZeroDivisionError exception. If both are colors then a new color is formed where each value is the quotient of the two corresponding values of the operands. If one is a color and the other a number, then a new color is formed by dividing each field of the color by the number, or the number by each field, depending on order.

### Additive Operators
Additive operators are left-associative and include the symbols + and -. These operators have a lower priority than multiplicative operators.

	additive-expression: 
		multiplicative-expression 
		additive-expression '+' multiplicative-expression 
		additive-expression '-' multiplicative expression 

The + (addition) operator yields the sum of its arguments. If one operator is a number and the other a sequence, the sequences are concatenated. If both are colors then a new color is formed where each value is the sum of the two corresponding values of the operands. If one is a color and the other a number, then a new color is formed by adding each field of the color to the number.

The - (subtraction) operator yields the difference of its arguments. If both are colors then a new color is formed where each value is the difference of the two corresponding values of the operands. If one is a color and the other a number, then a new color is formed by subtracting each field of the color from the number, or the number from each field, depending on order.

### Relational Operators
Relational operators are left-associative and include the symbols <, >, <=, >=. These operators have a lower priority than additive operators.

	relational-expression:
		additive-expression
		relational-expression '<' additive-expression
		relational-expression '>' additive-expression
		relational-expression '<=' additive-expression
		relational-expression '>=' additive-expression

The < (less than), > (greater than), <= (less than or equal to), >= (greater than or equal to) operators evaluate to a boolean value of true if the respective relation is true and false if it is false.

If the operands are numbers, then the operands are compared arithmetically. If the operands are Strings, then the strings are compared lexicographically using the numeric equivalents of their characters.

### Equality Operators
Equality operators are left-associative and include the symbols == and !=. These operators have a lower priority than relational operators.

	equality-expression: 
		relational-expression
		equality-expression '==' relational-expression 
		equality-expression '!=' relational-expression

The == (equals) and != (not equals) operators evaluate to a boolean value of true if the respective relation is true and false if it is false.

If the operands are numbers, then the operands are compared arithmetically. If the operands are Strings, then the strings are compared lexicographically using the numeric equivalents of their characters. If the operands are of different types that cannot be converted to a common type, then the result is always unequal.

### Logical AND operator
The logical AND operator is left-associative and includes the keyword 'and'. This operator has a lower priority than equality operators.
 
	logical-AND-expression: 
		equality-expression 
		logical-AND-expression 'and' equality-expression 

Consider the expression: x and y. The operator first evaluates x; if x is false, the expression evaluate to false; otherwise, the expression is evaluated as y.

### Logical OR operator
The logical OR operator is left-associative and includes the keyword 'or'. This operator has a lower priority than the logical AND operator.

	logical-OR-expression: 
		logical-AND-expression 
		logical-OR-expression 'or' logical-AND-expression 

Consider the expression: x or y. The operator first evaluates x; if x is true, the expression evaluate to true; otherwise, the expression is evaluated as y.

### Assignment Expressions
Assignment expressions are either the assignment of an assignment expression to an identifier, or a logical or expression.

	assignment-expression:
		ID '=' assignment-expression 
		logical-OR-expression

Assignment is evaluated after all other operations in an expression.

### Declarations
A function declaration starts with the function name and is followed by an optional comma separated argument list and a mandatory statement block.

	function-definition:
		ID parameter-declaration block

	parameter-declaration:
		epsilon
		variable
		parameter-declaration ',' variable

### Statements
Statements are units of code executed to change the program state, but are not associated with a value. Statements include control flow, loops and expression statements.

	statement: 
		expression-statement 
		selection-statement
		iteration-statement 
		return-statement

### Statement Blocks
Statements can be grouped into a logical block that determines a unit of scope and execution, so that all statements it contains can be executed when a function is called, a loop body is entered, or control otherwise selects the block for execution. The block begins with a colon, followed immediately by a newline. All lines in the block after the line containing the colon have an indentation greater than or equal to the previous line. A line not conforming to this rule marks the end of the block.

	block:
		':' NEWLINE INDENT statement-list DEDENT

A line with greater indentation than the previous line is interpreted as being preceded by an INDENT token. A line with indentation less than the proceeding line is interpreted as being preceded by a DEDENT token.

### Expression Statements
An expression statement is simply an expression followed by a new line that separates the statement from the following statement.

	expression-statement:
		expression NEWLINE

### Selection Statements
Selection statements allow the programmer to specify control flow.

	selection-statement:
		'if' expression block
		'if' expression block 'else' block

In both if and if else statements the if block is executed if the boolean expression evaluates to true. In if else statements, the else block is executed if the boolean expression evaluates to false.

### Iteration Statements
Iteration statements allow the user to write loops. While loops execute until a boolean expression evaluates to false. for loops allow iteration over each element in a sequence, and forp, or for pixel, loops allow iteration over every pixel in an image.

	iteration-statement:
		'for' variable 'in' variable block
		'forp' variable 'in' variable block
		'while' expression block

The for loop must be given a variable with type list. In each iteration of the loop, the first identifier is assigned the next item in the list. The forp loop is similar to this, except that the second variable must be an Image. Additionally, if the pixel variable is assigned a color, this change is reflected in the image. While loops evaluate a boolean expression at the start of each iteration, and abort if it evaluates to false.

## Grammar
	program:
		statement-list
		statement-list translation-unit

	block:
		':' NEWLINE INDENT statement-list DEDENT

	translation-unit:
		function-definition
		translation-unit function-definition

	function-definition:
		ID parameter-declaration block

	parameter-declaration:
		epsilon
		variable
		parameter-declaration ',' variable

	statement-list:
		statement
		statement-list statement

	statement: 
		expression-statement 
		selection-statement
		iteration-statement 
		return-statement

	return-statement:
		'return' expression-statement

	expression-statement:
		expression NEWLINE

	selection-statement:
		'if' expression block
		'if' expression block 'else' block

	iteration-statement:
		'for' variable 'in' variable block
		'forp' variable 'in' variable block
		'while' expression block

	expression:
		assignment-expression

	assignment-expression:
		variable-expression '=' assignment-expression 
		logical-OR-expression

	logical-OR-expression: 
		logical-AND-expression 
		logical-OR-expression 'or' logical-AND-expression 
	 
	logical-AND-expression: 
		equality-expression 
		logical-AND-expression 'and' equality-expression 
	 
	equality-expression: 
		relational-expression
		equality-expression '==' relational-expression 
		equality-expression '!=' relational-expression

	relational-expression:
		additive-expression
		relational-expression '<' additive-expression
		relational-expression '>' additive-expression
		relational-expression '<=' additive-expression
		relational-expression '>=' additive-expression

	additive-expression: 
		multiplicative-expression 
		additive-expression '+' multiplicative-expression 
		additive-expression '-' multiplicative expression 
 
	multiplicative-expression: 
		logical-NOT-expression
		multiplicative-expression '*' logical-NOT-expression
		multiplicative-expression '/' logical-NOT-expression

	logical-NOT-expression:
		intermediate-expression
		'not' logical-NOT-expression

	intermediate-expression:
		primary-expression
		function-expression

	primary-expression: 
		variable-access-expression
		STRING 
		NUMBER
		TRUE
		FALSE
		'[' parameters ']'
		'(' expression ')'

	function-expression:
		'#' ID parameters

	parameters:
		primary-expression
		parameters ',' primary-expression
		epsilon

	variable-access-expression:
		variable-expression
		variable-access-expression '.' ID

	variable-expression:
		variable
		variable_expression '[' parameters ']'

	variable:
		'@' ID

