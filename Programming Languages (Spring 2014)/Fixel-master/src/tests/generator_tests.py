from unittest import TestCase
from translator import generator
import test_strings
import test_utils


class GeneratorTests(TestCase):
	def oneliner_test(self):
		self.run_generator_on_tree(test_strings.oneliner_tree, test_strings.oneliner_python)
	
	def indent_test(self):
		self.run_generator_on_tree(test_strings.indent_tree, test_strings.indent_python)
	
	def function_def_test(self):
		self.run_generator_on_tree(test_strings.function_def_tree, test_strings.function_def_python)

	def or_and_test(self):
		self.run_generator_on_tree(test_strings.or_and_tree, test_strings.or_and_python)
		
	def not_test(self):
		self.run_generator_on_tree(test_strings.not_tree, test_strings.not_python)

	def selection_if_test(self):
		self.run_generator_on_tree(test_strings.selection_if_tree, test_strings.selection_if_python)

	def selection_ifelse_test(self):
		self.run_generator_on_tree(test_strings.selection_ifelse_tree, test_strings.selection_ifelse_python)

	def equality_notequal_test(self):
		self.run_generator_on_tree(test_strings.equality_notequal_tree, test_strings.equality_notequal_python)

	def iteration_for_test(self):
		self.run_generator_on_tree(test_strings.iteration_for_tree, test_strings.iteration_for_python)

	def multiplicative_test(self):
		self.run_generator_on_tree(test_strings.multiplicative_tree, test_strings.multiplicative_python)

	def pixel_test(self):
		self.run_generator_on_tree(test_strings.pixel_tree, test_strings.pixel_python)

	def forp_test(self):
		self.run_generator_on_tree(test_strings.forp_tree, test_strings.forp_python)

	def run_generator_on_tree(self, tree_string, expected_python):
		tree_string_no_ws = tree_string.replace(' ', '')
		tree, _ = test_utils.string_to_tree(tree_string_no_ws, 0)
		gen = generator.Generator(tree)
		main_string, functions_string = gen.get_strings()
		self.assertEqual(expected_python[0], main_string)
		self.assertEqual(expected_python[1], functions_string)
