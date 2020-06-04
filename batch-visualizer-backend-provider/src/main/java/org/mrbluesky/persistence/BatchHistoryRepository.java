package org.mrbluesky.persistence;


import java.util.List;
import org.mrbluesky.vo.entity.BatchHistory;
import org.mrbluesky.vo.entity.BatchHistoryId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchHistoryRepository extends
    JpaRepository<BatchHistory, BatchHistoryId> {

  Page<BatchHistory> findAll(Pageable pageable);
  Page<BatchHistory> findByBatchId(String batchId, Pageable pageable);

}
