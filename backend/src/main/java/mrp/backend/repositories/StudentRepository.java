package mrp.backend.repositories;

import mrp.backend.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository <Student, String> {
    Student findByCode(String code);
}
