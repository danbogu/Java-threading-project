
public class LogisticsCrew extends Crew {
	private int possible_cargo_load;
	private Queue <Flight> flight_queue;
	private Queue <Flight> next_queue;
	private Queue <Flight> technical_queue;

	
	public LogisticsCrew(String id, Queue<Flight> flight_queue, Queue <Flight> next_queue, Queue <Flight> technical_queue) {
		super(id);
		assignRandomCargoLoad();
		this.flight_queue = flight_queue;
		this.next_queue = next_queue;
		this.technical_queue = technical_queue;
	}//LogisticsCrew - Constructor
	
	
	public void run() {
		while(true) {
			Flight current_flight;
			current_flight = flight_queue.extract();
			if(current_flight == null) 
				break;
			else {
				if(canUnloadBaggage(current_flight))
					unloadBaggage(current_flight);
				else {
					callAssistanceTruck(current_flight);
					unloadBaggage(current_flight);
				}//else
				if(Malfunction())
					referToTechnicalCrew(current_flight);
				else
					referToNextQueue(current_flight);
			}//else (not null)	
		}//while
	}//run
	
	//This method refers the flight to the next queue.
	private void referToNextQueue(Flight f) {
		next_queue.insert(f);
	}//referToNextQueue
	
	//This methods refers the flight to technical crew.
	private void referToTechnicalCrew(Flight f) {
		f.setMalfunctionAt(Malfunction.logisticsMalfunction());
		technical_queue.insert(f);
	}//referToTechnicalCrew
	
	//This method simulates a possible malfunction.
	private boolean Malfunction() {
		double rand_num = Math.random()*100;
		if(rand_num<10)
			return true;
		return false;
	}//Malfunction
	
	//This method simulates time waiting for assistance truck.
	private void callAssistanceTruck(Flight current_flight) {
		try {Thread.sleep(2000); }catch(InterruptedException e) {}
		current_flight.setLogisticAssistance();
		current_flight.setHandleTime(2);
	}//callAssistanceTruck
	
	//This method unloads plane's baggage.
	private void unloadBaggage(Flight current_flight) {
		int unloading_time = current_flight.getBaggage()*100; // 1 sec per 10 units = 100 mil_sec per unit.
		try {Thread.sleep(unloading_time); }catch(InterruptedException e) {}
		current_flight.setHandleTime(unloading_time/1000);
	}//unloadBaggage
	
	//This method checks if the crew can unload the cargo.
	private boolean canUnloadBaggage(Flight f) {
		if(f.getBaggage()<possible_cargo_load)
			return true;
		return false;
	}//canUnloadBaggage
	
	
	//This method assigns a random possible cargo load.
	private void assignRandomCargoLoad() {
		double ran_num;
		double third = 1.0/3.0;
		ran_num = Math.random();
		if(ran_num < third)
			possible_cargo_load = 50;
		else if(ran_num > third && ran_num < 2*third)
			possible_cargo_load = 70;
		else
			possible_cargo_load = 90;
	}//assignRandomCargoLoad

}//class
