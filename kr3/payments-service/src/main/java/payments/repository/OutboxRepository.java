package payments.repository;

import payments.entity.OutboxEvent_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent_, Long> {
    List<OutboxEvent_> findBySentFalse();
}
