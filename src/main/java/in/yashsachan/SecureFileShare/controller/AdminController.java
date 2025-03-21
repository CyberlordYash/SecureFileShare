package in.yashsachan.SecureFileShare.controller;

import in.yashsachan.SecureFileShare.model.FileMetadata;
import in.yashsachan.SecureFileShare.model.User;
import in.yashsachan.SecureFileShare.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;
    @GetMapping("/users")
    public List<User> GetALlUsers()
    {
        return adminService.GetAllUsers();
    }

    @DeleteMapping("/user/{userName}")
    public String DeleteUserByUserName(@PathVariable String userName){
        if(adminService.DeleteUserByUserName(userName)){
            return "User Successfully Deleted";}
        return "failed to delete user";
    }

    @GetMapping("/files")
    public List<FileMetadata> GetAllFiles()
    {
        return adminService.GetAllFiles();
    }

    @DeleteMapping("/file/{fileId}")
    public String DeleteFileByFileId(@PathVariable String fileId)
    {
        return adminService.DeleteFileById(fileId);
    }
}
