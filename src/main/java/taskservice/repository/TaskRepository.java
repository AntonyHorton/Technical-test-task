package taskservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import taskservice.entity.Task;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    //Resolve N+1
    @Override
    @EntityGraph(attributePaths = "assignee")
    Page<Task> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "assignee")
    Optional<Task> findById(Long id);
}
