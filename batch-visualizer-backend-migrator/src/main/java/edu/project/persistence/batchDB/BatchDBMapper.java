package edu.project.persistence.batchDB;

import edu.project.domain.*;

import java.util.List;

public interface BatchDBMapper {
    public int setAllAprBatSchMst(AprBatSchMstVO aprBatSchMstVO);
    public int setAllAprBatProLst(AprBatProLstVO aprBatProLstVO);
    public int setAllAprPrfBatMst(AprPrfBatMstVO aprPrfBatMstVO);
    public int setComBatfileTrn(ComBatFileTrnVO comBatFileTrnVO);
    public int setComBatresTrn(ComBatResTrnVO comBatResTrnVO);
    public int setAllBatDatFlgMst(BatDatFlgMstVO batDatFlgMstVO);
    public int setAllComBatSchMst(ComBatSchMstVO comBatSchMstVO);
    public int setAllComPrfBatMst(ComPrfBatMstVO comPrfBatMstVO);
    public int setComBatExcHis(ComBatExcHisVO comBatExcHisVO);

}
