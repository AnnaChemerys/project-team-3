package dao;

public enum FileNames {

    ORDERS("orders.ser"), PRODUCTS("products.ser"), USERS("users.ser");

    private final String fileName;

    FileNames(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
