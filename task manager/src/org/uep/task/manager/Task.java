package org.uep.task.manager;

public record Task(String description, String dueDate, int priority) {
}


//public class Task {
//    private String description;
//    private String dueDate;
//    private int priority;
//
//    public Task(String description, String dueDate, int priority) {
//        this.description = description;
//        this.dueDate = dueDate;
//        this.priority = priority;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getDueDate() {
//        return dueDate;
//    }
//
//    public int getPriority() {
//        return priority;
//    }
//
//    @Override
//    public String toString() {
//        return "Opis: " + description + ", Termin: " + dueDate + ", Priorytet: " + priority;
//    }
//}

