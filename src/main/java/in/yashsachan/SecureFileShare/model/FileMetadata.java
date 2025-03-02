package in.yashsachan.SecureFileShare.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "file_metadata")

public class FileMetadata {

    @Id
    private String id;
    private String fileName;
    private String filePath;    //  path where file is stored

    // Owner reference
    @DBRef
    private User owner;
}
