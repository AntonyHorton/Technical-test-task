package taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taskservice.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {}
