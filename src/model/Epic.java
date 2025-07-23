package model;

import java.util.ArrayList;
import util.enums.*;

public class Epic extends Task{
    private ArrayList <Integer> subtasks = new ArrayList<>();

    public Epic (String name, String description, Status status, int id){
        super(name, description, status, id);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtasks=" + subtasks +
                '}';
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Integer> subtasks) {
        this.subtasks = subtasks;
    }

    //проблема с тем, по индексу удалится или нет
    public void deleteSubtask(int subtaskNum){
        this.subtasks.remove((Object) subtaskNum);
    }

    public void addSubtask(int subtaskNum){
        this.subtasks.add(subtaskNum);
    }

}
