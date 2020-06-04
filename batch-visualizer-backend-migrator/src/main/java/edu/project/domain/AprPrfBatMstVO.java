package edu.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AprPrfBatMstVO {
    private String mbrshPgmId;
    private String mstBatId;
    private String preMstBatId;
    private String aplYn;
    private String regrId;
    private String regDt;
    private String updrId;
    private String updDt;
}
