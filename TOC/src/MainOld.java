import java.io.File;  
import java.io.FileNotFoundException; 
import java.util.Scanner;

public class MainOld {

	static DFA dfa;
	static String [][]table;
	
	public static void main(String[] args) {
		
		dfa = new DFA();
		/*String array[][]=new String[10][10];
		array[1][0]="S"; ///start-> end
		array[0][1]="S"; ///end <-start
		array[array.length-1][0]="F";
		array[0][array.length-1]="F";
		String a="A=q1,q2";
		String splitByEqualMark[]=a.split("=");
	    String splitByComma[]=splitByEqualMark[1].split(",");
		dfa.setAccept_states(splitByComma);
		Transaction transact=new Transaction("a","b","c");
		DFA dfa = new DFA();
		dfa.transactions[dfa.transaction_count++]=transact;
		System.out.println("An error occurred.");*/
		handleTxt("file.txt");
		init_table();
		

	}
	
	public static void handleTxt(String file_txt) {
		
		try {
		      File myObj = new File(file_txt);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine(); ///each line of the .txt file.
		        String splitByEqualMark[]=data.split("=");
		        String splitByComma[]=splitByEqualMark[1].split(",");
		        if(splitByEqualMark[0].equals("S"))
		        	dfa.setStart_state(splitByEqualMark[1]);
		        else if(splitByEqualMark[0].equals("A"))
		        	dfa.setAccept_states(splitByComma);
		        else if(splitByEqualMark[0].equals("E"))
		        	dfa.setAlphabet(splitByComma);
		        else if(splitByEqualMark[0].equals("Q"))
		        	dfa.setAll_states(splitByComma);
		        else {	
		        	splitByComma=splitByEqualMark[0].split(",");
		        	Connection transact=new Connection(splitByComma[0],splitByComma[1],splitByEqualMark[1]);
		        	dfa.transactions[dfa.transaction_count++]=transact;

		        }	        
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	public static void init_table() {
		int length=dfa.getAll_states().length;
		table = new String[length+3][length+3];
		///initialize Start state and Final state.
		table[0][1]="S";
		table[1][0]="S";
		table[table.length-1][0]="F";
		table[0][table.length-1]="F";
		for (int i = 2; i < table.length-1; i++) {
			table[i][0]=dfa.getAll_states()[i-2];
			table[0][i]=dfa.getAll_states()[i-2];
		}
		fill_table(table);
		for (int i = 0;  i< table.length; i++) {
			for (int j = 0; j < table.length; j++) {
				System.out.print(table[i][j]+" ");
			}
			System.out.println("\n");
		}
		
		
		
	}
	public static void fill_table(String table[][]) {
		
		//handling start and accept states for the transaction table.
		for (int i = 1; i < table.length; i++) {
			if(table[0][i].equals(dfa.getStart_state())){
				table[1][i]="E"; ///means empty string.
			}
			for(int k=0; k<dfa.getAccept_states().length; k++) {
				if(table[i][0].equals(dfa.getAccept_states()[k])) { ///start state i S ve final state'i F olarak ayırıyoruz.
					table[i][table.length-1]="E"; //means empty string.
				}	
			}
			if(i>1) {
				for (int j = 0; j < dfa.transaction_count; j++) {
					if(dfa.transactions[j].getStart().equals(table[i][0])) {
						for (int j2 = 2; j2 < table.length; j2++) {
							if(dfa.transactions[j].getEnd().equals(table[0][j2])) {
								if(table[i][j2]!=null) {
									table[i][j2]=table[i][j2]+dfa.transactions[j].getConnection();
								}
								else
									table[i][j2]=dfa.transactions[j].getConnection();
							}
						}
					}
				}
			}		
			
		}

		
	}
	
	
	
	

}
