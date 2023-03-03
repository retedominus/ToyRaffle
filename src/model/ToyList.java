package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ToyList {
    private final List<Toy> toys;
    private int nextId;

    public ToyList() {
        this.toys = new ArrayList<>();
        nextId = 1;
    }

    public void addToy(Toy toy) {
        toys.add(toy);
        nextId++;
    }

    public void saveToys() {
        try (PrintWriter writer = new PrintWriter(new File("toys.csv"))) {
            StringBuilder sb = new StringBuilder();
            sb.append("id, name, qty, frequency");
            sb.append(System.lineSeparator());
            for (Toy toy : toys) {
                sb.append(toy.toString());
                sb.append(System.lineSeparator());
            }
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readToys() {
        try (BufferedReader br = new BufferedReader(new FileReader("toys.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                String name = values[1];
                int quantity = Integer.parseInt(values[2]);
                int frequency = Integer.parseInt(values[3]);
                toys.add(new Toy(id, name, quantity, frequency));
                if (id >= nextId) {
                    nextId = id + 1;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Toy> getToys() {
        return toys;
    }

    public int getNextId() {
        return nextId;
    }
}