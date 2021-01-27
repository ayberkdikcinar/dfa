
public class Connection {

	private String start;
	private String connection;
	private String end;
	

	public Connection() {
		super();
		this.start = "";
		this.end = "";
		this.connection = "";
	}
	
	public Connection(String start,String character,String end) {
		super();
		this.start = start;
		this.end = end;
		this.connection = character;
	}
	
	public String print() {
		return start + " - " + connection + " - " + end;
	}

	//// Getter-setters
	
	
	public String getStart() {
		return start;
	}



	public void setStart(String start) {
		this.start = start;
	}



	public String getEnd() {
		return end;
	}



	public void setEnd(String end) {
		this.end = end;
	}



	public String getConnection() {
		return connection;
	}



	public void setConnection(String connection) {
		this.connection = connection;
	}



}
