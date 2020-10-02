
public class Flight implements Runnable{
	public String code;
	private int baggage;//landing flights only
	private int passengers;
	private int arrival_time;
	private String destination;//departure flights only
	private String malfunctionAt;
	private boolean isLanding, securityProblem, fueled, logisticAssistenceCall;
	private Queue <Flight> queue;
	private int handle_time;
	private int cost;
	
	//landing flight constructor
	public Flight(String code, int passengers, int arrival_time, String land_or_takeoff, Queue<Flight> queue) {
		this.code = code;
		this.passengers = passengers;
		this.arrival_time = arrival_time*1000;//in milliseconds
		this.queue = queue;
		this.destination = "";
		this.handle_time = this.baggage = 0;
		this.securityProblem = this.fueled = this.logisticAssistenceCall = false;
		landOrTakeoff(land_or_takeoff);
	}//constructor
	
	public void run() {
		try {
			Thread.sleep(this.arrival_time); 
		}catch(InterruptedException e) {}
		enterToRunwayQueue();
	}//run 
	
	//This method checks if the flight is landing or taking off.
	private void landOrTakeoff(String land_or_takeoff) {
		try {
		this.baggage = Integer.valueOf(land_or_takeoff);
		this.isLanding = true;
		}catch(NumberFormatException e) {
			this.isLanding = false;
			this.destination = land_or_takeoff;
		}//catch
	}//landOrTakeoff
		
	//This method inserts the flight to the waiting runway queue.
	private void enterToRunwayQueue() {
		queue.insert(this);
	}//enterToRunwayQueue
	
	//-------------------necessary gets and sets-------------------
	
	public int getBaggage() {
		return baggage;
	}//getBaggage
	
	public int getPassengers() {
		return passengers;
	}//getPassengers
	
	public String getDestination() {
		String destinantion_copy = destination;
		return destinantion_copy;
	}//getDestination
	
	public void setMalfunctionAt(String mal){
		String malfunction = mal;
		malfunctionAt = malfunction;
	}//setMalfunction
	
	public String getMalfunctionAt(){
		String malfunctionAt_copy = malfunctionAt;
		return malfunctionAt_copy; 
	}//sentToTechnicalFrom
	
	//This method returns a boolean argument if the flight is landing.
	public boolean isLanding() {
		return isLanding;
	}//isLanding
	
	public int getCost() {
		return cost;
	}//getCost
	
	public void setHandleTime(int time) {
		this.handle_time += time;
	}//setHandleTime
	
	public void setCost(int cost) {
		this.cost += cost;
	}//setCost
	
	public int getHandleTime() {
		return handle_time;
	}//getHandleTime
	
	public String getCode() {
		String code_copy = code;
		return code_copy;
	}//getCode
	
	public boolean getSecurityProblem() {
		return securityProblem;
	}//getSecurityProblem
	
	public void setSecurityIssue() {
		this.securityProblem = true;
	}//setSecurityIssue
	
	public void setFueled() {
		this.fueled = true;
	}//setFueled
	
	public boolean getIfFueled() {
		return fueled;
	}//getIfFueled
	
	public void setLogisticAssistance() {
		this.logisticAssistenceCall = true;
	}//setLogisticAssistance
	
	public boolean getLogisticAssistance() {
		return logisticAssistenceCall;
	}//getLogisticAssistance
	
	
}//flight
