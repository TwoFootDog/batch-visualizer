package org.mrbluesky.persistence;

import java.util.List;
import org.mrbluesky.vo.entity.PrevBatchInfo;
import org.mrbluesky.vo.entity.PrevBatchInfoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrevBatchInfoRepository extends JpaRepository<PrevBatchInfo, PrevBatchInfoId> {

  // find before
  List<PrevBatchInfo> findByPrevBatchInfoIdPrevBatchIdAndPrevBatchUseYn(String prevBatchId,
                                                                            String prevBatchUseYn);
  // find next
  List<PrevBatchInfo> findByPrevBatchInfoIdBatchIdAndPrevBatchUseYn(String prevBatchId,
                                                                            String prevBatchUseYn);

}
