public class Subtask extends Task{
    int epicId;

    Subtask(String name, String description, Status status, int id, int epicId){
        super(name, description, status, id);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
