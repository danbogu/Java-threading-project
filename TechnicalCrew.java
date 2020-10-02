
public class TechnicalCrew extends Crew {
	
	private Queue <Flight> flight_queue;
	private Queue <Flight> waiting_for_security;
	private Queue <Flight> waiting_for_logistics;
	private Queue <FlightDetails> waiting_for_management;
	//private boolean shift_is_not_over;
	
	public TechnicalCrew(String id, Queue<Flight> flight_queue, Queue<Flight> waiting_for_security, Queue<Flight> waiting_for_logistics, Queue<FlightDetails> waiting_for_management) {
		super(id);
		this.flight_queue = flight_queue;
		this.waiting_for_security = waiting_for_security;
		this.waiting_for_logistics = waiting_for_logistics;
		this.waiting_for_management = waiting_for_management;
		//shift_is_not_over = true;
	}// TechnicalCrew - constructor

	public void run() {
		while(true) {
			Flight current_flight;
			current_flight = flight_queue.extract();
			if(current_flight == null) 
				break;
			else{
				repairPlane(current_flight);
				if(current_flight.getMalfunctionAt().equals(Malfunction.fuelMalfunction())) {
					extraFuelRepair(current_flight);
					sendDocumentToManagement(current_flight);
				}//if (fuel malfunction)
				else {
					if(current_flight.getMalfunctionAt().equals(Malfunction.landingMalfunction()))
						referToLogisticsQueue(current_flight);
					else if(current_flight.getMalfunctionAt().equals(Malfunction.logisticsMalfunction()))
						referToSecutiryQueue(current_flight);
				}//else (not fuel malfunction)
			}//else (not null)
		}//while
	}//run
	
	//This method refers the flight to the logistics queue.
	private void referToLogisticsQueue(Flight f) {
		waiting_for_logistics.insert(f);
	}//referToLogisticsQueue
	
	//This method refers the flight to the security queue.
		private void referToSecutiryQueue(Flight f) {
			waiting_for_security.insert(f);
		}//referToSecutiryQueue
	
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
		waiting_for_management.insert(document);
	}//sendDocumentToManagement
	
	//This method simulates extra fuel repairment .
		private void extraFuelRepair(Flight f) {
			try {Thread.sleep(1000); }catch(InterruptedException e) {}
			f.setHandleTime(1);
		}//repairPlane
	
	//This method simulates plane repairment.
	private void repairPlane(Flight f) {
		long repairment_time = Math.round((Math.random()*2 + 3)*1000); //min 3+0 = 3 sec. max 3+1*2 = 5 sec.
		try {Thread.sleep(repairment_time); }catch(InterruptedException e) {}
		f.setCost(repairCost());
		f.setHandleTime((int)repairment_time/1000);
	}//repairPlane
	
	//This method returns random repair cost.
	private int repairCost() {
		double cost;
		cost = Math.random()*500 + 500; // min 500+0*500=500;max 500+1*500=1000;
		int int_cost = (int)Math.round(cost);
		return int_cost;
	}//repairCost
	
}//class TechnicalCrew
