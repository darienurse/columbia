import os
import nose
import sys

path_to_tests = os.path.join(os.path.dirname(__file__), 'tests')  # obtain path to tests relative to this script
argv = list(sys.argv)
argv.insert(1, path_to_tests)
argv.insert(1, '-w')
nose.main(argv=argv)
