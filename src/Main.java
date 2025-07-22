import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String command = scanner.nextLine();
            String taskType;

            switch (command) {
                case "1":
                    taskManager.getListOfTasks();
                    break;
                case "2":
                    taskManager.clearTasks();
                    break;
                case "3":
                    System.out.println("Введите номер задачи");
                    int taskId = scanner.nextInt();
                    scanner.nextLine();
                    if (taskManager.getTask(taskId) != null){
                        System.out.println(taskManager.getTask(taskId).toString());
                    }
                    break;
                case "4":
                    System.out.println("Выберите тип задачи: TASK, EPIC, SUBTASK");
                    taskType = scanner.nextLine();
                    Status checkStatus;
                    try {
                        TaskType type = TaskType.valueOf(taskType);//Для мгновенной проверки значения
                        switch (taskType) {
                            case "EPIC":
                                System.out.println("Введите имя эпика");
                                String epicName = scanner.nextLine();
                                System.out.println("Введите описание эпика");
                                String epicDescription = scanner.nextLine();

                                Epic epic = new Epic(epicName, epicDescription, Status.NEW, taskManager.setId());
                                taskManager.createTask(epic, TaskType.EPIC);
                                break;
                            case "SUBTASK":
                                System.out.println("Введите имя подзадачи");
                                String subtaskName = scanner.nextLine();
                                System.out.println("Введите описание подзадачи");
                                String subtaskDescription = scanner.nextLine();
                                System.out.println("Введите статус подзадачи: NEW/IN_PROGRESS/DONE");
                                String subtaskStatus = scanner.nextLine();

                                try {
                                    checkStatus = Status.valueOf(subtaskStatus);//Для мгновенной проверки значения
                                    System.out.println("Введите номер родительского эпика");
                                    int epicId = scanner.nextInt();
                                    scanner.nextLine();

                                    Subtask subtask = new Subtask(subtaskName, subtaskDescription, Status.valueOf(subtaskStatus), taskManager.setId(), epicId);
                                    taskManager.createTask(subtask, TaskType.SUBTASK);
                                } catch (IllegalArgumentException e) {
                                    System.err.println("Ошибка: строка '" + subtaskStatus + "' не соответствует ни одному из указанных значений");
                                }
                                break;
                            default:
                                System.out.println("Введите имя задачи");
                                String name = scanner.nextLine();
                                System.out.println("Введите описание задачи");
                                String description = scanner.nextLine();
                                System.out.println("Введите статус задачи: NEW/IN_PROGRESS/DONE");
                                String status = scanner.nextLine();

                                try {
                                    checkStatus = Status.valueOf(status);//Для мгновенной проверки значения
                                    Task task = new Task(name, description, Status.valueOf(status), taskManager.setId());
                                    taskManager.createTask(task, TaskType.TASK);
                                } catch (IllegalArgumentException e) {
                                    System.err.println("Ошибка: строка '" + status + "' не соответствует ни одному из указанных значений");
                                }
                                break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("Ошибка: строка '" + taskType + "' не соответствует ни одному из указанных значений");
                    }

                    break;
                case "5":
                    System.out.println("Введите номер задачи");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    if (!taskManager.tasks.containsKey(id)){
                        System.out.println("Задачи с таким номером нет");
                        break;
                    }
                    taskType = taskManager.getTask(id).getClass().toString();
                    switch (taskType) {
                        case "class Epic":
                            System.out.println("Введите имя эпика");
                            String epicName = scanner.nextLine();
                            System.out.println("Введите описание эпика");
                            String epicDescription = scanner.nextLine();

                            Epic epic = new Epic(epicName, epicDescription, taskManager.getTask(id).status, id);
                            taskManager.updateTask(epic, TaskType.EPIC);
                            break;
                        case "class Subtask":
                            System.out.println("Введите имя подзадачи");
                            String subtaskName = scanner.nextLine();
                            System.out.println("Введите описание подзадачи");
                            String subtaskDescription = scanner.nextLine();
                            System.out.println("Введите статус подзадачи: NEW/IN_PROGRESS/DONE");
                            String subtaskStatus = scanner.nextLine();

                            try {
                                checkStatus = Status.valueOf(subtaskStatus);//Для мгновенной проверки значения
                                System.out.println("Введите номер родительского эпика");
                                int parentEpicId = scanner.nextInt();
                                scanner.nextLine();

                                Subtask subtask = new Subtask(subtaskName, subtaskDescription, Status.valueOf(subtaskStatus), id, parentEpicId);
                                taskManager.updateTask(subtask, TaskType.SUBTASK);
                            } catch (IllegalArgumentException e) {
                                System.err.println("Ошибка: строка '" + subtaskStatus + "' не соответствует ни одному из указанных значений");
                            }
                            break;
                        default:
                            System.out.println("Введите имя задачи");
                            String name = scanner.nextLine();
                            System.out.println("Введите описание задачи");
                            String description = scanner.nextLine();
                            System.out.println("Введите статус задачи: NEW/IN_PROGRESS/DONE");
                            String status = scanner.nextLine();

                            try {
                                checkStatus = Status.valueOf(status);//Для мгновенной проверки значения
                                Task task = new Task(name, description, Status.valueOf(status), id);
                                taskManager.updateTask(task, TaskType.TASK);
                            } catch (IllegalArgumentException e) {
                                System.err.println("Ошибка: строка '" + status + "' не соответствует ни одному из указанных значений");
                            }
                            break;
                    }
                    break;
                case "6":
                    System.out.println("Введите номер задачи");
                    int deletedTaskId = scanner.nextInt();
                    scanner.nextLine();
                    taskManager.deleteTask(deletedTaskId);
                    break;
                case "7":
                    System.out.println("Введите номер эпика");
                    int epicId = scanner.nextInt();
                    scanner.nextLine();
                    taskManager.getEpicTasks(epicId);
                    break;
                case "8":
                    return;
                default:
                    System.out.println("Введена несуществующая команда\nПожалуйста, введите команду из списка.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Получить список всех задач");
        System.out.println("2 - Очистить список задач");
        System.out.println("3 - Получить задачу по идентификатору");
        System.out.println("4 - Создать новую задачу");
        System.out.println("5 - Обновить задачу");
        System.out.println("6 - Удалить задачу по идентификатору");
        System.out.println("7 - Отобразить все подзадачи для эпика по его идентификатору");
        System.out.println("8 - Выход");
    }
}
