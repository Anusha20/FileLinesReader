# FileLinesReader
## Prerequisite 
Please install and configure Maven 3.x 
## To run
use the following command in the home directory of the project
mvn exec:java -Dexec.args=<path of input file>
### example
mvn exec:java -Dexec.args="/usr/Name/Docs/InputFile"

## Functional Requirements

This is a server that serves individual lines of an immutable text file over the network to clients using the following simple REST API:

- GET /lines/<line index>
- Returns an HTTP status of 200 and the text of the requested line or an HTTP 413 status if the requested line is beyond the end of the file.
- Your server should support multiple simultaneous clients.

## Perf requirement
- The system should perform well for small and large files.

- The system should perform well as the number of GET requests per unit time increases.

- You may pre-process the text file in any way that you wish so long as the server behaves correctly.

###The text file will have the following properties:

- Each line is terminated with a newline ("\n").
- Any given line will fit into memory.
- The line is valid ASCII (e.g. not Unicode).
