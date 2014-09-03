from unittest import TestCase
from translator import lexer
import test_strings


class LexerTests(TestCase):
	def oneliner_test(self):
		self.run_lexer_on_source(test_strings.oneliner_source, test_strings.oneliner_tokens)

	def indent_test(self):
		self.run_lexer_on_source(test_strings.indent_source, test_strings.indent_tokens)

	def function_def_test(self):
		self.run_lexer_on_source(test_strings.function_def_source, test_strings.function_def_tokens)

	def or_and_test(self):
		self.run_lexer_on_source(test_strings.or_and_source, test_strings.or_and_tokens)

	def not_test(self):
		self.run_lexer_on_source(test_strings.not_source, test_strings.not_tokens)

	def selection_if_test(self):
		self.run_lexer_on_source(test_strings.selection_if_source, test_strings.selection_if_tokens)

	def selection_ifelse_test(self):
		self.run_lexer_on_source(test_strings.selection_ifelse_source, test_strings.selection_ifelse_tokens)

	def iteration_for_test(self):
		self.run_lexer_on_source(test_strings.iteration_for_source, test_strings.iteraton_for_tokens)

	def multiplicative_test(self):
		self.run_lexer_on_source(test_strings.multiplicative_source, test_strings.multiplicative_tokens)

	def equality_notequal_test(self):
		self.run_lexer_on_source(test_strings.equality_notequal_source, test_strings.equality_notequal_tokens)

	def pixel_test(self):
		self.run_lexer_on_source(test_strings.pixel_source, test_strings.pixel_tokens)

	def forp_test(self):
		self.run_lexer_on_source(test_strings.forp_source, test_strings.forp_tokens)

	def run_lexer_on_source(self, source, expected_tokens):
		my_lex = lexer.get_lex()
		my_lex.input(source)

		for expected_token in expected_tokens:
			actual_token = my_lex.token()
			self.assertEqual(expected_token, (actual_token.type, actual_token.value))

		self.assertIsNone(my_lex.token())  # make sure token stream was exhausted
