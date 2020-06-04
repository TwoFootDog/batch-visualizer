package edu.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AprBatSchMstVO {
    private String mbrshPgmId;
    private String mstBatId;
    private String mstBatNm;
    private String hostNm;
    private String inputFileYn;
    private String fileId;
    private String hnmFileNm;
    private String preBatExistYn;
    private String fileAutoSendYn;
    private long   multiProcCnt;
    private String reportTyp;
    private String aplYn;
    private String regrId;
    private Date   regDt;
    private String updrId;
    private Date   updDt;
}
