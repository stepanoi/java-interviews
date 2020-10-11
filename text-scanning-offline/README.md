# Digital Number Scanning Application

Suppose there are nine digit digits represented in com.digitalnumber format.
It looks like the following example ("." denotes a whitespace for your readability):

```text
...._.._....._.._.._.._.._.
..|._|._||_||_.|_...||_||_|
..||_.._|..|._||_|..||_|._|
```

Each com.digitalnumber digit is three lines long, and each line is exactly 27 characters wide.
A digit has nine digits, from 0-9.
The digits are composed of underscores "_" and pipes "|".
An empty line separates multiple digits.
An com.digitalnumber.digit.scanner.input file could contain up to 400 entries.

## Requirements
* Each digit should be parsed and written to the console as actual digit (i.e.: 123456789 for the above com.digitalnumber digit example), line by line.
* Unfortunately, the com.digitalnumber.digit.scanner.classifier is not perfect and sometimes provides illegal characters that do not transform into a digit.
If the digit cannot be read, replace the illegal characters with an "?" and append "ILL" to the output.

Please make it a maven project. Testing your solution is important part of the task - we will pay special attention to the coverage and corner cases considered.
Also bear in mind that the implementation will set the stage for our live session to follow - as part of this session we will ask you to evolve your solution to support new requirement.
