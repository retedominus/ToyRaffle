package model;

public class Toy {
    private final int id;
    private final String name;
    private int quantity;
    private int frequency;

    public Toy(int id, String name, int qty, int frequency) {
        this.id = id;
        this.name = name;
        this.quantity = qty;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setQuantity(int quantity) {
        if(quantity < 1) {
            System.out.println("Количество не может быть менее 1.");
        } else {
            this.quantity = quantity;
        }
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%d,%d",
                id, name, quantity, frequency);
    }
}
