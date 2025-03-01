package in.yashsachan.SecureFileShare.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class Role {

    @Id
    private String id;      // MongoDB _id
    private String name;    // "ROLE_ADMIN", "ROLE_USER", "ROLE_GUEST"
}
