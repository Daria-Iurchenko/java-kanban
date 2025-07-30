package model;
import util.enums.*;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, Status status, int id, int epicId){
        super(name, description, status, id);
        if (epicId != id){
            this.epicId = epicId;
        } else{
            System.out.println("Номер эпика должен отличаться от номера подзадачи. Номер эпика не заполнен.");
        }
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", epicId=" + epicId +
                '}';
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        if (epicId != id){
            this.epicId = epicId;
        } else{
            System.out.println("Номер эпика должен отличаться от номера подзадачи. Номер эпика не скорректирован.");
        }
    }
}
