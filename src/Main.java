import controller.ToyController;
import model.ToyList;
import view.ToyView;

public class Main {
    public static void main(String[] args) {
        ToyList toyList = new ToyList();
        ToyView toyView = new ToyView();
        ToyController toyController = new ToyController(toyList, toyView);

        toyList.readToysCSV(); // чтение сохраненных данных из файла

        toyController.start(); // запуск основного цикла программы

        toyList.saveToysCSV(); // сохранение данных в файл
    }
}
