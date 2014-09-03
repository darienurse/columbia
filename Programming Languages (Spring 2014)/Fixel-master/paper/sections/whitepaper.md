# White Paper
## Introduction

Over the last few years, businesses and technology centered on social media have experienced extraordinary growth. Sharing articles, videos, and personal information has never been easier. Photo-sharing, in particular, has become popular due to the proliferation of smartphones, tablets, and other smart devices on the market. This function is typically provided through both websites and applications that facilitate the upload and display of images. Social networking services like Instagram, Twitter, Pinterest, and Snapchat have capitalized on this trend by provide their users with new and interesting ways to share photos and other media.

With the growth of photo sharing and social media in mind, our group has decided to create a language that would facilitate and enhance the online photo sharing experience. Fixel is an easy-to-learn, simple to use programming language for editing images. It is targeted towards individuals who often share information through social media and offers them more control over their photo editing experience than standard image sharing applications. The syntax is structured to resemble that of posts on social networking services, leveraging the use of hashtags (`#`) and at symbol (`@`). This syntax will help those who are familiar with social networking services easily adapt to Fixel.

Fixel offers various tools for image editing including, but not limited to, cropping, filtering, scaling, and blurring. These tools are typically complex and may require high-level mathematics to implement. Fixel’s built in functions abstracts these tools such that individuals with absolutely no programming experience can uses them. To maintain simplicity and optimum portability, Fixel uses Python as its runtime environment. Python can run on most modern operating systems and provides a wide variety of image editing tools from the Python Imaging Library (PIL).

The remainder of this whitepaper is organized as followed: Related Work is a summary of the Python programming language and its role in developing Fixel. Goals enumerates a list of goals Fixel hopes to achieve. Architecture outlines the technical, backend features of Fixel. Finally, Features describes the various image editing features that Fixel will implement.

## Related Work

Most modern programming languages already have external libraries to simplify image processing functionality, focusing on image filtering and manipulation. While these external libraries and frameworks facilitate similar image filter construction, the resulting implementations are exceedingly complex and cater more to seasoned programmers with a significant image processing background.

Python specifically has a robust and powerful Python Imaging Library (PIL), which provides functionality from image canvas cropping, aspect-ratio fitting, and file format conversion to complex filter constructions. These libraries can be very useful for users seeking to have very fine control over how their images are edited, however a background in signal processing is required to fully harness the capabilities of these libraries. 

Similarly, Processing, and its sister language Processing.js, are languages built on top of Java and JavaScript, respectively, to facilitate visually-based programming projects for non-programmers or novice programmers. While Processing is a more user-friendly environment for constructing simple image-processing filters, it has grown to become a more robust and powerful development environment which is not as conducive to an introductory understanding of programming. It also requires a background in signal processing to construct image filters.

## Goals of Fixel
Fixel will be a user friendly language that offers its users the opportunity to create a customizable experience in an environment that mimics one they are already familiar and comfortable with.

### User Friendly
One of the most important goals to be pursued in the construction of this language is that it will be intuitive for the user.  It is assumed that a large portion of the target audience may have limited programming experience and the aim of Fixel is that they will be able to fully utilize it with a minimal learning curve.  Since the goal of Fixel is to bring complex photo-editing abilities to people who do not necessarily have a programming background, Fixel will have an easy-to-learn syntax.

### Mimic Syntax of Social Media
The syntax of Fixel will mimic that of social media and currently existing photo modification tools, such as Instagram and Twitter.  This will serve multiple purposes.  The first is that the user will hopefully feel more at ease using this new tool.  Secondly, this will provide continuity between Fixel and currently existing options.  Finally, it will allow users inexperienced with programming a more entertaining and accessible experience since their programs will not look like conventional code.

### Customizable Experience
By offering users increased photo alteration options, Fixel aims to offer increased autonomy over the picture sharing experience.  Fixel allows for increased originality since users will not be hindered by predetermined filters and effects.  As a result, it will be possible to create attractive and increasingly complex images to be shared.  Fixel  gives the user increased freedom without requiring extensive knowledge of image editing libraries thereby increasing the opportunity for creativity and artistry.

### Dynamic Typing
Another important user-friendly feature of Fixel is that it is dynamically typed. Dynamic typing will allow users to easily create powerful and compact programs without writing the tedious boilerplate required of statically typed languages. Beginner programmers can focus on what they want their program to do without having to worry about defining complex types to do it. A dynamic language will simplify the process for both new and experienced programmers. Beginners can avoid lower level details of reference type, casting and polymorphism. Experts can obtain the powerful effects of typing features such as polymorphism without having to dig into contracts and interfaces set up in static languages.


## Architecture
### Portable
An easy-to-use language is still not accessible if it is limited to a specific environment and requires a complex and tedious setup. That is why we chose to use Python as our runtime environment. Python was designed to be highly extensible, support a variety of programming paradigms, and be able to run on any major operating system. Additionally, a variety of Python implementations exist in addition to the standard compiler and VM that allow Python to be run on different platforms such as the JVM and .NET. This versatility and variety makes Python not only accessible on a number of platforms, but also highly integratable in many different types of projects. We will be taking advantage of this by running Fixel programs in Python and writing our translator in Python. This will allow deployment and/or development anywhere that Python can be run.

### Robust
In order to increase ease of use without decreasing capability, our language will abstract away as much of the challenging/tedious parts of programming as possible. This will include simplification and/or internal handling of errors, stringing together function calls and determining parameters from programmer’s input and assisting with control flow such as loops and switches. This abstraction requires our translator to intelligently handle situations that which we wish to hide from the programmer.

## Features
Fixel is constructed to offer maximum customization for those interested in image editing and enhancement. Using a simple syntax, programmers are able to automate a number of edits to their photos using built-in functions. This process is intuitive, and users are awarded the flexibility to define their own set of inputs to customize the result of each transformation. The functions available to Fixel programmers are:

### Blur
Using an existing library’s set of Fourier transforms, Fixel allows for the blurring of edges. Alternatively, if the user desires, they may select a center point (using its x and y pixel coordinates) and have the system blur out the areas surrounding it.

### Grayscale
A simple bit-by-bit translation can convert an image with RGB hex values to its corresponding grayscale version. The user can also define the level of “tint” applied to the pixels (from a range zero to one), if they wish to move the image towards being grayscale, but not remove color entirely.

###Invert
This feature has no inputs. It simply takes the user’s test image and returns a negative version of the image.

###Overlay
The overlay feature is predicated on the user having some degree of familiarity with RGB color schemes and the basic principles behind color hex values. They are able to use the overlay command to manipulate the hue and saturation of the image by manually entering the offset for these two values.

###Brightness and Contrast
Users may specify how they’d like change the brightness and contrast of their images with percentages (for example, changing the brightness to be 125\% would increase it by 25\%).

###Border
The user can select from a collection of pre-configured border styles for their images and define the thickness of these borders. Some borders are more ornate than others.

###Crop
By providing a set of coordinates the user would like to retain, they can crop off the remainder of the image and keep only the part that falls within the predefined space. The user has the option of cropping to a rectangle, a square, a circle, or an ellipsoid, all of which are configured using four coordinates.

###Scale
The scale function allows users to change the size of the image. They may do so either by providing a new final size for the image (and have the pixels stretch to fit this new size), or they can retain the current images proportions and specify a percentage increase or decrease in size.

###Collage
Fixel is built to support a series of pre-defined collage “types” that the user can select and then customize using their images. This is done by understanding the collage configurations and assembling arrays filled with images in the order in which they expect them to appear in the configuration.

###Rotate
This command allows the user to rotate the image any number of degrees they wish. If the resulting image is not a rectangle, white space will be added to ensure that it is.

###Caption
By defining the text of a caption, the user can use this function to insert text below their image or collage. They may also specify the font size, type, style, and weight of the caption using properties borrowed from CSS syntax.
