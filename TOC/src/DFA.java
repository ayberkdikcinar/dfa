
public class DFA {

	private String start_state;
	private String[] accept_states;
	private String[] all_states;
	private String[] alphabet;
	public Connection[] transactions;
	public int transaction_count;

	public DFA() {
		
		transaction_count=0;
		transactions= new Connection[100];
	}
	
	
	public String getStart_state() {
		return start_state;
	}


	public void setStart_state(String start_state) {
		this.start_state = start_state;
	}


	public String[] getAccept_states() {
		return accept_states;
	}


	public void setAccept_states(String[] accept_states) {
		this.accept_states = accept_states;
	}


	public String[] getAll_states() {
		return all_states;
	}


	public void setAll_states(String[] all_states) {
		this.all_states = all_states;
	}


	public String[] getAlphabet() {
		return alphabet;
	}


	public void setAlphabet(String[] alphabet) {
		this.alphabet = alphabet;
	}


}
