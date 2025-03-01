package in.yashsachan.SecureFileShare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "share_link")
public class ShareLink {

    @Id
    private String id;
    private String token;
    private LocalDateTime expiration;
    private boolean used;

    @DBRef
    private FileMetadata file;
}
