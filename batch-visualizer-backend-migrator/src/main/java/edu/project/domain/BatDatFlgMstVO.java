package edu.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatDatFlgMstVO {
    private String mbrshPgmId;
    private String fileId;
    private String hnmFileNm;
    private String telgrmNo;
    private String telgrmTyp;
    private String fileFg;
    private String organCd;
    private String avlFrDy;
    private String avlToDy;
    private String batBrdFileFrmFg;
    private String ifCycleFg;
    private String ifChnlFg;
    private String sendRecvFg;
    private String fileType;
    private String fileSendYn;
    private String sendHostNm;
    private String mapFileFg;
    private String mapFileId;
    private String lmtAplYn;
    private String resRplYn;
    private String resFileNm;
    private String resFileTyp;
    private String rcvCntFg;
    private long   recLen;
    private long   processExeCnt;
    private String reqFilePathNm;
    private String resFilePathNm;
    private String monoCnts;
    private String regrId;
    private Date   regDt;
    private String updrId;
    private Date   updDt;
    private String rcvOrganCd;
    private String rcvSvrIp;
    private String bilIfKey;
}
