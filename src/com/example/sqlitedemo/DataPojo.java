package com.example.sqlitedemo;

public class DataPojo {

	String Name;
	String age;
	String Address;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public DataPojo(String name, String age, String address) {
		super();
		Name = name;
		this.age = age;
		Address = address;
	}
	
	
	public DataPojo(){
		
	}
	
}
