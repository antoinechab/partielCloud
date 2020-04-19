package model;

public class Compte {
	private int id;
	private String name;
	private float amount;
	private int lastrisk;
	
	public Compte() {
		
	}
	
	public Compte(int id , String name, float amount, int lastrisk) {
		this.setId(id);
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	@Override
	public String toString() {
		   return this.id + " - " + this.name + " avec un montant de: " + this.amount;
		}


}
