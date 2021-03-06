package edu.project.authorization.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationReqVO {
    private String userId;
    private String userPasswd;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private String roleName;
    private String page;
}
