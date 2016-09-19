# Overall Design

The File is initially indexed; then the lines are read by directly moving to a related offset and reading from the file.

## Indexing 
The file is divided into blocks, and the offset of the blocks start are recorded in an array. 
The no of blocks is determined based on the no of lines in the file and the amount of server memory(Server memory is typically considered as 25% of the total free memory)
A Blocks's offset is stored in the array where index = lineNumber/total no of blocks 
As The number of lines between two blocks increases the performance decreases.
The file is read twice during indexing once to get the number of lines to determine the blocks and once again to record the offsets.

## Reading a line
When a line number is given, the line number is checked if it is more than the total number of lines. The line's nearest offset is determined, and then the file pointer is moved to that offset then the lines are read one by one till the line is reached.

## On Receiving a request
Once the RestAPI receives a request, a task is created to read the line, and this task is then submitted to the executor queue and a thread picks it up and runs the task. Once the task is completed, the Response object is created, and the result is returned.




