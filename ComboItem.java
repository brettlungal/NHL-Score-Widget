
public class ComboItem {

	private String teamName;
	private int teamId;
	
	public ComboItem(String teamName,int teamId) {
		this.teamName = teamName;
		this.teamId = teamId;
	}
	
	public String getName() {
		return teamName;
	}
	
	public int getId() {
		return teamId;
	}
	
	public String toString() {
		return teamName;
	}
	
}
