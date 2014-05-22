package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="bookdata_tbl")
public class Book {
	
	
	int id;
	
	String bookname;
	
	String authod;
	
	String publishCompany;
	
	String publishTime;
	
	String totalAmount;
	
	String remainer;
	
	public Book() {
		super();
	}

	
	public Book(int id, String bookname, String authod, String publishCompany,
			String publishTime, String totalAmount) {
		super();
		this.id = id;
		this.bookname = bookname;
		this.authod = authod;
		this.publishCompany = publishCompany;
		this.publishTime = publishTime;
		this.totalAmount = totalAmount;
	}


	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getAuthod() {
		return authod;
	}

	public void setAuthod(String authod) {
		this.authod = authod;
	}

	public String getPublishCompany() {
		return publishCompany;
	}

	public void setPublishCompany(String publishCompany) {
		this.publishCompany = publishCompany;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRemainer() {
		return remainer;
	}

	public void setRemainer(String remainer) {
		this.remainer = remainer;
	}
	
	
	
}
