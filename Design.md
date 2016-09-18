# Overall Design

The File is intially indexed ,then the lines are read by directly moving to a related offset and reading from the file.

## Indexing 
THe file is divided into a number of blocks and the offset of the blocks start are recorded in an array. 
The no of blocks is determined based on the no of lines in the file and the amount of server memory(Server memory is typically considered as 25% of the total free memory)
A Blocks's offset is typically stored in the array where its index = lineNumber/total no of blocks 
As The number of lines between two blocks increase the performance decreases.
The file is read twice during indexing once to get the number of lines to determine the blocks and once again to record the offsets.

## Reading a line
When a line number is given , the line number is checked if it is more than the total number of lines. the the line's nearest offset is determined and then the file pointer is moved to that offset then the lines are read one by one till the line is reached.

## On Receving a request
Once the RestAPI receives a request the a task to read the line is created and  a task is submitted to the executor queue and a thread picks it up and runs the task. once the task is completed the Response object is created and the result is returned.




