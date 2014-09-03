# Test Plan
The majority of the testing is broken up into unit tests that focus on checking the output each part of the translator produces given a variety of inputs. These unit tests are focused on the the lexer, the parser and the generator. Each test creates an instance of the given stage, feeds it an input, and compares the output it produces to an expected output. There are also some tests that run the entire translator on a source program, and check only the final output. This allows for more complex programs to be written, since the intermediate forms for these test cases don't have to be written down. Lastly, all of the built in functions and classes that Fixel programs use at runtime are tested. The built-in functions are too complex to make assertions about, as they manipulate image files, so they are simply run to make sure they don't raise any errors.

## Tools
The Python Coverage utility assisted in writing the test cases to make sure as much of the source code as possible was tested. The coverage tool would analyze the tests and report any lines of source code in the tested files that were not executed. This was particularly useful in determining if every grammar production was tested. `Mock` was used to isolate parts of code that depended on the behaviour of other sections of code to operate properly, and the tool `Nose` was used to run all of the tests.

## Challenges
One challenge that had to be overcome was how to express the inputs and expected outputs. The first and last forms of a program throughout translation (the Fixel source and Python source) were easily written with multiline strings, but the intermediate forms were a bit harder to write down. A list of tuples of strings made writing token streams fairly easy, but expressing syntax trees was not quite so simple. The method chosen was a string representation that recursively defines a tree node and all of it's subtrees. This format could be both parsed and generated programmatically, and could thus be used to express test cases. It was not very easy to read, however, and as such was impractical to write by hand even for programs that were just a few lines long. To solve this, a simple tool was written for the test suite that creates a tree string from a given Fixel source string. The validity of this output was then confirmed with a tool found at `http://ironcreek.net/phpsyntaxtree` which displays a graphical representation of a tree given in the string syntax.

Another challenge was presented when figuring out how to isolate the parser. The lexer and generator were easily run in isolation as they simply produce an output when given an input. The parser, however, requires a lexer object, from which it obtains an input token stream. The solution used was creating a mock lexer with the Mock tool that presents the same interface as a real lexer, but produces a predetermined token stream.

## Relevant Files
All of the tests files are in the tests directory within the src directory.

### test_strings.py
Many program representations are needed by multiple test files (e.g. a token stream will be used as both the expected output of a lexer test and as the input for a parser test), so every string used as input and/or expected output for tests are kept in this file.

### fixel_tests.py
Runs the entire translator on various fixel source progpams and compares the output to the expected Python source.

### lexer_tests.py
A collection of test cases for the lexer. Each test runs the lexer on a Fixel program and compares the output to the expected token stream.

### parser_tests.py
A collection of test cases for the parser. Each test runs the parser on a token stream and compares the output to the expected syntax tree.

### generator_tests.py
A collection of test cases for the generator. Each test runs the generator on a syntax tree and compares the output to the expected Python source.

### runtime_tests.py
Calls all of the built in functions on a test image to determine if they execute without raising an error. Testing the changes in the images was not practical given the time frame of this project, so no assertions are made about image manipulating functions during this test. Also tests the behavior and operations of the data types Fixel programs use at runtime.

### test_utils.py
Contains several methods that assist in writing and running tests, including creating trees from strings and vice versa.

## Continuous Integration Server
Fixel used a Raspberry Pi running the open source continuous integration server Jenkins. Jenkins was used as a second reference for running tests, and to collect statistics about the state of Fixel's tests over time. Whenever someone pushed the master branch to the remote on BitBucket, Jenkins would check out the commit pushed and run the tests. If a test failed, Jenkins would email the whole team informing them that the failure occurred and what caused it. This helped ensure that master stayed clean, and kept everyone informed about when it might be problematic to create a new branch from master. From all of these test runs, Jenkins collected data about how many tests were run, how many failed, and what percent of code is covered, and how this data changed over time.

![Test Results for each build run by Jenkins.](./img/jenkins_tests.png)

![Coverage over time.](./img/coverage.png)
