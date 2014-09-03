import os


def create_program(main_string, functions_string):
	path_to_fixel_src = os.path.join(os.path.dirname(__file__), '..')
	return header + repr(path_to_fixel_src) + '))\n' + import_fixel_functions + functions_string + main_pre_fixel + main_string + main_post_fixel


header = '''\
\'\'\'
  ______   __     __  __     ______     __
 /\  ___\ /\ \   /\_\_\_\   /\  ___\   /\ \       
 \ \  __\ \ \ \  \/_/\_\/_  \ \  __\   \ \ \____  
  \ \_\    \ \_\   /\_\/\_\  \ \_____\  \ \_____\ 
   \/_/     \/_/   \/_/\/_/   \/_____/   \/_____/ 
                                                     
\'\'\'

import os
import sys

# add fixel top to path so fixel functions can be imported
sys.path.append(os.path.abspath('''

import_fixel_functions = '''
from runtime import fixelFunctions
from runtime import runtime_classes

'''


main_pre_fixel = '''
inputImages = sys.argv[1:]
if len(inputImages) < 1:
	print "\\nNo images were used as arguments. Please append the paths to the images you'd like to use as arguments and run this Fixel program again.\\n"
	sys.exit(0)
inputImageCount = 0
Namespace = type('Namespace', (object,), {'images': []})  # cleaner than having to declare a class
ns = Namespace()

# create variables for each image
for currentImage in inputImages:
	image = runtime_classes.Image(currentImage)
	setattr(ns, "image"+str(inputImageCount), image)
	ns.images.append(image)
	inputImageCount += 1

'''

main_post_fixel = '''
for image in ns.images:
	fixelFunctions.saveImage(image, "JPEG")
'''
