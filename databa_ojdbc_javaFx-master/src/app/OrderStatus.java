package app;

import java.sql.Date;

public class OrderStatus {
    private int id=0;
    private int idBasket=0;
    private Date dtStage=Date.valueOf("2012-03-10");
    private String shipper="";
    private String shippingNum="";

    @Override
    public String toString(){
        return String.format("id: %d \nidBasket: %d \ndtStage: %s \nshipper: %s  \nshippingNum: %s ", id, idBasket, dtStage, shipper, shippingNum);
    }

    public OrderStatus(int id, int idBasket, Date dtStage, String shipper, String shippingNum) {
        this.id = id;
        this.idBasket = idBasket;
        this.dtStage = dtStage;
        this.shipper = shipper;
        this.shippingNum = shippingNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(int idBasket) {
        this.idBasket = idBasket;
    }

    public Date getDtStage() {
        return dtStage;
    }

    public void setDtStage(Date dtStage) {
        this.dtStage = dtStage;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShippingNum() {
        return shippingNum;
    }

    public void setShippingNum(String shippingNum) {
        this.shippingNum = shippingNum;
    }
}
