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

    public void addPrizeToy(Toy prizeToy) {
        toys.add(prizeToy);
    }

    public void saveToysCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("toys.csv", false))) {
            StringBuilder sb = new StringBuilder();
            for (Toy toy : toys) {
                sb.append(toy.toString());
                sb.append(System.lineSeparator());
            }
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void savePrizeToy() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("prizeToys.csv", true))) {
            StringBuilder sb = new StringBuilder();
            for (Toy toy : toys) {
                sb.append(toy.toString());
                sb.append(System.lineSeparator());
            }
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readPrizeToyFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("prizeToys.csv"))) {
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
            System.out.println("Призовые игрушки не найдены. Проведите розыгрыш.\n");
        }

    }

    public void readToysCSV() {
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
            System.out.println("\n");
        }

    }

    public List<Toy> getToys() {
        return toys;
    }

    public int getNextId() {
        return nextId;
    }

    public Toy getToyById(int id) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                return toy;
            }
        }
        return null;
    }

    public void deleteToy(Toy toyToDelete) {
        toys.remove(toyToDelete);
    }

    public void clearToyList() {
        toys.clear();
    }

    public void decreaseToyQuantity(Toy toy) {
        toy.setQuantity(toy.getQuantity() - 1);
    }

    public List<Integer> getToyFrequencies() {
        List<Integer> frequencies = new ArrayList<>();
        for (Toy toy : toys) {
            frequencies.add(toy.getFrequency());
        }
        return frequencies;
    }

    public void updateToyList(List<Toy> toyList, Toy updatedToy) {
        for (int i = 0; i < toyList.size(); i++) {
            if (toyList.get(i).getId() == updatedToy.getId()) {
                toyList.set(i, updatedToy);
                break;
            }
        }
    }


}
