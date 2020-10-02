
public class BoundedQueue <T> extends Queue <T> {

	private int max_size;
	
	public BoundedQueue(int max_size){
		super();
		this.max_size = max_size;
	}//constructor
	
	//This method adds an element to the queue up to the max size allowed
	public synchronized void insert(T item) {
		while(buffer.size()>=max_size)
			try {
				this.wait();
			}catch(InterruptedException e) {}
		    buffer.add(item);
		    notifyAll();  
	}//insert

}//class BoundedQueue
