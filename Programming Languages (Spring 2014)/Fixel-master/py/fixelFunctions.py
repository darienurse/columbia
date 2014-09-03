import os, math, sys
from PIL import Image
from PIL import ImageFilter
from PIL import ImageEnhance
from PIL import ImageOps
from PIL import ImageFont
from PIL import ImageDraw
from PIL import ImageColor
import runtime_classes

# function to save the final output images
def saveImage(indata,filetype):
	outfile = os.path.splitext(indata.name)[0] + "-fixel.jpg"
	indata.image_data.convert('RGB').save(outfile,filetype)

# function that takes an image object as input and turns it gray
def grayscale(indata):
	im = indata.image_data.convert("L").convert("RGB")
	indata.set_image_data(im)

# function that resizes an image, keeping its aspect ratio, according to the scale ratio provided
def scale(indata,ratio):
	im = indata.image_data
	try:
		size = im.size[0]*ratio,im.size[1]*ratio
	except:
		print "\nNo ratio has been entered to scale the image '"+indata.name+"' to. Please add a ratio to your call to the Fixel scale function.\n"
		sys.exit(0)
	im = im.resize(size, Image.ANTIALIAS)
	indata.set_image_data(im)

# function that stretches an image to meet the desired height and width
def stretch(indata,newWidth,newHeight):
	im = indata.image_data
	if (isinstance(newWidth,int) == False | isinstance(newHeight,int)):
		print "\nEither the height or the width you specified in your call to the #stretch function is not an integer. Please make sure both are integers and try again.\n"
		sys.exit(0)
	newIm = im.resize(size, Image.ANTIALIAS)
	indata.set_image_data(newIm)

# function that rotates an image at a predefined angle
def rotate(indata,angle):
	if (isinstance(angle,int) == False):
		print "\nThe angle you entered is not valid. It must be an integer from -360 to 360.\n"
		sys.exit(0)
	im = indata.image_data.convert('RGBA')
	rot = im.rotate(angle, expand=1)
	white = Image.new('RGBA', rot.size, (255,)*4)
	im = Image.composite(rot, white, rot)
	im.convert(indata.image_data.mode)
	indata.set_image_data(im)

# function that colorizes an image using a color and opacity (scaled from 0-1) provided	
def overlay(indata,color,opacity):
	if (isinstance(opacity,int) == False):
		print "\nThe opacity you entered for the #overlay function is not valid. It must be an integer from 0 to 1.\n"
		sys.exit(0)
	opacity = int(255*float(opacity)/100)	
	rgb = list(color.rgb) + [opacity]
	rgb = tuple(rgb)
	im = indata.image_data
	overlayColor = Image.new(mode='RGBA',size=im.size,color=rgb)
	im.paste(overlayColor, [0,0,im.size[0],im.size[1]], overlayColor)
	indata.set_image_data(im)

# blur function that adds a fast blur of degree specified to the image
def blur(indata,degree):
	if (isinstance(degree,int) == False):
		print "\nThe degree you entered for the #blur function is not valid. It must be an integer.\n"
		sys.exit(0)
	if (degree>10):
		degree = 10
	elif (degree<0):
		degree=0
	im = indata.image_data.filter(fixelGaussianBlur(radius=degree))
	indata.set_image_data(im)

# function that sharpens the features of an image to a specified degree
def sharpen(indata,degree):
	if (isinstance(degree,int) == False):
		print "\nThe degree you entered for the #sharpen function is not valid. It must be an integer.\n"
		sys.exit(0)
	if (degree>10):
		degree = 10
	elif (degree<0):
		degree=0
	im = ImageEnhance.Sharpness(indata.image_data).enhance(degree)
	indata.set_image_data(im)

# function that changes the brightness of an image according to an inputted degree
def brighten(indata,degree):
	if (isinstance(degree,int) == False):
		print "\nThe degree you entered for the #brighten function is is not valid. It must be an integer.\n"
		sys.exit(0)
	degree=(degree/10)+1
	if (degree>2):
		degree = 2
	elif (degree<0):
		degree=0
	im = ImageEnhance.Brightness(indata.image_data).enhance(degree)
	indata.set_image_data(im)

# same as the brighten function, except with contrast
def contrast(indata,degree):
	if (isinstance(degree,int) == False):
		print "\nThe degree you entered for the #contrast function is is not valid. It must be an integer.\n"
		sys.exit(0)
	degree=(degree/10)+1
	if (degree>2):
		degree = 2
	elif (degree<0):
		degree=0
	im = ImageEnhance.Contrast(indata.image_data).enhance(2)
	indata.set_image_data(im)

# function that adds a border of size and color determined by the user
def border(indata,border,color):
	if (isinstance(degree,int) == False):
		print "\nThe border you entered for the #border function is is not valid. It must be an integer.\n"
		sys.exit(0)
	try:
		theColor=color.rgb
	except:
		print "\nFixel requires that you use a valid color object for this function. Define it by: @variableName = color(colorValue).\n"
		sys.exit(0)
	im = ImageOps.expand(indata.image_data,border=border,color=color.rgb)
	indata.set_image_data(im)

# function that crops the image at the coordinates specified
def cropit(indata, left, top, right, bottom):
	if (isinstance(left,int) == False | isinstance(top,int) == False | isinstance(right,int) == False | isinstance(bottom,int) == False):
		print "\nThe coordinate values must all be integers representing pixel values that fall within the bounds of the image.\n"
		sys.exit(0)
	im = indata.image_data.crop((left, top, right, bottom))
	indata.set_image_data(im)

# function that adds a caption to an image	
def caption(indata,text):
	font_file_path = os.path.join(os.path.dirname(__file__), "HelveticaNeue.ttc")
	font = ImageFont.truetype(font_file_path, 100)
	im = ImageDraw.Draw(indata.image_data)
	im.text((10, 10), text, fill="#ff0000", font=font)
	del im

# functions that generates a color object (as defined in runtime_classes) according to an inputted rgb, hex, or text value	
def color(*argv):
	if type(argv[0]) is str:
		rgb = ImageColor.getrgb(argv[0])
	else:
		rgb = argv
	return runtime_classes.Color(rgb)

# function that stitches together an array of images
def collage(indata,images,w,h):
	im = Image.new("RGB", (w, h), "white")
	count = len(images)
	heightMax = int(math.floor(math.sqrt(count)))
	widthMax = int(math.ceil(count/heightMax))
	widthCount = 0
	heightCount = 0
	for image in images:
		im2 = image[0]
		size = (w/widthMax),(h/heightMax)
		im2 = im2.resize(size, Image.ANTIALIAS)
		im.paste(im2,(widthCount*(w/widthMax),heightCount*(h/heightMax)))
		widthCount = widthCount+1
		if widthCount == widthMax:
			widthCount = 0
			heightCount = heightCount+1
	indata[0] = im

# new class that creates a gaussian blur filter based on a specified degree
class fixelGaussianBlur(ImageFilter.Filter):
    name = "GaussianBlur"
    
    def __init__(self, radius=2):
        self.radius = radius
    
    def filter(self, image):
        return image.gaussian_blur(self.radius)