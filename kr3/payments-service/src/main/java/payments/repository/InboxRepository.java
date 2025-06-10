package payments.repository;

import payments.entity.InboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InboxRepository extends JpaRepository<InboxEvent, Long> {
}
