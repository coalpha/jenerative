classpath:=".;lib/*"

start: sketches/halftone/Halftone.run

build: sketches/halftone/Halftone.class

clean:
	# find . -name "*.class" -type f -delete

%.run: %.class always
	java -cp $(classpath) $*

%.class: %.java always
	javac -cp $(classpath) $<

always:

.PHONY: start always
