package org.uep.task.manager;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Aplikacja do zarządzania zadaniami (Task List).
 * Pozwala na dodawanie, wyświetlanie, sortowanie, wczytywanie i zapisywanie zadań.
 */
public class TaskManagerApp {
    /**
     * Główna metoda uruchamiająca aplikację.
     * Obsługuje menu, interakcję z użytkownikiem i zlicza statystyki działań.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long startTime = System.currentTimeMillis();
        TaskService manager = new TaskService();

        if(args.length != 1) {

        }
        //loadTasks(manager, "tasks_output.txt");

        boolean running = true;
        int dodanoZadan = 0;
        int posortowano = 0;
        int wyswietlono = 0;
        int wczytanoPlikow =0;
        while (running) {
            System.out.println("\n--- org.uep.task.manager.Task List ---");
            System.out.println("1. Dodaj zadanie");
            System.out.println("2. Wyświetl zadania");
            System.out.println("3. Sortuj po priorytecie");
            System.out.println("4. Wczytaj plik");
            System.out.println("5. Zapisz i zakończ");
            System.out.println("6. Zakończ bez zapisu");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Opis:");
                    String desc = scanner.nextLine();

                    System.out.print("Termin (YYYY-MM-DD):");

                    String date = null;
                    while (true) {
                        String dateInput = scanner.nextLine();
                        try {
                            LocalDate parsedDate = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
                            date = parsedDate.toString();
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Niepoprawny format daty. Użyj formatu YYYY-MM-DD. Wpisz poprawną:");

                        }
                    }

                    int priority = 0;
                    while (true) {
                        System.out.print("Priorytet (1-5), gdzie 1 to najważniejszy, 5 to najmniej ważny: ");
                        try {
                            priority = Integer.parseInt(scanner.nextLine());
                            if (priority >= 1 && priority <= 5) {
                                break;
                            } else {
                                System.out.println("Priorytet musi być liczbą od 1 do 5.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Nieprawidłowy priorytet. Podaj liczbę całkowitą.");
                        }
                    }


                    Task task = new Task(desc, date, priority);
                    manager.addTask(task);
                    dodanoZadan++;
                    break;

                case "2":
                    manager.showTasks();
                    wyswietlono++;
                    break;

                case "3":
                    manager.sortByPriority();
                    System.out.println("Posortowano po priorytecie.");
                    posortowano++;
                    break;

                case "4":
                    System.out.println("Podaj nazwe pliku ktory chcesz wczytac:");
                    loadTasks(manager, scanner);
                    wczytanoPlikow++;
                    break;

                case "5":
                    System.out.println("Nazwa nowego pliku:");
                    saveTasks(manager, scanner.nextLine());
                    statisticsTasks(startTime, wczytanoPlikow, wyswietlono, posortowano, dodanoZadan);
                    running = false;
                    break;

                case "6":
                    statisticsTasks(startTime, wczytanoPlikow, wyswietlono, posortowano, dodanoZadan);
                    running = false;
                    break;


                default:
                    System.out.println("Nieprawidłowy wybór.");
            }
        }
    }
    /**
     * Zapisuje zadania do pliku tekstowego w formacie CSV.
     *
     * @param manager obiekt zawierający listę zadań
     * @param filename nazwa pliku do zapisu
     */
    private static void saveTasks(TaskService manager, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Task task : manager.getTasks()) {
                writer.println(task.description() + ";" +
                               task.dueDate() + ";" +
                               task.priority());
            }
            System.out.println("Zapisano do pliku.");
        } catch (IOException e) {
            System.out.println("Błąd zapisu pliku.");
        }
    }
    /**
     * Wczytuje zadania z pliku tekstowego w formacie CSV.
     * Pozwala użytkownikowi ponowić próbę lub wrócić do menu.
     *
     * @param manager obiekt, do którego mają zostać dodane wczytane zadania
     * @param scanner obiekt do pobierania danych od użytkownika
     */
    private static void loadTasks(TaskService manager, Scanner scanner) {
        while (true) {
            System.out.print("Podaj nazwę pliku z zadaniami (np. tasks.txt) lub aby wrócić do menu wpisz 'menu': ");
            String filename = scanner.nextLine();

            if (filename.equalsIgnoreCase("menu")) {
                System.out.println("Powrócono do menu.");
                break;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length == 3) {
                        String desc = parts[0];
                        String date = parts[1];
                        int priority = Integer.parseInt(parts[2]);
                        manager.addTask(new Task(desc, date, priority));
                    }
                }
                System.out.println("Wczytano plik.");
                break;
            } catch (IOException e) {
                System.out.println("Błąd odczytu pliku. Spróbuj jeszcze raz wczytac.");
            }
        }
    }

    /**
     * Wyświetlanie statystyk w konsoli
     * @param czasStartu
     * @param liczbaPlikow
     * @param liczbaWyswietlen
     * @param liczbaSortowan
     * @param liczbaZadan
     */
    private static void statisticsTasks(long czasStartu, int liczbaPlikow, int liczbaWyswietlen, int liczbaSortowan, int liczbaZadan) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - czasStartu;
        System.out.println("Statystyki: ");
        System.out.println("Czas działania programu: " + (duration / 1000.0) + " sekund.");
        System.out.println("wczytanoPlikow = " + liczbaPlikow);
        System.out.println("wyswietlono = " + liczbaWyswietlen);
        System.out.println("posortowano = " + liczbaSortowan);
        System.out.println("dodanoZadan = " + liczbaZadan);

    }
}
