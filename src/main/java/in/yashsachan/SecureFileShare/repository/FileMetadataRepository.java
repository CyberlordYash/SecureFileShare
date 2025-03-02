package in.yashsachan.SecureFileShare.repository;

import in.yashsachan.SecureFileShare.model.FileMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileMetadataRepository extends MongoRepository<FileMetadata, String> {
}