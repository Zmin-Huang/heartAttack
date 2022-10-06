package tw.howard.midProject;

import java.util.LinkedList;

public class player extends LinkedList<Integer>{
	
	private String name;
	private String sex;
	public player(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
	}
}
