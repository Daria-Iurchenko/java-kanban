package manager;

import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList <Task> history = new ArrayList<>();

    //Получение списка просмотренных задач
    @Override
    public ArrayList<Task> getHistory(){
        return history;
    }

    //Добавление задачи в список просмотренных
    @Override
    public void add(Task task){
        history.add(task);
        if (history.size()>10){
            history.removeFirst();
        }
    }
}
