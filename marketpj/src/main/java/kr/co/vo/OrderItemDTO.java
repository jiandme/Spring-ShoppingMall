package kr.co.vo;

import java.util.List;

public class OrderItemDTO {
	
	/* �ֹ� ��ȣ */
	private String orderId;
	
	/* ��ǰ ��ȣ */
    private int bookId;
    
	/* �ֹ� ���� */
    private int bookCount;
    
	/* vam_orderItem �⺻Ű */
    private int orderItemId;
    
	/* ��ǰ �� �� ���� */
    private int bookPrice;
    
	/* ��ǰ ���� �� */
    private double bookDiscount;
    
	/* ��ǰ �Ѱ� ���� �� ȹ�� ����Ʈ */
    private int savePoint;
    
	/* DB���̺� ���� ���� �ʴ� ������ */
    
	/* ���� ����� ���� */
    private int salePrice;
    
	/* �� ����(���� ����� ���� * �ֹ� ����) */
    private int totalPrice;
    
	/* �� ȹ�� ����Ʈ(��ǰ �Ѱ� ���� �� ȹ�� ����Ʈ * ����) */
    private int totalSavePoint;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getBookCount() {
		return bookCount;
	}

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}

	public double getBookDiscount() {
		return bookDiscount;
	}

	public void setBookDiscount(double bookDiscount) {
		this.bookDiscount = bookDiscount;
	}

	public int getSavePoint() {
		return savePoint;
	}

	public void setSavePoint(int savePoint) {
		this.savePoint = savePoint;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalSavePoint() {
		return totalSavePoint;
	}

	public void setTotalSavePoint(int totalSavePoint) {
		this.totalSavePoint = totalSavePoint;
	}
    
	public void initSaleTotal() {
		this.salePrice = (int) (this.bookPrice * (1-this.bookDiscount));
		this.totalPrice = this.salePrice*this.bookCount;
		this.savePoint = (int)(Math.floor(this.salePrice*0.05));
		this.totalSavePoint =this.savePoint * this.bookCount;
	}

	@Override
	public String toString() {
		return "OrderItemDTO [orderId=" + orderId + ", bookId=" + bookId + ", bookCount=" + bookCount + ", orderItemId="
				+ orderItemId + ", bookPrice=" + bookPrice + ", bookDiscount=" + bookDiscount + ", savePoint="
				+ savePoint + ", salePrice=" + salePrice + ", totalPrice=" + totalPrice + ", totalSavePoint="
				+ totalSavePoint + "]";
	}
	
	
}
