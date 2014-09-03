import os
import sys
import fixelFunctions, runtime_classes

inputImages = sys.argv[1:]
if len(inputImages) < 1:
	print "\nNo images were used as arguments. Please append the paths to the images you'd like to use as arguments and run this Fixel program again.\n"
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
	
fixelFunctions.rotate(ns.image0,'wo')

for image in ns.images:
	fixelFunctions.saveImage(image, "JPEG")