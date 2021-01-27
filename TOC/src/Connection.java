
public class Transaction {

	private String start;
	private String character;
	private String end;
	

	
	public Transaction(String start,String character,String end) {
		super();
		this.start = start;
		this.end = end;
		this.character = character;
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



	public String getCharacter() {
		return character;
	}



	public void setCharacter(String character) {
		this.character = character;
	}



}
