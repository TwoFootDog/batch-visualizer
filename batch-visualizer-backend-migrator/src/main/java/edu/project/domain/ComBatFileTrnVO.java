package edu.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComBatFileTrnVO {
    private String mbrshPgmId;
    private String fileId;
    private String fileNm;
    private String transRcvFg;
    private String transOrganCd;
    private String rcvOrganCd;
    private String stsCd;
    private String workSts;
    private Date   sendFrDt;
    private Date   sendToDt;
    private long   fileSz;
    private long   totRecCnt;
    private long   sendRecCnt;
    private String msg;
    private String regrId;
    private Date   regDt;
    private String updrId;
    private Date   updDt;
    private String fileName;
    private String rcvDy;
}
