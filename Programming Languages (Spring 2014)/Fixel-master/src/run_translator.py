import argparse
import os
import sys
from translator import translator

arg_parser = argparse.ArgumentParser()
arg_parser.add_argument('-v', '--verbose', help='prints every intermediate step in translation',
						action='store_true', default=False)
arg_parser.add_argument('-f', '--fixel-file', type=argparse.FileType('r'))
namespace = arg_parser.parse_args()

if namespace.fixel_file:
	path, name = os.path.split(namespace.fixel_file.name)

	source_string = namespace.fixel_file.read()
	namespace.fixel_file.close()

	pwd = os.getcwd()
	script_dir = os.path.dirname(__file__)  # directory this script is in
	os.chdir(script_dir)  # change directory so parse outputs are in right place
	try:
		result = translator.translate(source_string, namespace.verbose)
		os.chdir(pwd)  # go back to original pwd before writing out file
		outname = os.path.join(path, name.split('.')[0] + '.py')
		f = open(outname, 'w')
		f.write(result)
		f.close()
	except translator.parser.ParsingError as e:
		print e.value
		sys.exit(1)

else:
	try:
		result = translator.translate(verbose=namespace.verbose)
	except translator.parser.ParsingError as e:
		print e.value
		sys.exit(1)
