package org.mrbluesky.persistence;

import java.util.List;
import org.mrbluesky.vo.entity.BoBatchJob;
import org.mrbluesky.vo.entity.BoBatchJobId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoBatchJobRepository extends JpaRepository<BoBatchJob, BoBatchJobId> {

  Page<BoBatchJob> findAll(Pageable pageable);
  Page<BoBatchJob> findByBatchJobId(BoBatchJobId boBatchJobId, Pageable pageable);
  List<BoBatchJob> findByBatchUseFlag(String batchUseFlag);

}
