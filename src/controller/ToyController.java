package controller;

import model.Toy;
import model.ToyList;
import view.ToyView;


import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ToyController {
    private final ToyList toyList;
    private final ToyList prizeToyList;
    private final ToyView toyView;

    public ToyController(ToyList toyList, ToyView toyView) {
        this.toyList = toyList;
        this.toyView = toyView;
        this.prizeToyList = toyList;
    }

    public void start() {
        boolean running = true;
        while (running) {
            try {
                int choice = toyView.showMainMenu();
                switch (choice) {
                    case 1 -> showAllToys();
                    case 2 -> addNewToy();
                    case 3 -> editToyFrequency();
                    case 4 -> playGame();
                    case 5 -> showPrizeToys();
                    case 6 -> running = false;
                    default -> toyView.showError("Неверный выбор. Попробуйте снова.");
                }
            } catch (InputMismatchException e) {
                toyView.showError("Неверный формат ввода. Попробуйте снова.");
            }
        }
    }


    private void addNewToy() {
        String name = toyView.enterToyName();
        int frequency = toyView.enterToyFrequency();
        int quantity = toyView.enterToyQuantity();
        toyList.clearToyList();
        toyList.readToysCSV();
        int id = toyList.getNextId();
        Toy newToy = new Toy(id, name, quantity, frequency);
        toyList.addToy(newToy);
        toyList.saveToysCSV();
        toyView.showSuccessMessage("Игрушка успешно добавлена с id: " + id);
    }

    private void editToyFrequency() {
        toyList.clearToyList();
        toyList.readToysCSV();
        if (!toyList.getToys().isEmpty()) {
            int id = toyView.selectToyId();
            Toy toyToEdit = toyList.getToyById(id);
            if (toyToEdit == null) {
                toyView.showError("Игрушка не найдена");
                return;
            }
            int newFrequency = toyView.enterToyFrequency();
            toyToEdit.setFrequency(newFrequency);
            toyList.updateToyList(toyList.getToys(), toyToEdit);
            toyList.saveToysCSV();
            toyView.showSuccessMessage("Частота выпадения игрушки изменена.");
        } else {
            System.out.println("У вас пока нет игрушек. Для начала добавьте пару для розыгрыша.\n");
        }

    }

    private void showAllToys() {
        toyList.clearToyList();
        toyList.readToysCSV();
        List<Toy> toys = toyList.getToys();
        if (!toys.isEmpty()) {
            toyView.showAllToys(toys);
        } else {
            System.out.println("У вас пока нет игрушек. Для начала добавьте пару для розыгрыша.\n");
        }
    }

    public void playGame() {
        toyList.clearToyList();
        toyList.readToysCSV();
        if (!toyList.getToys().isEmpty()) {
            List<Toy> toysWithHighestFrequency = getToysWithHighestFrequency();
            Toy prizeToy = selectPrizeToy(toysWithHighestFrequency);
            if (prizeToy.getQuantity() > 1) {
                toyList.decreaseToyQuantity(prizeToy);
                toyList.updateToyList(toyList.getToys(), prizeToy);
                toyList.saveToysCSV();
            } else {
                toyList.deleteToy(prizeToy);
                toyList.saveToysCSV();
            }
            prizeToyList.clearToyList();
            prizeToy.setQuantity(1);
            prizeToyList.addPrizeToy(prizeToy);
            prizeToyList.savePrizeToy();
            toyView.showWinnerMessage(prizeToy);
        } else {
            System.out.println("У вас пока нет игрушек. Для начала добавьте пару для розыгрыша.\n");
        }
    }


    private List<Toy> getToysWithHighestFrequency() {
        int maxFrequency = Collections.max(toyList.getToyFrequencies());
        return toyList.getToys().stream()
                .filter(toy -> toy.getFrequency() == maxFrequency)
                .collect(Collectors.toList());
    }

    private Toy selectPrizeToy(List<Toy> toysWithHighestFrequency) {
        int index = new Random().nextInt(toysWithHighestFrequency.size());
        return toysWithHighestFrequency.get(index);
    }

    private void showPrizeToys() {
        toyView.showPrizeToys();
    }
}


