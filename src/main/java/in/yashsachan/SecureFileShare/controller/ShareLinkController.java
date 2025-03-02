package in.yashsachan.SecureFileShare.controller;


import in.yashsachan.SecureFileShare.model.FileMetadata;
import in.yashsachan.SecureFileShare.model.ShareLink;
import in.yashsachan.SecureFileShare.service.FileService;
import in.yashsachan.SecureFileShare.service.ShareLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/share")
public class ShareLinkController {

    @Autowired
    private ShareLinkService shareLinkService;

    @Autowired
    private FileService fileService;

    @PostMapping("/generate/{fileId}")
    public String generateShareLink(@PathVariable String fileId,
                                    @RequestParam(defaultValue = "60") int minutes) {
        FileMetadata fileMetadata = fileService.findById(fileId);
        if (fileMetadata == null) {
            return "File not found";
        }

        LocalDateTime expiration = LocalDateTime.now().plusMinutes(minutes);
        ShareLink shareLink = shareLinkService.generateShareLink(fileMetadata, expiration);
        return shareLink.getToken();
    }

    @GetMapping("/access/{token}")
    public byte[] accessFileViaToken(@PathVariable String token) throws Exception {
        var shareLinkOpt = shareLinkService.findByToken(token);
        if (shareLinkOpt.isEmpty()) {
            throw new RuntimeException("Invalid share link");
        }

        ShareLink shareLink = shareLinkOpt.get();
        if (shareLink.isUsed()) {
            throw new RuntimeException("Link already used");
        }
        if (LocalDateTime.now().isAfter(shareLink.getExpiration())) {
            throw new RuntimeException("Link expired");
        }

        // If you want a one-time link, mark it used
        shareLinkService.markUsed(shareLink);

        FileMetadata fileMetadata = shareLink.getFile();
        return fileService.getFileContent(fileMetadata);
    }
}

