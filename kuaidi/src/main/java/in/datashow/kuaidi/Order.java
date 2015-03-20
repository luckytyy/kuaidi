package in.datashow.kuaidi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

	public Order(String orderNum) {
		this.orderNum = orderNum;
	}

	private List<OrderRecord> records;
	private String orderNum;
	private String info;

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public Order addRecords(OrderRecord record) {
		if (records == null) {
			records = new ArrayList<OrderRecord>();
		}
		records.add(record);
		return this;
	}

	public List<OrderRecord> getRecords() {
		return records;
	}

	public void setRecords(List<OrderRecord> records) {
		this.records = records;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public static class OrderRecord {
		private Date datetime;
		private String address;
		private String status;

		public OrderRecord() {
		}
		
		public OrderRecord(Date datetime, String address, String status) {
			super();
			this.datetime = datetime;
			this.address = address;
			this.status = status;
		}

		public Date getDatetime() {
			return datetime;
		}

		public void setDatetime(Date datetime) {
			this.datetime = datetime;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	}

	
	public Order() {
	}
	
}
