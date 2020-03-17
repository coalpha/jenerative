start: sketches/halftone/Halftone.run

clean:
	# find . -name "*.class" -type f -delete

%.run: %.class always
	java $*

%.class: %.java always
	javac $<

always:

.PHONY: start always
