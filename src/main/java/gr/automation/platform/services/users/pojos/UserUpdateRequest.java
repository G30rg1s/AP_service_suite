
package gr.automation.platform.services.users.pojos;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String fatherName;
    private String motherName;
    private Integer age;
}
