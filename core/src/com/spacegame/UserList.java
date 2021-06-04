package com.spacegame;

import java.io.Serializable;
import java.util.ArrayList;


public class UserList implements Serializable {


	private ArrayList<String> names;
	private ArrayList<String> passwords;

	public UserList(){
		names= new ArrayList<>();
		passwords= new ArrayList<>();
	}

	public ArrayList<String> getName() {
		return names;
	}

	public void setName(ArrayList<String> name) {
		this.names = name;
	}

	public ArrayList<String> getPasswords() {
		return passwords;
	}

	public void setPasswords(ArrayList<String> passwords) {
		this.passwords = passwords;
	}
}

