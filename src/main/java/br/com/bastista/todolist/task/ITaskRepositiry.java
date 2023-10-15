package br.com.bastista.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ITaskRepositiry extends JpaRepository<TaskModel, UUID> {

    List<TaskModel> findByIdUSer(UUID idUSer);
}
