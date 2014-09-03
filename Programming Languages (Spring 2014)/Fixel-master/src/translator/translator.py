import parser
import lexer
import generator
import post_process


def translate(source_string=None, verbose=False):
	my_lex = lexer.get_lex(verbose)
	my_parser = parser.get_yacc()
	if source_string is None:
		source_string = 'if @hey < not 1:\n\t#sup\n#hey @image1,@image2\nsup:\n\treturn 5\n'

	if verbose:
		print("source:\n")
		print(source_string + '\n')
		print('tokens:\n')

	tree = my_parser.parse(source_string, lexer=my_lex)
	gen = generator.Generator(tree)
	main_string, functions_string = gen.get_strings()
	python_string = post_process.create_program(main_string, functions_string)

	if verbose:
		print("\nAST:\n")
		print(str(tree) + '\n')
		print('python:\n')
		print(python_string)

	return python_string
