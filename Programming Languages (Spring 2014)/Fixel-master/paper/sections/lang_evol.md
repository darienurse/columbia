# Language Evolution

During the development of Fixel, we chose to follow an iterative approach, starting with the simplest Fixel programs and building up our implementation from there. Feedback and advice from past groups also supported this development strategy. Consequently, we identified a hierarchy of program features to implement, and added them to our language in a series of phases. 

In Phase 1, we sought to implement an extremely basic version of Fixel, where the user could successfully write and execute one-line programs invoking a built-in function, with the end result of editing an image. Although the Fixel programs were simple in construction, writing the underlying back-end involved crafting a full-featured lexer, constructing a complete grammar for the parser, and including rudimentary code generation. Moreover, we had to formulate all the built-in functions, as well as integrate the compiled Fixel with our native libraries. 

We then moved on to Phase 2: implementing support for multi-line programs and the additional power they afford to the user. Development in this phase included multiple function calls, variables as parameters to functions, modifying multiple images, and built-in functions utilizing multiple image objects. In this stage of Fixel's implementation, we detected and removed many ambiguities in our grammar, as well as grew our code generation modules to accommodate the more complex programs Fixel would support. After resolving our grammar's ambiguities, we also found that the control flow we'd defined worked seamlessly without significant additional effort. 

In Phase 3, we added functionality for supporting Color and Pixel. We chose to add these types in the third phase rather than earlier so as to ensure that the Fixel language could adequately create basic filters before providing additional means of control and image modification. In this phase, we also finalized some ambiguities in the requirements we would impose upon the structure of programs written in Fixel, such as the ordering of function definitions and invocations. By implementing our grammar and code generation in an earlier phase and then revisiting the structure of Fixel programs in this phase, we were able to make more educated decisions about how users would choose to write Fixel programs and how to best match those use cases with our underlying system architecture.

<!-- is this paragraph above ok? -->

## Compiler Tools

After evaluating our goals for Fixel as well as our group's programming background, we made the design decision to use Python as the target language for our compiler. Additionally, we chose to implement the compiler in Python. This allowed us to use the well-documented *PLY* (Python Lex-Yacc) lexer and parser tools to develop our translator, as well as utilize Python's runtime interpreter to facilitate more user-friendly development.

## Libraries

We chose to construct our built-in functions on top of `PIL` (Python Imaging Library). Using PIL but abstracting away its lower levels provided us with the ability to support many complex image processing algorithms while focusing on the user-facing interface to these functions in Fixel.

For testing, we chose to use Python's built-in unit testing framework, `unittest`, as well as the external libraries `Mock` to assist in isolating logical units of our code, `Nose` to assist in running the tests and `Coverage` to track our code coverage. These libraries allowed us to quickly and effectively achieve testing coverage across our code base during the development process.

## Consistency

When implementing the initial LRM we developed, PLY's Yacc tool as well as our test cases quickly demonstrated the gaps and ambiguities in our grammar. As we resolved these rules, we were sure to also update our LRM specification in a timely fashion so that our written documents would stay in sync with our technical implementation. 

We applied a similar workflow to the development of built-in functions. Initially we constructed a list of necessary built-ins and developed our library from that list. As development progressed, we found that a certain function could be reduced to two more fundamental built-ins, or some types of workflows could benefit from a new built-in. This required repeated additions to the built-in functions library and their integration with the code generation modules. 

