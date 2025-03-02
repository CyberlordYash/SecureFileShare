package in.yashsachan.SecureFileShare.service;




import in.yashsachan.SecureFileShare.model.FileMetadata;
import in.yashsachan.SecureFileShare.model.User;
import in.yashsachan.SecureFileShare.repository.FileMetadataRepository;
import in.yashsachan.SecureFileShare.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class FileService {

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    private final String uploadDir = "uploads"; // local directory

    public FileMetadata storeFile(byte[] fileData, String originalFilename, User owner) throws Exception {
        // Ensure the upload directory exists
        java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
        if (!java.nio.file.Files.exists(uploadPath)) {
            java.nio.file.Files.createDirectories(uploadPath);
        }

        // Create a unique file name
        String filePath = java.nio.file.Paths.get(uploadDir, System.currentTimeMillis() + "_" + originalFilename).toString();

        // Encrypt and save the file data
        EncryptionUtil.encryptAndSaveFile(fileData, filePath);

        // Create and store metadata
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileName(originalFilename);
        fileMetadata.setFilePath(filePath);
        fileMetadata.setOwner(owner);

        return fileMetadataRepository.save(fileMetadata);
    }


    public byte[] getFileContent(FileMetadata fileMetadata) throws Exception {
        return EncryptionUtil.decryptFile(fileMetadata.getFilePath());
    }

    public FileMetadata findById(String id) {
        return fileMetadataRepository.findById(id).orElse(null);
    }
}
