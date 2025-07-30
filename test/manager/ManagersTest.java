package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    //утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров
    public void differentClassesShouldBeEqualWhenSameId() {
        Assertions.assertNotNull(Managers.getDefault());
        // Проверяем, что он в рабочем состоянии (например, может выполнять операции)
        InMemoryTaskManager taskManager = (InMemoryTaskManager) Managers.getDefault();
        assertDoesNotThrow(taskManager::getListOfTasks);

        assertNotNull(Managers.getDefaultHistory());
        // Проверяем, что он в рабочем состоянии (например, может выполнять операции)
        InMemoryHistoryManager historyManager;
        historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();
        assertDoesNotThrow(historyManager::getHistory);
    }
}