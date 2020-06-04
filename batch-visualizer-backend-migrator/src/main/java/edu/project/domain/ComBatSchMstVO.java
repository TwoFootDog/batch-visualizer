package edu.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComBatSchMstVO {
    private String mbrshPgmId;
    private String batId;
    private String batNm;
    private String batExeFilePathNm;
    private String batTyp;
    private Date   batExeDt;
    private String batExpl;
    private String batUseFg;
    private String batWorkFg;
    private long   batParamCnt;
    private String batParamVAlCnts1;
    private String batParamVAlCnts2;
    private String batParamVAlCnts3;
    private String batParamVAlCnts4;
    private String batParamVAlCnts5;
    private String batParamVAlCnts6;
    private String batParamVAlCnts7;
    private String batParamVAlCnts8;
    private String batParamVAlCnts9;
    private String batParamVAlCnts10;
    private String regrId;
    private Date   regDt;
    private String updrId;
    private Date   updDt;
    private String patYn;
    private String patWorkFg;
}
