import java.util.HashMap;

public class ManagementCrew extends Crew {
	
	private Queue <Flight> waiting_for_secutiry; 
	private Queue <Flight> waiting_for_logistics;
	private BoundedQueue <Flight> waiting_for_fuel;
	private Queue <Flight> waiting_for_technical;
	private Queue <FlightDetails> flight_details_queue;
	private int num_of_flights_expectd;
	private static int flights_arrived;
	private int num_of_logistic_crews, num_of_fuel_crews, num_of_technical_crews, num_of_secutiry;
	private int total_passengers, total_baggage, total_cost, total_suspicious_items, total_logistic_assistace, total_fuel;
	private DataBase database;
	private String favoriteDestination;
	private HashMap <String, Integer> destinationMap;
	
	public ManagementCrew(String id, int num_of_flights_expectd, int num_of_technical_crews, Queue <FlightDetails> flight_details_queue, Queue <Flight> waiting_for_secutiry, Queue <Flight> waiting_for_logistics, BoundedQueue <Flight> waiting_for_fuel, Queue <Flight> waiting_for_technical) {
		super(id);
		this.flight_details_queue = flight_details_queue;
		this.waiting_for_fuel = waiting_for_fuel;
		this.waiting_for_logistics = waiting_for_logistics;
		this.waiting_for_secutiry = waiting_for_secutiry;
		this.waiting_for_technical = waiting_for_technical;
		this.num_of_flights_expectd = num_of_flights_expectd;
		flights_arrived = 0;
		num_of_logistic_crews = 3;
		num_of_fuel_crews  =2;
		this.num_of_technical_crews = num_of_technical_crews;
		num_of_secutiry = 2;
		total_passengers = total_baggage = total_cost = total_suspicious_items = total_logistic_assistace = total_fuel = 0;
		destinationMap = new HashMap<String, Integer>();
		favoriteDestination = "";
		database = new DataBase();
	}//ManagementCrew - constructor

	public void run() {
		createSQLtables();
		while(flights_arrived < num_of_flights_expectd) {
			FlightDetails current_flight_details;
			current_flight_details = flight_details_queue.extract();
			if(current_flight_details != null) {
				countAggregateData(current_flight_details);
				insertDetailsToDatabase(current_flight_details);
				flights_arrived++;
			}//if (flight_details not null)
		}//while
		endDay();
		findFavoriteDestination();
		System.out.println("Total Passengers: " + total_passengers);
		System.out.println("Total Baggage: " + total_baggage);
		System.out.println("Favorite Destination: " + favoriteDestination);
		System.out.println("Total Cost: " + total_cost);
		System.out.println("Total Fuel Used: " + total_fuel);
		System.out.println("Total Suspicious Items: " + total_suspicious_items);
		System.out.println("Total Logistic Assistance Trucks: " + total_logistic_assistace);
	}//run
	
	//This method search favorite destination.
	private void findFavoriteDestination(){
		int favorite = 0;
		for (String des : destinationMap.keySet()) {
		    if(destinationMap.get(des)>favorite)
		    	favorite = destinationMap.get(des);
		    	favoriteDestination = des;
		}//for
	}//findFavoriteDestination
	
	//This method inserts flight details to SQL database.
	private void insertDetailsToDatabase(FlightDetails fd) {
		try {Thread.sleep(2000); }catch(InterruptedException e) {}
		
		if(!fd.getIsTakeoff()){
			String line = "(ID, Passengers, Cargo, Cost, isSecurityIssue, timeInAirfield) " + "VALUES('"+fd.getID()+"', '"+fd.getPassengers()+"', '"+fd.getCost()+"', '"+fd.getCargo()+"', '"+fd.getSecurityCheck()+"', '"+fd.getTimeInAirfield()+"')"; 
			database.insertToTable("Landings", line);
		}//the flight landed
		else{
			String line = "(ID, Passengers, Destination, timeInAirfield) " + "VALUES('"+fd.getID()+"', '"+fd.getPassengers()+"', '"+fd.getDestination()+"', '"+fd.getTimeInAirfield()+ "')"; 
			database.insertToTable("Takeoffs", line);
		}//the flight tookoff
	}//insertDetailsToDatabase

	//This method counts aggregate data
	private void countAggregateData(FlightDetails fd){
		total_passengers += fd.getPassengers();
		total_cost += fd.getCost();
		if(!fd.getIsTakeoff())//flight landed
			total_baggage += fd.getCargo();
		else //flight tookoff
			if(destinationMap.containsKey(fd.getDestination()))
				destinationMap.put(fd.getDestination(),destinationMap.get(fd.getDestination())+1);
			else
				destinationMap.put(fd.getDestination(),1);
		if(fd.getSecurityCheck())
			total_suspicious_items++;
		if(fd.getFueledStatus())
			total_fuel += 1000;
		if(fd.getLogisticAssistance())
			total_logistic_assistace++;
	}//countAggregateData
	
	//This method creates the database tables
	private void createSQLtables(){
		String sqlLandingTableFields = "(ID varchar(10), Passengers int, Cargo int,Cost int,isSecurityIssue varchar(10), timeInAirfield int)";
		database.createTable("Landings", sqlLandingTableFields);
		String sqlTakeoffTableFields = "(ID varchar (10),Passengers int, Destination varchar(30), timeInAirfield int)";
		database.createTable("Takeoffs", sqlTakeoffTableFields);
	}//createSQLtables
	
	//This method ends work day of crews.
	private void endDay() {
		//finish logistics crew day
		for (int i = 0; i < num_of_logistic_crews ; i++)
			waiting_for_logistics.insert(null);
		//finish security crew day
		for (int i = 0; i < num_of_secutiry ; i++)
			waiting_for_secutiry.insert(null);
		//finish fuel crew day
		for (int i = 0; i < num_of_fuel_crews ; i++)
			waiting_for_fuel.insert(null);
		//finish technical crew day
		for (int i = 0; i < num_of_technical_crews ; i++)
			waiting_for_technical.insert(null);
	}//endDay

}//ManagementCrew
