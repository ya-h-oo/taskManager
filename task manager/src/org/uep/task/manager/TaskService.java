package org.uep.task.manager;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Klasa TaskService zarządza kolekcją zadań.
 * Umożliwia dodawanie, wyświetlanie, sortowanie oraz pobieranie listy zadań.
 */
public class TaskService {
    private final ArrayList<Task> tasks;

    /**
     * Konstruktor tworzący pustą listę zadań.
     */
    public TaskService() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Dodaje nowe zadanie do listy.
     *
     * @param task obiekt zadania do dodania
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Wyświetla wszystkie zadania w konsoli.
     */
    public void showTasks() {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    /**
     * Sortuje zadania według priorytetu rosnąco (1 = najwyższy priorytet).
     */
    public void sortByPriority() {
        tasks.sort(Comparator.comparingInt(Task::priority));
    }

    /**
     * Zwraca aktualną listę zadań.
     *
     * @return lista zadań
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}

