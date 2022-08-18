/* 
Assignment #: 6
        Name: Michael Buerger
   StudentID: 1214351462
     Lecture: Tu Th 9:00 AM - 10:15 AM
 Description: The Hero class holds information about a Hero:
			  it's name, type, strength, charisma, and damage.
			  It also overrides the toString method.
*/

public class Hero {
	private String name;
	private String type;
	private int strength;
	private int charisma;
	private int damage;
	
	/**
	 * 
	 * @param _name Name
	 * @param _type	Type (Mage or Fighter)
	 * @param _strength Strength
	 * @param _charisma Charisma
	 * @param _damage Damage
	 */
	public Hero(String _name, String _type, int _strength, int _charisma, int _damage) {
		this.name = _name;
		this.type = _type;
		this.strength = _strength;
		this.charisma = _charisma;
		this.damage = _damage;
	}
	
	/*
		Default constructor, setup with some basic values
     */
	public Hero() {
		this("?", "?", 0, 0, 0);
	}
	
	/*
		Get name of hero
     */
	public String getName() {
		return name;
	}

	/*
		Set name of hero
     */
	public void setName(String name) {
		this.name = name;
	}

	/*
		Get type of hero
     */
	public String getType() {
		return type;
	}

	/*
		Set type of hero
     */
	public void setType(String type) {
		this.type = type;
	}

	/*
		Get strength of hero
     */
	public int getStrength() {
		return strength;
	}

	/*
		Set strength of hero
     */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/*
		Get charisma of hero
     */
	public int getCharisma() {
		return charisma;
	}

	/*
		Set charisma of hero
     */
	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}

	/*
		Get damage of hero
     */
	public double getDamage() {
		return damage;
	}

	/*
		Set damage of hero
     */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/*
		Override toString to display hero data
     */
	public String toString() {
		return String.format("Hero name: %s\t\t\t(%s)\nStrength: %d\tCharisma: %d\tDamage: %d\n", this.name, this.type, this.strength, this.charisma, this.damage);		
	}
}
