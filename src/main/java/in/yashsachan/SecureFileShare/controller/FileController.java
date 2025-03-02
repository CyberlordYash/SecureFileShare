package in.yashsachan.SecureFileShare.controller;


import in.yashsachan.SecureFileShare.model.FileMetadata;
import in.yashsachan.SecureFileShare.model.User;
import in.yashsachan.SecureFileShare.repository.UserRepository;
import in.yashsachan.SecureFileShare.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Authentication authentication) throws Exception {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        FileMetadata metadata = fileService.storeFile(file.getBytes(), file.getOriginalFilename(), user);
        return "File uploaded with ID: " + metadata.getId();
    }

    @GetMapping("/download/{fileId}")
    public byte[] downloadFile(@PathVariable String fileId, Authentication authentication) throws Exception {
        FileMetadata fileMetadata = fileService.findById(fileId);
        if (fileMetadata == null) {
            throw new RuntimeException("File not found");
        }

        // Optionally check if user is the owner or has the right role
        return fileService.getFileContent(fileMetadata);
    }
}
