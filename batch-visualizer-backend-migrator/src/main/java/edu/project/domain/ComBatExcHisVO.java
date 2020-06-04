package edu.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComBatExcHisVO {
    private String mbrshPgmId;
    private String batExeSeq;
    private String batId;
    private String batExeSts;
    private String batExeExpl;
    private String batExeFrDt;
    private String batExeToDt;
    private long   batExeReadCnt;
    private long   batExeProcCnt;
    private long   batExeEcptCnt;
    private String batExeInpFileNm;
    private String batExePrtFileNm;
    private String regrId;
    private Date   regDt;
    private String updrId;
    private Date   updDt;
    private String mstBatId;
    private String frTrcNo;
    private String toTrcNo;
}
