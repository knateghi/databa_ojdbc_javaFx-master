package app;

public class Product {
    private int id=0;
    private String name="";
    private String desc="";
    private String img="";
    private  double price=0.0;
    private  int stock=0;

    public Product() {
    }

    public Product(int id, String name, String desc, String img, double price, int stock) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.img = img;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString(){
        return String.format("id: %d \nname: %s \ndescription: %s \nimage: %s \nprice: %f  \nstock: %d ", id, name, desc, img, price, stock);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
