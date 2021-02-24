# Simple Shell Interpreter

Shell behaviour emulator written in Kotlin, runs as a console application. It cannot recreate sessions
and does not support multiple ones, terminating input means termination.   


## Basic idea
Everything is split into several phases:   
* lexical analysis - lexes the user-provided input
* syntax analysis - uses 2 phases. First one is to traverse commands 
or assignments representations and actualisise, if possible, arguments.
* post-traverse - visit representations and build executable objects (aka commands) 
from the representations
* execution - executes the commands and providing the output   
  

## List of Commands 
* assignment: `a=123` - stores variable to the context 
* echo: `echo something` - outputs arguments with default space separation 
* cat: `cat FILE` - outputs file's contents 
* pwd: `pwd` - prints project's directory
* wc: `wc file` - outputs number of lines, words and characters from a given input
* exit: `exit` - terminates the working session
* external commands - if command is not found, the interpreter would try to invoke from a system 
* pipe feature - simply redirects output from one commands to the input of another 
## Use example
`echo 123 | wc`

1 	1 	3

`pwd`

E:\MSE\Software-Design\CLI

`cat gradle.properties`

 kotlin.code.style=official
 
 `a=exit`  
 `$a` - termination 
 