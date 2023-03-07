package view;

import model.Toy;
import model.ToyList;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ToyView {
    private final Scanner scanner;

    public ToyView() {
        scanner = new Scanner(System.in);
    }

    public int showMainMenu() {
        System.out.println("""
                1. Посмотреть список игрушек
                2. Добавить новую игрушку
                3. Изменить частоту выпадения игрушки
                4. Провести розыгрыш
                5. Показать все выигравшие игрушки
                6. Выйти
                Выберите действие:\040""");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return 0;
        }
    }

    public int selectToyId() {
        while (true) {
            try {
                System.out.println("Введите id игрушки: ");
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // очистить буфер после некорректного ввода
                System.out.println("Ошибка: введите целое число!");
            }
        }
    }

    public String enterToyName() {
        System.out.println("Введите навзвание игрушки: ");
        return scanner.next();
    }

    public int enterToyQuantity() {
        while (true) {
            try {
                System.out.println("Введите количество игрушек: ");
                int quantity = scanner.nextInt();
                if (quantity > 0) {
                    return quantity;
                } else {
                    System.out.println("Ошибка: введите число больше 0!");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Ошибка: введите целое число!");
            }
        }
    }

    public int enterToyFrequency() {
        while (true) {
            try {
                System.out.println("Введите частоту выпадения игрушки (вес в % от 100): ");
                int quantity = scanner.nextInt();
                if (quantity > 0) {
                    return quantity;
                } else {
                    System.out.println("Ошибка: введите число больше 0!");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Ошибка: введите целое число!");
            }
        }
    }

    public void showPrizeToys() {
        ToyList prizeToys = new ToyList();
        prizeToys.readPrizeToyFile();
        System.out.println("Выданы следующие игрушки:");
        for (Toy toy : prizeToys.getToys()) {
            System.out.println("Игрушка: " + toy.getName() + " (id: " + toy.getId() + ") " +
                    "Количество: " + toy.getQuantity());
        }
        System.out.println();
    }


    public void showError(String errorMessage) {
        System.out.println("Ошибка: " + errorMessage + "\n");
    }

    public void showSuccessMessage(String message) {
        System.out.println("Успешно: " + message + "\n");
    }

    public void showAllToys(List<Toy> toys) {
        System.out.println("Вот все игрушки:\n");
        for (Toy toy : toys) {
            System.out.println("ID: " + toy.getId() + ", Имя: " + toy.getName() +
                    ", Количество: " + toy.getQuantity() + ", Частота выпадения: "
                    + toy.getFrequency());
        }
        System.out.println();
    }

    public void showWinnerMessage(Toy prizeToy) {
        System.out.println("Поздравляю! Вы выиграли " + prizeToy.getName() + "!\n");
    }

}
