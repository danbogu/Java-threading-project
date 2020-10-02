import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Airport {
	LandingTakeoffQueue<Flight> waiting_for_runway;
	Queue<Flight> waiting_for_logistics;
	Queue<Flight> waiting_for_secutiry;
	Queue<Flight> waiting_for_technical;
	Queue<FlightDetails> waiting_for_management;
	BoundedQueue<Flight> waiting_for_fuel; 
	Vector <Flight> flights_vector;
	RunwayDirector rd1;
	RunwayDirector rd2;
	RunwayDirector rd3;
	FuelCrew fc1;
	FuelCrew fc2;
	LogisticsCrew lg1;
	LogisticsCrew lg2;
	LogisticsCrew lg3;
	SecurityMan sm1;
	SecurityMan sm2;
	ManagementCrew mc;
	Thread th1;
	Thread th2;
	Thread th3;
	Thread th4;
	Thread th5;
	Thread th6;
	Thread th7;
	Thread th8;
	Thread th9;
	Thread th10;
	Thread th11;
	int num_of_tech_crews, time_for_secutiry; //input from GUI
	int num_of_flights_expected;
	TechnicalCrew [] tech_crews;
	Thread [] thread_for_technical_crew;
	Thread [] thread_for_flights;
	
	public Airport(int num_of_tech_crews, int time_for_secutiry) {
		waiting_for_runway = new LandingTakeoffQueue<Flight>();
		waiting_for_logistics = new Queue<Flight>();
		waiting_for_secutiry = new Queue<Flight>();
		waiting_for_fuel = new BoundedQueue<Flight>(8);
		waiting_for_technical = new Queue<Flight>();
		waiting_for_management = new Queue<FlightDetails>();
		flights_vector = new Vector <Flight>();
		createFlights("FlightsData.txt"); //data path.
		this.num_of_flights_expected = flights_vector.size();
		this.num_of_tech_crews = num_of_tech_crews;
		this.time_for_secutiry = time_for_secutiry;
		rd1 = new RunwayDirector(num_of_flights_expected, waiting_for_runway, waiting_for_logistics, waiting_for_management, waiting_for_technical);
		rd2 = new RunwayDirector(num_of_flights_expected, waiting_for_runway, waiting_for_logistics, waiting_for_management, waiting_for_technical);
		rd3 = new RunwayDirector(num_of_flights_expected, waiting_for_runway, waiting_for_logistics, waiting_for_management, waiting_for_technical);
		lg1 = new LogisticsCrew("lg1",waiting_for_logistics,waiting_for_secutiry,waiting_for_technical);
		lg2 = new LogisticsCrew("lg2",waiting_for_logistics,waiting_for_secutiry,waiting_for_technical);
		lg3 = new LogisticsCrew("lg3",waiting_for_logistics,waiting_for_secutiry,waiting_for_technical);
		sm1 = new SecurityMan(time_for_secutiry, waiting_for_secutiry, waiting_for_fuel);
		sm2 = new SecurityMan(time_for_secutiry, waiting_for_secutiry, waiting_for_fuel);
		fc1 = new FuelCrew("fc1",10000,waiting_for_fuel,waiting_for_management,waiting_for_technical);
		fc2 = new FuelCrew("fc2",5000,waiting_for_fuel,waiting_for_management,waiting_for_technical);
		mc = new ManagementCrew("1", num_of_flights_expected, num_of_tech_crews, waiting_for_management, waiting_for_secutiry, waiting_for_logistics, waiting_for_fuel, waiting_for_technical);
		tech_crews = new TechnicalCrew[num_of_tech_crews];
		thread_for_technical_crew = new Thread[num_of_tech_crews];
		thread_for_flights = new Thread[num_of_flights_expected];
		createTechCrews();
		th1 = new Thread(rd1);
		th2 = new Thread(rd2);
		th3 = new Thread(rd3);
		th4 = new Thread(lg1);
		th5 = new Thread(lg2);
		th6 = new Thread(lg3);
		th7 = new Thread(sm1);
		th8 = new Thread(sm2);
		th9 = new Thread(fc1);
		th10 = new Thread(fc2);
		th11 = new Thread(mc);
		flightThreads();
		startObjects();
		
	}//Airport constructor
	
	private void startObjects() {
		th1.start();
		th2.start();
		th3.start();
		th4.start();
		th5.start();
		th6.start();
		th7.start();
		th8.start();
		th9.start();
		th10.start();
		th11.start();
		for(int i = 0; i < num_of_tech_crews; i++)
			thread_for_technical_crew[i].start();
			
		for(int i = 0 ; i < num_of_flights_expected ; i++)
			thread_for_flights[i].start();
			
	}//startObjects
	
	private void flightThreads() {
		for(int i = 0 ; i < num_of_flights_expected ; i++)
			thread_for_flights[i] = new Thread(flights_vector.elementAt(i));
	}//flightThreads
	
	private void createTechCrews() {
		String id;
		for(int i = 0; i < num_of_tech_crews; i++) {
			id = "tc" + String.valueOf(i);
			tech_crews[i] = new TechnicalCrew(id, waiting_for_technical, waiting_for_logistics, waiting_for_secutiry, waiting_for_management);
			thread_for_technical_crew[i] = new Thread(tech_crews[i]);
		}//for loop
	}//createTechCrews
	
	//This method creates flights based on flights data file and inserts them to a vector.
		private void createFlights(String flights_data_path) {
			Vector <String> flights_data = new Vector<String>();
			flights_data = fileReader(flights_data_path);
			for (int i = 1 ; i < flights_data.size() ; i++) { //skip first line - headline
				String [] flight; //for each row of a flight
				flight = flights_data.get(i).split("\t");
				flights_vector.add(new Flight(flight[0], Integer.parseInt(flight[1]), Integer.parseInt(flight[2]), flight[3],waiting_for_runway));
			}//for
		}//createEmployees
	
	//this static function receives a path of a text file and returns a vector of lines <String> of the file
		private static Vector<String> fileReader(String path) {
			Vector <String> dataBuffer = null;
			BufferedReader br = null;
			try{
			  	FileReader fr = new FileReader(path);
			  	br = new BufferedReader(fr);
			  	dataBuffer = new Vector<String>();
			  	String line;
			  	while(((line = br.readLine()) != null))
			  		dataBuffer.add(line);
			}catch (FileNotFoundException exception) {
				System.out.println ("The file " + path + " was not found.");
			}catch (IOException exception){
				System.out.println (exception);
			}finally{
				try {
					br.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}//finally
			return dataBuffer;
		}//fileReader

}//Airport class
