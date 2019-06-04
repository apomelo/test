package test.com.fasterxml.jackson;

import java.util.List;

public class JacksonTestEntity<T> {

	private int num;
	private String name;
	private boolean status;
	private List<T> dialogue;
	
	public JacksonTestEntity(int num, String name, boolean status) {
		super();
		this.num = num;
		this.name = name;
		this.status = status;
	}

	public List<T> getDialogue() {
		return dialogue;
	}
	public void setDialogue(List<T> dialogue) {
		this.dialogue = dialogue;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "JacksonTestEntity [num=" + num + ", name=" + name + ", status=" + status + "]";
	}	
}
