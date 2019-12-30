package leaveManagement;

public class EmployeeLeaveBalanceDetails {
	private String empId;
	private int lop;
	private int optionalLeave;
	private int otherLeave;
	private int balance;
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public int getLop() {
		return lop;
	}
	public void setLop(int lop) {
		this.lop = lop;
	}
	public int getOptionalLeave() {
		return optionalLeave;
	}
	public void setOptionalLeave(int optionalLeave) {
		this.optionalLeave = optionalLeave;
	}
	public int getOtherLeave() {
		return otherLeave;
	}
	public void setOtherLeave(int otherLeave) {
		this.otherLeave = otherLeave;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "EmployeeLeaveBalanceDetails [empId=" + empId + ", lop=" + lop + ", optionalLeave=" + optionalLeave
				+ ", otherLeave=" + otherLeave + ", balance=" + balance + "]";
	}
	public EmployeeLeaveBalanceDetails() {
		super();
		this.empId = empId;
		this.lop = lop;
		this.optionalLeave = optionalLeave;
		this.otherLeave = otherLeave;
		this.balance = balance;
	}
	
}
