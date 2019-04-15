import java.io.*;

//This implements the cook thread. Functions like taking order, cooking food, serving etc are implemented here.

public class Cook extends Thread{
	public Order order;
	public int cookid;
	public int time;
	public Main Main;
	
	public Cook(int id, Main main){
		this.cookid = id;
		this.time = 0;
		Main = main;
	}


//The cook takes order from diner who has occupied the table. (Only if people are waiting for cooks, implemented in Diner class).
	public void takeorder(){
		synchronized(Main.order_q){
			try{
				while(Main.order_q.isEmpty()){
					Main.order_q.wait();
				}
				order = Main.order_q.poll();
				time = Math.max(time,order.time);
				System.out.println("time: "+time+" cook "+cookid+" takes order from diner "+order.dinerid);
			}
			catch(Exception e){
				System.out.println("Exception Occured");
			}
		}


		synchronized(Main.avail_cook_q){
			Main.avail_cook_q.remove(this);
		}
		synchronized(Main.busy_cook_q){
			Main.busy_cook_q.add(this);
		}
		synchronized(Main.machine_q){
			Main.machine_q.add(this);
		}

	}		
	
//Cook prepares the burgers, fries and cokes iteratively. Here assumption is that cook prepares all the burgers at once. That is, once a cook has access to a burger machine he/she does not leave it unless all the burgers are cooked. This is done iteratively for burger, coke and fries in that order. If a cook cannot get any of the three machines, he/she waits.
	public void prepare(){
		synchronized(Main.machine_q){
			while(order.burgers!=0 || order.cokes!=0 || order.fries!=0){
				if(Main.machine.burger && order.burgers!=0){
					synchronized(Main.machine){
						Main.machine.burger = false;
						time = Math.max(time,Main.machine.burgertime);
						System.out.println("time: "+time+" cook "+cookid+" started using burger machine");
						time+=order.burgers*5;
						order.burgers=0;
						Main.machine.burgertime = time;
						Main.machine.burger = true;
						System.out.println("time: "+time+" cook "+cookid+" stopped using burger machine");
						Main.machine.notify();
					}
				}
				if(Main.machine.coke && order.cokes!=0){
					synchronized(Main.machine){
						Main.machine.coke = false;
						time = Math.max(time,Main.machine.coketime);
						System.out.println("times: "+time+" cook "+cookid+" started using coke machine");
						time+=order.cokes*1;
						order.cokes=0;
						Main.machine.coketime = time;
						Main.machine.coke = true;
						System.out.println("time: "+time+" cook "+cookid+" stopped using coke machine");
						Main.machine.notify();
					}
				}
				if(Main.machine.fries && order.fries!=0){
					synchronized(Main.machine){
						Main.machine.fries = false;
						time = Math.max(time,Main.machine.friestime);
						System.out.println("time: "+time+" cook "+cookid+" started using fries machine");
						time+=order.fries*5;
						order.fries=0;
						Main.machine.friestime = time;
						Main.machine.fries = true;
						System.out.println("time: "+time+" cook "+cookid+" stopped using fries machine");
						Main.machine.notify();
					}
				}

				else if(order.burgers!=0 || order.fries!=0 || order.cokes!=0){
					try{
						Main.machine.wait();
					}
					catch( Exception e){
						System.out.println("Exception Occured");
					}
				}
				
 			}
			System.out.println("time: "+time+" cook "+cookid+" finished order for diner "+order.dinerid);
			Main.machine_q.remove(this);
		}
	}

//This function releases the cook resource once the order is complete.
	public void serve(){
		synchronized(order){
			order.time = time;
			order.notify();
		}
		System.out.println("time: "+time+" cook "+cookid+" served the food to diner "+order.dinerid);
		synchronized (Main.avail_cook_q){
			Main.avail_cook_q.add(this);
		}
		synchronized (Main.busy_cook_q){
			Main.busy_cook_q.remove(this);
		}
	}

	public void run(){
		while(true){
			takeorder();
			prepare();
			serve();
		}
	}

}
