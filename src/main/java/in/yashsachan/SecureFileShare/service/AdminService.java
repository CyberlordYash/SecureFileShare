package in.yashsachan.SecureFileShare.service;

import in.yashsachan.SecureFileShare.model.User;
import in.yashsachan.SecureFileShare.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    public List<User> GetAllUsers()
    {
        return userRepository.findAll();
    }

    public Boolean DeleteUserByUserName(String userName)
    {
        try{
            User user=userRepository.findByUsername(userName);
            if(user==null){
                System.out.println("User note found");
                return false;
            }
            userRepository.deleteById(user.getId());
            return true;
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return false;
        }
    }
}
