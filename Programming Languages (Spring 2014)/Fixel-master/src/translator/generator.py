"""
keys are nodes that should have spaces printed around them, and values say where to print.
1 means before, 2 means after and 3 means both (picture bitwise or).
"""
spaces_table = {
	',': 2,
	'return': 2,
	'if': 2,
	'for': 2,
	'in': 3,
	'while': 2,
	'=': 3,
	'or': 3,
	'and': 3,
	'!=': 3,
	'==': 3,
	'<': 3,
	'>': 3,
	'<=': 3,
	'>=': 3,
	'+': 3,
	'-': 3,
	'*': 3,
	'/': 3,
	'not': 2
}

'''
keywords corresponding to the list of built in functions
'''
bif_set = {
	'saveImage',
	'grayscale',
	'scale',
	'stretch',
	'rotate',
	'overlay',
	'blur',
	'sharpen',
	'brighten',
	'contrast',
	'border',
	'cropit',
	'caption',
	'color',
	'collage'
}

'''
remove these characters that are only used in fixel
characters not needed for translation
'''
ignore = ['#', '@', '']


class Generator(object):
	def __init__(self, tree):
		self.indent_level = 0
		self.in_main = True
		self.main_list = []  # output python for main function
		self.function_def_list = []  # output python for rest of program
		self.string_list = self.main_list
		self.tree = tree
		self.forp_pixel_stack = []
		self.forp_image_table = {}
		self.in_forp = False
		self.in_assignment_left = False
		self.process_tree(tree)

	#combines the string corresponding to the main and the funciton definitions
	def get_strings(self):
		return ''.join(self.main_list), ''.join(self.function_def_list)

	'''
	this calls the individual functions depending on the node being processed
	if no such funciton exists, then default processing is used
	'''
	def process_tree(self, node):
		if node.value in custom_functions_table:  # call custom processing function
			custom_function = custom_functions_table[node.value]
			custom_function(self, node)
		else:  # if no custom function, use default processing
			# get spaces information
			spaces = spaces_table.get(node.value, 0)
			space_before = spaces & 1
			space_after = spaces & 2

			if space_before:  # add space before processing
				self.string_list.append(' ')

			if len(node.children) > 0:  # non-leaf
				# process the children
				for child in node.children:
					self.process_tree(child)
			elif node.value not in ignore:  # leaf
				# if it's a leaf add it to the string list
				self.string_list.append(node.value)

			if space_after:
				self.string_list.append(' ')

	'''
	parses the program node
	'''
	def process_program(self, node):
		if len(node.children) == 1:
			self.process_tree(node.children[0])
		else:
			statement_list, translation_unit = node.children
			self.process_tree(statement_list)
			self.string_list = self.function_def_list
			self.in_main = False
			self.process_tree(translation_unit)

	'''
	parses the function definition node
	'''
	def process_function_definition(self, node):
		id_node, parameter_declaration, block = node.children
		self.string_list.append('def ')
		self.process_tree(id_node)
		self.string_list.append('(')
		self.process_tree(parameter_declaration)
		self.string_list.append(')')
		self.process_tree(block)

	'''
	parses the function expression node
	'''
	def process_function_expression(self, node):
		hashtag, id_node, parameters = node.children
		if id_node.value in bif_set:
			self.string_list.append('fixelFunctions.')
		self.process_tree(id_node)
		self.string_list.append('(')
		self.process_tree(parameters)
		self.string_list.append(')')

	'''
	parses the newline
	'''
	def process_newline(self, node):
		self.string_list.append(node.value)
		for i in range(0, self.indent_level):
			self.string_list.append('\t')

	'''
	parses the indent node
	'''
	def process_indent(self, node):
		self.indent_level += 1
		self.string_list.append('\t')

	'''
	parses the dedent node
	'''
	def process_dedent(self, node):
		self.indent_level -= 1
		del self.string_list[-1]  #todo worry about index errors

	'''
	process a variable node
	'''
	def process_variable(self, node):
		id_node = node.children[1]
		if self.in_main:
			self.string_list.append('ns.')

		# check if we need to replace pixel with image pixel access
		variable_name = id_node.value
		if self.in_forp and self.in_assignment_left:
			self.in_assignment_left = False

			if variable_name in self.forp_image_table:
				forp_image = self.forp_image_table[variable_name] #assigns image based on pixel
				if self.in_main:
					variable_name = 'ns.' + variable_name #prepend 'ns.' if necessary
				str_list = [forp_image, '[', variable_name, '.x, ', variable_name, '.y', ']'] #changes form to allow for pixel access in python
				self.string_list.extend(str_list)
			else:
				self.process_tree(id_node)
		else:
			self.process_tree(id_node)

	
	'''
	process iteration statements
	'''
	def process_iteration_statement(self, node):
		children = node.children
		if children[0].value == 'forp':
			self.in_forp = True

			# grab names of two variables
			image = children[3].children[1].value
			pixel = children[1].children[1].value
			self.forp_pixel_stack.append(pixel)
			self.forp_image_table[pixel] = image

			if self.in_main:  # prepend 'ns.' if needed
				pixel = 'ns.' + pixel
				image = 'ns.' + image

			# create the pixel object
			self.string_list.extend([pixel, ' = runtime_classes.Pixel(', image, ')\n', ('\t' * self.indent_level)])

			# create two loops and set pixel color
			self.indent_level += 1  # increment for first loop
			self.string_list.extend(['for ', pixel, '.x in xrange(0, ', image, '.width):\n', ('\t' * self.indent_level)])
			self.indent_level += 1  # increment for second loop
			self.string_list.extend(['for ', pixel, '.y in xrange(0, ', image, '.height):\n', ('\t' * self.indent_level)])

			# only process the statement list of the block
			block = children[4]
			self.process_tree(block.children[3])

			# get back to state before the forp loop
			self.indent_level -= 2
			del self.string_list[-2:]
			pixel = self.forp_pixel_stack[-1]
			del self.forp_image_table[pixel]
			del self.forp_pixel_stack[-1]
			if len(self.forp_pixel_stack) == 0:
				self.in_forp = False
		else:
			for child in children:
				self.process_tree(child)

	'''
	processes node corresponding to assignment expressions
	'''
	def process_assignment_expression(self, node):
		children = node.children
		if self.in_forp and len(children) == 3: #special forp case
			self.in_assignment_left = True
		for child in children: #process children on the tree
			self.process_tree(child)

	def process_true(self, node): #replaces 'true' with 'True' which will be valid in Python
		self.string_list.append('True')

	def process_false(self, node): #replaces 'false' with 'False' which will be valid in Python
		self.string_list.append('False')


'''
list of functions for nodes which require special custom processing
'''
custom_functions_table = {
	'program': Generator.process_program,
	'function_definition': Generator.process_function_definition,
	'function_expression': Generator.process_function_expression,
	'\n': Generator.process_newline,
	'INDENT': Generator.process_indent,
	'DEDENT': Generator.process_dedent,
	'variable': Generator.process_variable,
	'iteration_statement': Generator.process_iteration_statement,
	'assignment_expression': Generator.process_assignment_expression,
	'true': Generator.process_true,
	'false': Generator.process_false
}
