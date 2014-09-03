from unittest import TestCase
from runtime import fixelFunctions
from runtime import runtime_classes


test_file_path = 'Walter_White.jpg'


class RuntimeTests(TestCase):
	def bif_tests(self):
		test_image = runtime_classes.Image(test_file_path)
		fixelFunctions.grayscale(test_image)
		fixelFunctions.scale(test_image, 2)
		fixelFunctions.stretch(test_image, 300, 300)
		fixelFunctions.rotate(test_image, 90)
		fixelFunctions.overlay(test_image,fixelFunctions.color("green"), 50)
		fixelFunctions.blur(test_image, 10)
		fixelFunctions.sharpen(test_image, 5)
		fixelFunctions.brighten(test_image, 20)
		fixelFunctions.contrast(test_image, 25)
		fixelFunctions.cropit(test_image, 20, 20, 280, 280)
		fixelFunctions.border(test_image, 10, fixelFunctions.color("fuchsia"))
		fixelFunctions.caption(test_image, "cool")
		second_image = runtime_classes.Image('Capture.jpg')
		fixelFunctions.collage(test_image, [test_image, second_image], 1500, 1500)

		fixelFunctions.saveImage(test_image, 'JPEG')

	def image_tests(self):
		test_image = runtime_classes.Image(test_file_path)
		color = fixelFunctions.color("#ff391a")
		test_image[1, 1] = color
		self.assertEqual(test_image[1, 1].rgb, color.rgb)
		fixelFunctions.stretch(test_image, 300, 200)
		self.assertEqual(test_image.width, 300)
		self.assertEqual(test_image.height, 200)

	def color_tests(self):
		color1 = runtime_classes.Color((0,1,2))
		self.assertEqual(color1[0], 0)
		self.assertEqual(color1[1], 1)
		self.assertEqual(color1[2], 2)

		color2 = color1 + 10
		self.assertEqual(color2[0], 10)
		self.assertEqual(color2[1], 11)
		self.assertEqual(color2[2], 12)

		color3 = color1 * color2
		self.assertEqual(color3[0], 0)
		self.assertEqual(color3[1], 11)
		self.assertEqual(color3[2], 24)

		self.assertEqual(color1.r, color1[0])
		self.assertEqual(color1.g, color1[1])
		self.assertEqual(color1.b, color1[2])

	def pixel_test(self):
		test_image = runtime_classes.Image(test_file_path)
		p = runtime_classes.Pixel(test_image)
		p.x = 45
		p.y = 101
		self.assertEqual(p.color.rgb, test_image[45, 101].rgb)
