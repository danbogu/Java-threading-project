
public abstract class Crew implements Runnable {
	protected String id;
	
	
	public Crew(String id) {
		this.id = id;
	}//Crew - constructor
	
	public abstract void run();
	
	
		
		
}//class Crew
