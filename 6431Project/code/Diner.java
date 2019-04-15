import java.util.*;
import java.io.*;

//Diner class. This thread class contains all the functions a diner is capable of, namely, giving order, eating food, and leaving the table (or restaurant).

public class Diner extends Thread{
	public Main Main;
	public Order order;
	private Table table;
	public int dinerid;
	public int time;
	
	public Diner(int dinerid, List<Integer> order,Main main){
		this.dinerid = dinerid;
		this.order = new Order(order.get(1),order.get(2),order.get(3), dinerid);
		this.order.time   =  order.get(0);
		Main = main;
		time = order.get(0);
	}

//Diner enters at this stage and waits for a free table.
	public void enter(){
		synchronized (Main.tables){
			try{
				System.out.println("Diner "+this.dinerid+" entered at "+time);
			
				while(Main.tables.isEmpty()){
					Main.tables.wait();
				}
				table = Main.tables.remove(0);
				table.time = Math.max(time,table.time);
				order.time = table.time;
				time = table.time;
				System.out.println("time: "+time+" Diner "+dinerid+" gets table "+table.id);
			}
			catch(Exception e){
				System.out.println("Exception");
			}
		}
	}

//Once the diner has a table, he/she gives order.
	public void _order(){
		synchronized (Main.order_q){
			System.out.println("time: "+time+" diner "+dinerid+" orders "+order.burgers+" burgers, "+order.cokes+" coke and "+order.fries+" fries");
		Main.order_q.add(order);
		Main.order_q.notify();
		}
	}
	
//The order is obtained here. This means the cooks is free to server other diners (done in cook thread).
	public void get(){
		synchronized(order){
			try{
				order.wait();
			}
			catch(Exception e){
				System.out.println("Exception");
			}
		}
		time = Math.max(time,order.time);
	}

//Diner eats for 30 time units.
	public void eat(){
		System.out.println("time: "+time+" diner "+dinerid+" started eating");
		time+=30;
	}

//Diner leaves the table, which can be assigned people waiting outside.
	public void exit(){
		synchronized (Main.tables){
			table.time = time;
			Main.tables.add(table);
			Main.tables.notify();
		}
		Main.time =  time;
	}

	public void run(){
		enter();
		_order();
		get();
		eat();
		exit();
		System.out.println("time: "+time+" diner "+dinerid+" leaves.");
	}
}
