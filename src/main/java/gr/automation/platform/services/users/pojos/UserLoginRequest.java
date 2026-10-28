
package gr.automation.platform.services.users.pojos;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String email;
    private String password;

}
