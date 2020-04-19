package model;

public class Compte {
	private String name;
	private float amount;
	private int lastrisk;
	
	public Compte() {
		
	}
	
	public Compte(String name, float amount, int lastrisk) {
		this.setName(name);
		this.setAmount(amount);
		this.setLastrisk(lastrisk);
	}

	public int getLastrisk() {
		return lastrisk;
	}

	public void setLastrisk(int lastrisk) {
		this.lastrisk = lastrisk;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
