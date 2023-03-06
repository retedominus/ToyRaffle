package controller;
import model.Toy;
import model.ToyList;
import view.ToyView;


import java.util.Collections;
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
        }
    }

    private void addNewToy() {
        String name = toyView.enterToyName();
        int frequency = toyView.enterToyFrequency();
        int quantity = toyView.enterToyQuantity();
        int id = toyList.getNextId();
        Toy newToy = new Toy(id, name, quantity, frequency);
        toyList.addToy(newToy);
        toyView.showSuccessMessage("Игрушка успешно добавлена с id: " + id);
    }

    private void editToyFrequency() {
        int id = toyView.selectToyId();
        Toy toyToEdit = toyList.getToyById(id);
        if (toyToEdit == null) {
            toyView.showError("Игрушка не найдена");
            return;
        }
        int newFrequency = toyView.enterToyFrequency();
        toyToEdit.setFrequency(newFrequency);
        toyView.showSuccessMessage("Частота выпадения игрушки изменена.");
    }

    private void showAllToys() {
        List<Toy> toys = toyList.getToys();
        if (toys.isEmpty()) {
            toyView.showError("Игрушки не найдены.");
        } else {
            toyView.showAllToys(toys);
        }
    }

    public void playGame() {
        List<Toy> toysWithHighestFrequency = getToysWithHighestFrequency();
        Toy prizeToy = selectPrizeToy(toysWithHighestFrequency);
        if(prizeToy.getQuantity() > 1) {
            toyList.decreaseToyQuantity(prizeToy);
        } else {
            toyList.deleteToy(prizeToy);
        }
        prizeToyList.addPrizeToy(prizeToy);
        prizeToyList.savePrizeToy(prizeToy);
        toyView.showWinnerMessage(prizeToy);
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
        prizeToyList.readPrizeToyFile();
        toyView.showPrizeToys();
    }
}


