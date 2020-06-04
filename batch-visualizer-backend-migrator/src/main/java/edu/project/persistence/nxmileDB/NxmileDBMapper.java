package edu.project.persistence.nxmileDB;

import edu.project.domain.*;

import java.util.List;

public interface NxmileDBMapper {
    public AprBatSchMstVO getAllAprBatSchMst();
    public AprBatProLstVO getAllAprBatProLst();
    public AprPrfBatMstVO getAllAprPrfBatMst();
    public ComBatFileTrnVO getComBatFileTrn();
    public ComBatResTrnVO getComBatResTrn();
    public BatDatFlgMstVO getAllBatDatFlgMst();
    public ComBatSchMstVO getAllComBatSchMst();
    public ComPrfBatMstVO getAllComPrfBatMst();
    public ComBatExcHisVO getComBatExcHis();
}
