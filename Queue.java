import java.util.Vector;

public class Queue <T> {
	
	protected Vector <T> buffer;

	public Queue(){
	     buffer = new Vector<T>();
	}//constructor
	  
	//This method adds an element to the queue
	public synchronized void insert(T item) {
		buffer.add(item);
		notifyAll(); 
	}//insert

	//This method extracts an element from the queue
	public synchronized T extract(){
			while(buffer.isEmpty())
				try {
					this.wait();
				}catch(InterruptedException e) {}
			T t = buffer.elementAt(0);
			buffer.remove(0);
			return t;
	}//extract
	
	
	public boolean isEmpty() {
		if(buffer.isEmpty())
			return true;
		return false;
	}//isEmpty
	
}//class Queue
