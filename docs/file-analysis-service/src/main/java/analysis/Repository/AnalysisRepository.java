package analysis.Repository;

import analysis.Entity.AnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<AnalysisEntity, String> {}
