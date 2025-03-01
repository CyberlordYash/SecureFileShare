package in.yashsachan.SecureFileShare.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;          // MongoDB _id
    private String username;
    private String password;
    private String mfaSecret;    // For TOTP or other MFA


    @DBRef
    private Set<Role> roles = new HashSet<>();
}
