X10C=${X10_HOME}/bin/x10c++
#FLAGS=-O -NO_CHECKS
FLAGS=

Hash: Main.x10 Hash.x10
	$(X10C) $(FLAGS) -o $@ $^

test: Hash
	./Hash

clean:
	rm -f Hash *.h *.out *.err *.log *~ *.cc