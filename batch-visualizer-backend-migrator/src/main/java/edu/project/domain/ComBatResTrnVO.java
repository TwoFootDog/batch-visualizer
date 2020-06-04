package edu.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComBatResTrnVO {
    private String mbrshPgmId;
    private String fileId;
    private String fileNm;
    private String workDy;
    private String workFrTm;
    private String workSts;
    private String workToTm;
    private String rcvDy;
    private String rcvTm;
    private long   tgtCnt;
    private long   errCnt;
    private String resFileNm1;
    private String resFileNm2;
    private String brdReqNo;
    private String regrId;
    private Date   regDt;
    private String updrId;
    private Date   updDt;
}
