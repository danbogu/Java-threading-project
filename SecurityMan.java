
public class SecurityMan implements Runnable{
	private int security_check_time; //input from GUI
	private String rank; //randomly
	private Queue <Flight> flight_queue;
	private BoundedQueue <Flight> next_queue;
	//private static boolean shift_is_not_over;

	public SecurityMan(int check_time, Queue<Flight> flight_queue,BoundedQueue <Flight> next_queue) {
		this.rank = random_rank();
		this.security_check_time = check_time;
		this.flight_queue = flight_queue;
		this.next_queue = next_queue;
	}//SecurityMan - Constructor
	
	public void run() {
		while(true) {
			Flight current_flight;
			current_flight = flight_queue.extract();
			if(current_flight == null) 
				break;
			else {
				security_check(current_flight);
				if(suspicious_object()) 
					handle_suspicious_object(current_flight);
				referToNextQueue(current_flight);
			}//else (not null)	
		}//while
	}//run
	
	///This method randomly assign security-man rank
	private String random_rank() {
		double rand_num = Math.random()*100;
		if(rand_num<50)
			return "Senior";
		return "Junior";
	}//random_rank
	
	//This method refers the flight to the next queue.
	private void referToNextQueue(Flight f) {
		next_queue.insert(f);
	}//referToNextQueue
	
	//This method does security check to the fight
	private void security_check(Flight f) {
		try {Thread.sleep(security_check_time*1000); }catch(InterruptedException e) {}
		f.setHandleTime(security_check_time/1000);
	}//security_check
	
	//This method simulates a possible suspicious_object
	private boolean suspicious_object() {
		double rand_num = Math.random()*100;
		if(rand_num<5) 
			return true;
		return false;
	}//suspicious_object
	
	//This method simulates a handling the situation
	private void handle_suspicious_object(Flight f){
		try {Thread.sleep(2000); }catch(InterruptedException e) {} //takes 2 seconds to handle it
		f.setSecurityIssue();
		f.setHandleTime(2);
	}//handle_suspicious_object
}// class SecurityMan