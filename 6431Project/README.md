This project runs a restaurant simulation. Diners enter the restaurant at different times and give orders to cooks provided there are free tables and free cooks. The cooks cook food if the machines (burger, fries and coke) are available.

The code files contain details about what each script is doing.

Running the code.

1. Enter the code directory. $ cd /code
2. The code has been compiled. But it can again be compiled by running javac on Main.java. $ javac Main.java
3. Run the main class. $ java Main. Copy paste the input into after "Enter the input" shows up.

example:
Enter the input
2
2
1
1 1 1 1
2 1 1 1
diners = 2, tables = 2, cooks = 1
Diner 1 entered at 1
time: 1 Diner 1 gets table 1
time: 1 diner 1 orders 1 burgers, 1 coke and 1 fries
time: 1 cook 1 takes order from diner 1
Diner 2 entered at 2
time: 2 Diner 2 gets table 2
time: 2 diner 2 orders 1 burgers, 1 coke and 1 fries
time: 1 cook 1 started using burger machine
time: 6 cook 1 stopped using burger machine
times: 6 cook 1 started using coke machine
time: 7 cook 1 stopped using coke machine
time: 7 cook 1 started using fries machine
time: 12 cook 1 stopped using fries machine
time: 12 cook 1 finished order for diner 1
time: 12 diner 1 started eating
time: 42 diner 1 leaves.
time: 12 cook 1 served the food to diner 1
time: 12 cook 1 takes order from diner 2
time: 12 cook 1 started using burger machine
time: 17 cook 1 stopped using burger machine
times: 17 cook 1 started using coke machine
time: 18 cook 1 stopped using coke machine
time: 18 cook 1 started using fries machine
time: 23 cook 1 stopped using fries machine
time: 23 cook 1 finished order for diner 2
time: 23 cook 1 served the food to diner 2
time: 23 diner 2 started eating
time: 53 diner 2 leaves.
Last diner leaves at 53

