package edu.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AprBatProLstVO {
    private String mbrshPgmId;
    private String mstBatId;
    private long   workSeq;
    private long   multiProcSeq;
    private String pgmId;
    private String pgmCd;
    private String batExeFilePathNm;
    private String logFileDesc;
    private String logFilePathNm;
    private String batParamDm;
    private long   batParamCnt;
    private String batParamValCnts1;
    private String batParamValCnts2;
    private String batParamValCnts3;
    private String batParamValCnts4;
    private String batParamValCnts5;
    private String batParamValCnts6;
    private String batParamValCnts7;
    private String batParamValCnts8;
    private String batParamValCnts9;
    private String batParamValCnts10;
    private String regrId;
    private Date   regDt;
    private String updrId;
    private Date   updDt;
}
