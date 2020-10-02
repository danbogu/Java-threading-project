
public class FuelCrew extends Crew {

	private int possible_fuel_capacity, fuel_left;
	private BoundedQueue <Flight> flight_queue;
	private Queue <FlightDetails> next_queue;
	private Queue <Flight> technical_queue;
	//private static boolean shift_is_not_over;
	
	public FuelCrew(String id, int possible_fuel_capacity, BoundedQueue <Flight> flight_queue, Queue <FlightDetails> next_queue,Queue <Flight> technical_queue){
		super(id);
		this.fuel_left = this.possible_fuel_capacity = possible_fuel_capacity;
		this.flight_queue = flight_queue;
		this.next_queue = next_queue;
		this.technical_queue = technical_queue;
		//shift_is_not_over = true;
	}//FuelCrew - Constructor
	
	public void run() {
		while(true) {
			Flight current_flight;
			current_flight = flight_queue.extract();
			if(current_flight == null) 
				break;
			else{
				if(canFuelPlane())
					fuelPlane(current_flight);
				else {
					returnFlightToQueue(current_flight);
					refuelTruck(current_flight);
				}//else (can't fuel plane)
				if(Malfunction())
					referToTechnicalCrew(current_flight);
				else 
					sendDocumentToManagement(current_flight);
			}//else (not null)
		}//while
	}//run
	
	//This method creates the flight documentation and sends it to management.
	private void sendDocumentToManagement(Flight f) {
		FlightDetails document = new FlightDetails(f.getCode(),f.getPassengers());
		document.setCargo(f.getBaggage());
		document.setCost(f.getCost());
		document.setSecurityCheck(f.getSecurityProblem());
		document.setTimeInAirfield(f.getHandleTime());
		document.setFueledStatus(f.getIfFueled());
		document.setSecurityCheck(f.getSecurityProblem());
		document.setLogisticAssistance(f.getLogisticAssistance());
		this.next_queue.insert(document);
	}//sendDocumentToManagement
	
	//This method simulates a possible malfunction.
	private boolean Malfunction() {
		double rand_num = Math.random()*100;
		if(rand_num<30)
			return true;
		return false;
	}//Malfunction
		
	//This methods refers the flight to technical crew.
	private void referToTechnicalCrew(Flight f) {
		f.setMalfunctionAt(Malfunction.fuelMalfunction());
		technical_queue.insert(f);
	}//referToTechnicalCrew
	
	//This method refuels the truck.
	private void refuelTruck(Flight f) {
		try {Thread.sleep(5000); }catch(InterruptedException e) {}
		fuel_left = possible_fuel_capacity;
		f.setHandleTime(5);
	}//refuelTruck
	
	//This method returns the flight to queue for fueling.
	private void returnFlightToQueue(Flight f) {
		flight_queue.insert(f);
	}//returnFlightToQueue
	
	//This method checks if the crew has enough fuel to fuel the plane.
	private boolean canFuelPlane() {
		if(fuel_left >= 1000)
			return true;
		return false;
	}//canFuelPlane
	
	//This method simulates plane fueling.
	private void fuelPlane(Flight f) {
		long fueling_time = Math.round((Math.random() + 3)*1000); //min 3+0 = 3 sec. max 3+1 = 4 sec.
		try {Thread.sleep(fueling_time); }catch(InterruptedException e) {}
		fuel_left -= 1000;
		f.setHandleTime((int)fueling_time/1000);
		f.setFueled();
	}//fuelPlane
	
}//class - FuelCrew
