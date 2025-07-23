package manager;

import model.*;
import util.enums.*;

import java.util.HashMap;
import java.util.Objects;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    Integer maxId = 0;

    // Получение списка всех задач
    public void getListOfTasks(){
        for (Task task : tasks.values()){
            System.out.println(task.toString());
        }
    }

    // Удаление всех задач
    public void clearTasks(){
        tasks.clear();
    }

    // Получение задачи по идентификатору
    public Task getTask(int id){
        return tasks.get(id);
    }

    // Создание задачи
    public void createTask(Task task, TaskType taskType){
        if (!tasks.containsValue(task)){
            if (Objects.requireNonNull(taskType) == TaskType.SUBTASK) {
                Subtask subtask = (Subtask) task;
                if (tasks.containsKey(subtask.getEpicId()) && getTask(subtask.getEpicId()).getClass().toString().equals("class model.Epic")){
                    tasks.put(task.getId(), subtask);
                    Epic epic = (Epic) getTask(subtask.getEpicId());
                    epic.addSubtask(task.getId());
                    updateEpicStatus(subtask.getEpicId());
                } else {
                    System.out.println("Нет эпика с указанным номером");
                }
            } else {
                tasks.put(task.getId(), task);
            }
        }
    }

    // Обновление задачи
    public void updateTask(Task task, TaskType taskType){
        if (tasks.containsValue(task)){
            if (Objects.requireNonNull(taskType) == TaskType.SUBTASK) {
                Subtask subtask = (Subtask) task;
                if (tasks.containsKey(subtask.getEpicId()) && getTask(subtask.getEpicId()).getClass().toString().equals("class model.Epic")) {
                    Subtask oldSubtask = (Subtask) getTask(subtask.getId());

                    // Перенос подзадачи в другой эпик
                    if (subtask.getEpicId() != oldSubtask.getEpicId()) {
                        //Удаляем задачу в эпике
                        Epic epic = (Epic) getTask(oldSubtask.getEpicId());
                        epic.deleteSubtask(oldSubtask.getId());
                        updateEpicStatus(oldSubtask.getEpicId());

                        // Обновляем/добавляем задачу в эпике
                        tasks.put(subtask.getId(), subtask);
                        Epic epicNew = (Epic) getTask(subtask.getEpicId());
                        epicNew.addSubtask(task.getId());
                        updateEpicStatus(subtask.getEpicId());
                    } else{
                        // Обновляем/добавляем задачу в эпике
                        tasks.put(subtask.getId(), subtask);
                        updateEpicStatus(subtask.getEpicId());
                    }
                } else {
                    System.out.println("Нет эпика с указанным номером");
                }
            } else {
                tasks.put(task.getId(), task);
            }
        } else{
            System.out.println("Задачи с таким номером нет");
        }
    }

    // Удаление задачи по идентификатору
    public void deleteTask(int id){
        // При удалении эпика все подзадачи также удаляются
        if (tasks.containsKey(id)  && getTask(id).getClass().toString().equals("class model.Epic")) {
            Epic epic = (Epic) getTask(id);
            for (Task task: tasks.values()){
                if (task.getClass().toString().equals("class model.Subtask")){
                    Subtask subtask = (Subtask) task;
                    if (subtask.getEpicId() == epic.getId()) {
                        deleteTask(subtask.getId());
                    }
                }
            }
        }
        //Удаление подзадачи из эпика
        if (tasks.containsKey(id)  && getTask(id).getClass().toString().equals("class model.Subtask")){
            Subtask subtask = (Subtask) getTask(id);
            Epic epic = (Epic) getTask(subtask.getEpicId());
            epic.deleteSubtask(id);
            updateEpicStatus(epic.getId());
        }

        tasks.remove(id);
    }

    // Получение списка всех подзадач определённого эпика
    public void getEpicTasks(int epicId){
        if (tasks.containsKey(epicId) && getTask(epicId).getClass().toString().equals("class model.Epic")){
            Epic epic = (Epic) getTask(epicId);
            for (Task task: tasks.values()){
                if (task.getClass().toString().equals("class model.Subtask")){
                    Subtask subtask = (Subtask) task;
                    if (subtask.getEpicId() == epic.getId()) {
                        System.out.println(subtask);
                    }
                }
            }
        } else{
            System.out.println("Эпика с таким номером не существует");
        }
    }

    // Рассчитать id для новой задачи
    public Integer setId(){
        maxId++;
        return maxId;
    }

    // Обновление статуса эпика в соответствии с его подзадачами
    private void updateEpicStatus(int epicId){
        Epic epic = (Epic) getTask(epicId);
        Status newStatus = Status.IN_PROGRESS;
        int doneCount = 0;
        int newCount = 0;
        int totalCount = epic.getSubtasks().size();

        for (Task task: tasks.values()){
            if (task.getClass().toString().equals("class model.Subtask")){
                Subtask subtask = (Subtask) task;
                Status status = subtask.getStatus();
                if (subtask.getEpicId() == epic.getId()){
                    switch (status) {
                        case DONE:
                            doneCount++;
                            break;
                        case IN_PROGRESS:
                            break;
                        case NEW:
                            newCount++;
                            break;
                    }
                }

            }
        }
        if (totalCount == 0){
            newStatus = Status.NEW;
        }else if (doneCount == totalCount){
            newStatus = Status.DONE;
        } else if (totalCount == newCount){
            newStatus = Status.NEW;
        }
        epic.setStatus(newStatus);
        updateTask(epic, TaskType.EPIC);
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }
}
