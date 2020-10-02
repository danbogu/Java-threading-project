
public class RunwayDirector implements Runnable {
	
	//private static TestBuffer <Flight> landing_flights; 
	private Queue <Flight> departing_and_landing_flights; 
	private Queue <Flight> technical_queue; 
	private Queue <Flight> next_queue; 
	private Queue <FlightDetails> management_queue;
	private int num_of_flights_expectd;
	private static int flights_arrived;
	
	public RunwayDirector(int num_of_flights_expectd, Queue <Flight> departing_and_landing_flights, Queue <Flight> next_queue, Queue <FlightDetails> management_queue, Queue <Flight> technical_queue) {
		this.management_queue = management_queue;
		this.technical_queue = technical_queue;
		this.departing_and_landing_flights = departing_and_landing_flights;
		this.next_queue = next_queue;
		this.num_of_flights_expectd = num_of_flights_expectd;
		flights_arrived = 0;
	}//RunwayDirector - constructor
	
	public void run() {
		while(flights_arrived < num_of_flights_expectd) {
			Flight current_flight;
			current_flight = departing_and_landing_flights.extract();
			if(current_flight != null) {
				handleFlight(current_flight);
				flights_arrived++;
				if(current_flight.isLanding()) { // landing
					if(Malfunction())
						referToTechnicalCrew(current_flight);
					else
						referToNextQueue(current_flight);
				}// if landing
				else
					sendDocumentToManagement(current_flight);
			}//if (flight_details not null)
		}//while
	}//run
	
	//This method creates the flight documentation and sends it to management.
	private void sendDocumentToManagement(Flight f) {
		FlightDetails document = new FlightDetails(f.getCode(),f.getPassengers());
		document.setDestination(f.getDestination());
		document.setTimeInAirfield(f.getHandleTime());
		document.setIsTakeoff();
		management_queue.insert(document);
	}//sendDocumentToManagement
	
	//This method refers the flight to the next queue.
		private void referToNextQueue(Flight f) {
			next_queue.insert(f);
		}//referToNextQueue
		
		//This methods refers the flight to technical crew.
		private void referToTechnicalCrew(Flight f) {
			f.setMalfunctionAt(Malfunction.landingMalfunction());
			technical_queue.insert(f);
		}//referToTechnicalCrew
	
	//This method simulates a possible malfunction.
		private boolean Malfunction() {
			double rand_num = Math.random()*100;
			if(rand_num<25)
				return true;
			return false;
		}//Malfunction
	
	//This method simulates a random flight landing time.
	private void handleFlight(Flight f) {
		long run_way = Math.round((Math.random()*5 + 5)*1000); //min 5+0 = 5 sec. max 5+1*5 = 10 sec.
		try {Thread.sleep(run_way); }catch(InterruptedException e) {}
		int time = (int)run_way/1000;
		f.setHandleTime(time);
	}//landFlight
	

}// class RunwayDirector
