//Machine resource. Note: Current implementation has only one machine of each type. That is, only one object of type Machine.

public class Machine{
	public boolean burger, coke, fries;
	public int burgertime, coketime, friestime;
	public Machine(boolean burger, boolean coke, boolean fries){
		this.burger = burger;
		this.coke = coke;
		this.fries = fries;
		coketime = 0;
		burgertime = 0;
		friestime = 0;
	}
}
