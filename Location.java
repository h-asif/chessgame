import java.io.Serializable;



public class Location implements Serializable{
	static final long serialVersionUID= (long) 30000.3333;
	private int rank, file, position;
	
	public Location(Location location){
		rank = location.getRank() ;
		file = location.getFile();
		position = location.getPosition();
	}
	
	public Location(int newPosition){
		
		setLocation( newPosition );
		
	}
	
	public Location(int newFile, int newRank){
	
		setLocation(newFile , newRank);
		
	}

	public void setLocation( int newPosition ){
	
		position = newPosition;
		rank = 8 - ((int)Math.floor(newPosition/8) );
		file = 1 + (newPosition%8);
	
	}
	
	public void setLocation(int newFile, int newRank){
	
		rank = newRank;
		file = newFile;
		position = ( 8-newRank )*8 + (--newFile);
		
	}
	
	public void setLocation( Location newLocation ){
		
		position = newLocation.position;
		rank = newLocation.getRank();
		file = newLocation.getFile();
	
	}
	
	public int getRank(  ){
	
		return rank;
	
	}
	
	public int getFile(  ){
	
		return file;
	
	}
	
	public int getPosition(){
	
		return position;
	
	}
	
	
}