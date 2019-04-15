import java.io.*;
import java.lang.Thread;
import java.util.*;

//Main script the runs the restaurant simulation. This program creates the threads for diners and cooks and ends when all the threads have exited.

public class Main {

	//Priority queues for all the resources. For instance, Diners get priority based on arrival time.
	public static Queue<Diner> diner_q;
	public static Queue<Cook> machine_q;
	public static Queue<Cook> avail_cook_q;
	public static Queue<Cook> busy_cook_q;
	public static Queue<Order> order_q;
	public static ArrayList<Table> tables = new ArrayList<Table>();
	public static Machine machine = new Machine(true,true,true);
	public static int time = 0;


	//Custom compare functions for priority-queues.
	public static Comparator<Diner> d_crit = new Comparator<Diner>(){
		public int compare(Diner d1, Diner d2){
			return (d1.time - d2.time);
		}
	};

	public static Comparator<Cook> c_crit = new Comparator<Cook>(){
		public int compare(Cook c1, Cook c2){
			return (c1.time - c2.time);
		}
	};

	public static Comparator<Order> o_crit = new Comparator<Order>(){
		public int compare(Order o1, Order o2){
			return (o1.time - o2.time);
		}
	};

	//The main function.
	public static void main(String[] args) {
    		System.out.println("Enter the input");
	    	Scanner input = new Scanner(System.in);
		
		//Taking Input
		int diner_num = input.nextInt();
		int table_num = input.nextInt();
		int cook_num = input.nextInt();
		ArrayList<Integer> in_data  = new ArrayList<Integer>();
		for(int i=0;i<diner_num;i++){
			for(int j=0;j<4;j++){
				in_data.add(input.nextInt());
			}
		}

		
		System.out.println("diners = "+diner_num+", tables = "+table_num+", cooks = "+cook_num);


		Main main = new Main();
		//Diner, Order and Cook queues instantiation.
		avail_cook_q = new PriorityQueue<Cook>(cook_num, c_crit);
		busy_cook_q = new PriorityQueue<Cook>(cook_num, c_crit);
		order_q = new PriorityQueue<Order>(diner_num, o_crit);
		diner_q = new PriorityQueue<Diner>(diner_num, d_crit);
		machine_q = new PriorityQueue<Cook>(cook_num,c_crit);

		//Table generated.
		for(int i=0;i<table_num;i++){
			tables.add(new Table(i+1));
		}

		//Live Cooks (threads) generated.
		for(int i=0;i<cook_num;i++){
			Cook cook = new Cook(i+1,main);
			cook.start();
			avail_cook_q.add(cook);
		}


		//Diner threads generated.
		for (int i=0;i<diner_num;i++){
			diner_q.add(new Diner(i+1,in_data.subList(4*i,4*i+4),main));
		}

		//Sending in the diners as they arrive.
		for(int i=0;i<diner_num;i++){
			Diner diner = diner_q.poll();
			if(diner.time>120){
				System.out.println("Diner "+diner.dinerid+" arived after closing time "+diner.time);
			}
			else{
				diner.start();
			}
			try{
				Thread.sleep(50); //Helps synchronizing the threads. This can be replaced by join on all the diner threads.
			}
			catch(Exception e){
				System.out.println("Exception");
			}
		}
		
		System.out.println("Last diner leaves at "+time);
		System.exit(1);
  }
}
