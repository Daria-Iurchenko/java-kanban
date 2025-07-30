package model;

import org.junit.jupiter.api.Test;
import util.enums.Status;
import static org.junit.jupiter.api.Assertions.*;

public class SubtaskTest {

    @Test
    //Subtask нельзя сделать своим же эпиком
    public void shouldBeNoActionWhenEpicAndSubtaskHaveSameId() {
        Subtask subtask = new Subtask("name2", "description2", Status.IN_PROGRESS, 1, 2);
        subtask.setEpicId(1);
        assertEquals(2, subtask.getEpicId());
    }

    @Test
    //Subtask нельзя сделать своим же эпиком
    public void shouldBeNoActionWhenEpicAndSubtaskHaveSameIdWhenSubtaskInit() {
        Subtask subtask = new Subtask("name2", "description2", Status.IN_PROGRESS, 1, 1);
        assertEquals(0, subtask.getEpicId());
    }
}