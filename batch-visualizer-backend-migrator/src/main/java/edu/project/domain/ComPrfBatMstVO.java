package edu.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComPrfBatMstVO {
    private String mbrshPgmId;
    private String batId;
    private String prevBatId;
    private String prevBatUseYn;
    private String regrId;
    private Date   regDt;
    private String updrId;
    private Date   updDt;
}
