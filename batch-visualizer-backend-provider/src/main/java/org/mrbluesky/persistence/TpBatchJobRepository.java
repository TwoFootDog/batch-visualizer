package org.mrbluesky.persistence;

import java.util.List;
import org.mrbluesky.vo.entity.TpBatchJob;
import org.mrbluesky.vo.entity.TpBatchJobId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TpBatchJobRepository extends JpaRepository<TpBatchJob, TpBatchJobId> {

  Page<TpBatchJob> findAll(Pageable pageable);
  Page<TpBatchJob> findByBatchJobId(TpBatchJobId tpBatchJobId, Pageable pageable);
  Page<TpBatchJob> findByHostname(String hostname, Pageable pageable);

}
