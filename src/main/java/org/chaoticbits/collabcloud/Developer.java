package org.chaoticbits.collabcloud;

public class Developer {
	private String name = "";
	private String email = "";

	public Developer(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return name + " <" + email + ">";
	}
}
