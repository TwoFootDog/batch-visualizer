package edu.project.authorization.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoResVO {
    private CustomHeaderResVO header;
    private String userId;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private String roleName;
}
