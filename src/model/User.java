package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="userdata_tbl")
public class User {
	
	
	
	
	String username;
	
	String password;
	
	String authority;
	
	String gender;
	
	String number;

	List<BorrowBook_tbl> records = new ArrayList<BorrowBook_tbl>();

	
	
	@OneToMany(mappedBy="user")
	public List<BorrowBook_tbl> getRecords() {
		return records;
	}

	public void setRecords(List<BorrowBook_tbl> records) {
		this.records = records;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public User( String username, String password, String authority,
			String gender, String number) {
		super();
		
		this.username = username;
		this.password = password;
		this.authority = authority;
		this.gender = gender;
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public User() {
		
	}

	public User(String username, String password, String authority) {
		super();
		this.username = username;
		this.password = password;
		this.authority = authority;
	}

	@Id
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	
	
}
