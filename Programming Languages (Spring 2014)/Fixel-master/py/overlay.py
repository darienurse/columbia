# standard libraries to import
import os, sys, fixelFunctions

# create variables for the system to iterate through
outputImages = list(sys.argv)
del outputImages[0]
outputImageCount = 0

# create a variable containing the raw image data for the input images
for currentImage in outputImages:
	outputImages[outputImageCount] = fixelFunctions.imageData(currentImage)
	outputImageCount = outputImageCount+1

# begin the custom functions here
# perform any necessary transformations to the input images (as per the fixel script
outputImages[0] = fixelFunctions.overlay(outputImages[0],[255,0,0],10)

# output all images with "-fixel" appended to the end
inputImages = list(sys.argv)
del inputImages[0]
inputImageCount = 0
for currentImage in inputImages:
	fixelFunctions.saveImage(outputImages[inputImageCount],currentImage,"JPEG")
	inputImageCount = inputImageCount+1
	


