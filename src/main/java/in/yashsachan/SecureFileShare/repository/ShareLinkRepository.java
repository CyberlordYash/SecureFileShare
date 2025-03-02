package in.yashsachan.SecureFileShare.repository;

import in.yashsachan.SecureFileShare.model.ShareLink;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShareLinkRepository extends MongoRepository<ShareLink, String> {
    Optional<ShareLink> findByToken(String token);
}