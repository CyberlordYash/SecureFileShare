package in.yashsachan.SecureFileShare.repository;

import in.yashsachan.SecureFileShare.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String name);
}