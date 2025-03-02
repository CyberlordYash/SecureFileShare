package in.yashsachan.SecureFileShare.repository;

import in.yashsachan.SecureFileShare.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}