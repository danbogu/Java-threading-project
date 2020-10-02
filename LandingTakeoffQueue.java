
public class LandingTakeoffQueue <F extends Flight> extends Queue<F> {
	
	public LandingTakeoffQueue() {
		super();
	}//constructor 
	
	public synchronized void insert(F f) {
		if(buffer.size()>0) {
			if(!f.isLanding()){
				buffer.add(f);
				notifyAll(); 
			}//if departing 
			else {
				int index = buffer.size();
			for(int i = 0; i < buffer.size(); i++)
				if(!buffer.elementAt(i).isLanding()) {
					index = i;
					break;
				}//if
			buffer.add(index-1, f);
		}
	}else {  //the queue is empty
		buffer.add(f);
		notifyAll(); 
	}
}//insert
	
}//class LandingTakeoffQueue
