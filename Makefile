start: halftone/Halftone.run

clean:
	# find . -name "*.class" -type f -delete

%.run: %.class
	java $*

%.class: %.java
	javac $<

.PHONY: start clean
