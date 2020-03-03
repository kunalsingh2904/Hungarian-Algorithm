# How to run:
	1. open Eclipse
	2. download and open in eclipse
	3. go to run->run configurations
	4. select TestProgram from Java application
	5. select Arguments 
	4. write array.txt in Program arguments // you can use other files as cost matrix. put that file in same location of array.txt and give that file name in Program arguments.
	5. click on run 

# Junit test
	1. I have created 3 test cases in src/JUintTest.java.
	2. open folder in eclipse and right click on JUnitTest.java and choose "Run as" --> "JUnit test".
	3. It will show how many test cases passes or fail.

# Output:
	1. I assume row of matrix are workers and columns are work.
	2. Output will be like:
		Worker(row) <---> Work(col) <---> Cost

		   1                 0             50
		   0                 1             20
		   3                 2             60
		   2                 3             10

		Total cost: 140  

	3. This show which worker has assigned which work. And Total cost.