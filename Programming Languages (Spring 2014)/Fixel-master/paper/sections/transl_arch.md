#Translator Architecture

At the highest level, our translator can be said to have three distinct stages of translation: lexing, parsing, and code generation. A high-level block diagram is included for clarity.

![High-level block diagram of translation architecture](./img/transl_arch.png)

## Lexer
The first step of our translation process is lexing. The Lexer is written in PLY's lexing tool. The Lexer continuously processes the input string and matches its contents with specified tokens. The lexer was written by our Project Manager, Kavita, and our Language and Tools Guru, Amrita.

## Parser
The second step of our translation process is parsing, which is written using PLY's yacc parsing tool. The Parser contains all the rules of Fixel's grammar and constrcts an abstract syntax tree (AST) from the tokens produced by the Lexer. The tokens are produced as a LexToken containing a type and value for each token. The parser evaluates and classifies the tokens while constructing an AST with a hierarchy of Node objects. The Node object contains any children, a type for the Node, and a leaf value if applicable. The parser was written by our System Architect, Darien, and our Language and Tools Guru, Amrita. 

## Code Generator
The AST produced by the parser is passed into the third step of our translation process, the Code Generator. The code generator is implemented in two steps: tree processing and post-processing. These steps are called by a higher-level Translator module.

### Tree Processing
In the Tree Processing stage, we recursively process Nodes in the AST based on a Node's value. The result is appended to an output string to be passed to later stages of the translator  Certain types of Nodes, such as function defintions, indent/dedent, and variable Nodes, have custom processing steps that are independently implemented. Other Nodes are simply added to the output string. The Tree Processing stage of Code Generation was written by our Tester and Validator, Matt.

### Post-Processing
After the tree is parsed into requisite Python output, file handling, built-in function imports, and other Python code needs to prepended and appended to the output string to produce proper functionality. This step also handles file IO for images and creating the final Python program. The post-processor was written by our Tester and Validator, Matt, and our System Integrator, Neel.

### Built-in Functions Library
Fixel is a language that can be used by consumers right out of the box: it comes with an assortment of functions that can be stacked and customized by the user to begin making transformations on their images. Given a basic understanding for image manipulation, the user can begin creating custom filters and if desired, more advanced programs in Fixel. The built-in functions were written by our System Integator, Neel. For a more comprehensive description of Fixel's built-in functions and their uses, please consult the white paper.

## Integration
All of the different stages of our translator are brought together and run in tandem by a top-level wrapper script, `fixel`. This script was written by our System Integrator, Neel.
 
