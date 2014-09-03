#Development and Runtime Environment

This compiler was developed using both Windows and MacOS.  Git hosted on Bitbucket was used for version control as well as for proper source code sharing.  We used Python 2.7.4 to write and test our code.  

Since python does not require a conventional Makefile, we instead we wrote a bash file in order to run the code.  This file takes numerous arguments, the first is the fixel file with the extension .fxl and there are the images to be passed in as fixel arguments.  The `fixel` file first creates the translator to create the executable python file.  Then the python file is run on the image arguments passed in.

This is run as follows:
`fixel program_name.fxl [image_arguments]`

For windows computers it should be run as follows:
`fixel.py program_name.fxl [image_arguments]`

Fixel:
