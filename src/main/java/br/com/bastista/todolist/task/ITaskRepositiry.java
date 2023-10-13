package br.com.bastista.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepositiry extends JpaRepository<TaskModel, UUID> {

}
