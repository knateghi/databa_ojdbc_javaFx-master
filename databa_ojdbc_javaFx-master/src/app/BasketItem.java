package app;

public class BasketItem implements Comparable< BasketItem >{
    private int id=0;
    private int idBasket=0;
    private double price = 0.0;
    private int quantity = 1;
    private int sizeCode = 2;
    private int formCode = 3;
    private Product product = new Product();
    private String productName = "";
    private String productDesc = "";


    public BasketItem( int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
        productName = product.getName();
                productDesc = product.getDesc();
        price = product.getPrice();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(int sizeCode) {
        this.sizeCode = sizeCode;
    }

    public int getFormCode() {
        return formCode;
    }

    public void setFormCode(int formCode) {
        this.formCode = formCode;
    }


    @Override
    public int compareTo(BasketItem o) {
        return this.getId() - o.getId();
    }
}
