package in.yashsachan.SecureFileShare.service;

import in.yashsachan.SecureFileShare.model.FileMetadata;
import in.yashsachan.SecureFileShare.model.User;
import in.yashsachan.SecureFileShare.repository.FileMetadataRepository;
import in.yashsachan.SecureFileShare.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileMetadataRepository fileMetadataRepository;

    public List<User> GetAllUsers()
    {
        return userRepository.findAll();
    }

    public Boolean DeleteUserByUserName(String userName)
    {
        try{
            User user=userRepository.findByUsername(userName);
            if(user==null){
                System.out.println("User not found");
                return false;
            }
            userRepository.deleteById(user.getId());
            return true;
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            return false;
        }
    }
    public List<FileMetadata> GetAllFiles()
    {
        return fileMetadataRepository.findAll();
    }

    public String DeleteFileById(String fileId)
    {
        String reply="";
        try{
            Optional<FileMetadata> optionalFile=fileMetadataRepository.findById(fileId);
            if(optionalFile.isPresent()){
                fileMetadataRepository.deleteById(fileId);
                FileMetadata fileMetadata=optionalFile.get();
                reply+="File deleted from DB";
                java.nio.file.Path filePath= java.nio.file.Paths.get(fileMetadata.getFilePath());
                if(java.nio.file.Files.exists(filePath)){
                    java.nio.file.Files.delete(filePath);
                    reply+=" and Server";
                }else{
                    reply+=" but file does not exist on server";
                }

            }
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());

        }
        return  reply;
    }
}
