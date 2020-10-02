
public class FlightDetails {

	private String ID, destination;
	private boolean security_checked, fueled, logistic_assistance_called;
	private int passengers, cargo, cost, timeInAirfield;
	private boolean isTakeoff;
	
	public FlightDetails(String ID, int passengers) {
		this.ID = ID;
		this.passengers = passengers;
		this.cargo = this.cost = this.timeInAirfield = 0;
		this.logistic_assistance_called = this.security_checked = this.fueled  = this.isTakeoff = false;
	}//Constructor
	

	public void setDestination(String destination) {
		this.destination = destination;
	}//setDestination
	
	public void setCargo(int cargo) {
		this.cargo = cargo;
	}//setCargo
	
	public void setCost(int cost) {
		this.cost += cost;
	}//setCost
	
	public void setTimeInAirfield(int time) {
		this.timeInAirfield += time;
	}//setTimeInAirfield
	
	public void setSecurityCheck(boolean security_problem) {
		this.security_checked = security_problem;
	}//setSecurityCheck
	
	public void setFueledStatus(boolean status) {
		this.fueled = status;
	}//setFueledStatus
	
	public void setLogisticAssistance(boolean assistance) {
		this.logistic_assistance_called = assistance;
	}//setLogisticAssistance
	
	public String getID() {
		String ID_copy = ID;
		return ID_copy;
	}//getID
	
	public boolean getSecurityCheck() {
		return security_checked;
	}//getSecurityCheck
	
	public boolean getFueledStatus() {
		return fueled;
	}//getFueledStatus	
	
	public boolean getLogisticAssistance() {
		return logistic_assistance_called;
	}//getLogisticAssistance	
	
	public int getPassengers() {
		return this.passengers;
	}//getPassengers
	
	public int getCargo() {
		return this.cargo;
	}//getCargo
	
	public int getCost() {
		return this.cost;
	}//getCost
	
	public int getTimeInAirfield() {
		return this.timeInAirfield;
	}//getTimeInAirfield
	
	public String getDestination() {
		String destination_copy = this.destination;
		return destination_copy;
	}//getDestination
	
	public void setIsTakeoff(){
		this.isTakeoff = true;
	}//setIsLanded
	
	public boolean getIsTakeoff(){
		return isTakeoff;
	}//getIsTakeoff
	
}//class FlightDetails
