import difflib
from unittest import TestCase
import mock
from translator import parser
import test_utils
import test_strings


class ParserTests(TestCase):
	def test_test(self):
		self.assertTrue(True)

	def oneliner_test(self):
		self.run_parser_on_tokens(test_strings.oneliner_tokens, test_strings.oneliner_tree)

	def indent_test(self):
		self.run_parser_on_tokens(test_strings.indent_tokens, test_strings.indent_tree)

	def function_def_test(self):
		self.run_parser_on_tokens(test_strings.function_def_tokens, test_strings.function_def_tree)

	def or_and_test(self):
		self.run_parser_on_tokens(test_strings.or_and_tokens, test_strings.or_and_tree)

	def not_test(self):
		self.run_parser_on_tokens(test_strings.not_tokens, test_strings.not_tree)

	def selection_if_test(self):
		self.run_parser_on_tokens(test_strings.selection_if_tokens, test_strings.selection_if_tree)

	def selection_ifelse_test(self):
		self.run_parser_on_tokens(test_strings.selection_ifelse_tokens, test_strings.selection_ifelse_tree)

	def iteration_for_test(self):
		self.run_parser_on_tokens(test_strings.iteraton_for_tokens, test_strings.iteration_for_tree)

	def multiplicative_test(self):
		self.run_parser_on_tokens(test_strings.multiplicative_tokens, test_strings.multiplicative_tree)

	def equality_notequal_test(self):
		self.run_parser_on_tokens(test_strings.equality_notequal_tokens, test_strings.equality_notequal_tree)

	def pixel_test(self):
		self.run_parser_on_tokens(test_strings.pixel_tokens, test_strings.pixel_tree)

	def forp_test(self):
		self.run_parser_on_tokens(test_strings.forp_tokens, test_strings.forp_tree)

	def run_parser_on_tokens(self, tokens, expected_tree):
		mock_lex = mock.MagicMock()
		mock_tokens = []
		for token in tokens:
			mock_token = test_utils.get_mock_token(token[0], token[1])
			mock_tokens.append(mock_token)

		# pad iteration in case lookahead goes past end of stream
		mock_lex.token.side_effect = mock_tokens + [None, None]

		my_parser = parser.get_yacc()
		tree = my_parser.parse('', lexer=mock_lex)
		tree_string = test_utils.tree_to_string(tree)
		diff_string = self.get_diff(expected_tree, tree_string)
		self.assertEqual(expected_tree.replace(' ', ''), tree_string.replace(' ', ''), 'diff:\n' + diff_string)

	def get_diff(self, tree1, tree2):
		diff_gen = difflib.context_diff(tree1.split(' '), tree2.split(' '))
		diff_string = ''
		for diff in diff_gen:
			diff_string += diff
		return diff_string
