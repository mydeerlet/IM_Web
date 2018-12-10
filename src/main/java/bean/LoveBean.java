package bean;

import java.util.Date;

public class LoveBean {

	private int id;
	private String result;
	private Date crateDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getCrateDate() {
		return crateDate;
	}
	public void setCrateDate(Date crateDate) {
		this.crateDate = crateDate;
	}
	
	
	
	@Override
	public String toString() {
		return "LoveBean [id=" + id + ", result=" + result + ", crateDate=" + crateDate + "]";
	}
	
	
	
	
	
}
