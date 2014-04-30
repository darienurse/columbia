X10C=${X10_HOME}/bin/x10c++
FLAGS=-nooutput -NO_CHECKS -VERBOSE_CHECKS -EXPERIMENTAL

Nqueens: Main.x10 Solver.x10
	$(X10C) $(FLAGS) -o $@ $^

Hello: HelloWorld.x10
	$(X10C) $(FLAGS) -o $@ $^

test: Nqueens
	./Nqueens configurations

clean:
	rm -f Hello Nqueens *.h *.out *.err *.log *~ *.cc