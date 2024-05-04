# Definition Extractor

Test task for the "Natural Language Code View" internship.

## Usage

The CLI tool takes the only argument: the path to a Kotlin file. 
You can try running the tool against Example.kt by simply clicking "Run" in IntelliJ.

## Implementation

Code is parsed with [kotlin-grammar-tools.](https://github.com/Kotlin/grammar-tools) I extended the parse tree class with 
several extra methods for searching in trees.

Unit tests are implemented.

## Challenges

`kotlin-grammar-tools` doesn't keep the source code parts in parse results. 
I had to concatenate the tokens of a function to restore its source code. 
This method doesn't preserve formatting; however, it would be enough to
process the code with LLM. 

If the original formatting was important, the solution would be to rewrite the
`Parser` library from `kotlin-grammar-tools` for storing original code in trees.
