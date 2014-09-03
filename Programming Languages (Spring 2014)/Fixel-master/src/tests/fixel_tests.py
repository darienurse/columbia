import difflib
from unittest import TestCase
from translator import translator
from translator import post_process
import test_strings


class FixelTests(TestCase):
	def oneliner_test(self):
		self.run_fixel_on_input(test_strings.oneliner_source, test_strings.oneliner_python)

	def complex_test(self):
		self.run_fixel_on_input(test_strings.complex_source, test_strings.complex_python)

	def big_test(self):
		self.run_fixel_on_input(test_strings.big_source, test_strings.big_python)
	
	def run_fixel_on_input(self, source, expected_python):
		fixel_output = translator.translate(source)

		# post process is too simple to test, just call to get proper output
		expected_python_post_process = post_process.create_program(expected_python[0], expected_python[1])

		d = difflib.Differ()
		diff = d.compare(fixel_output.splitlines(), expected_python_post_process.splitlines())
		diff_string = '\n'.join(diff)
		self.assertEqual(fixel_output, expected_python_post_process, 'diff\n' + diff_string)