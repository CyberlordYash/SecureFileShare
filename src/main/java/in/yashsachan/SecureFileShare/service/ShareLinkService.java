package in.yashsachan.SecureFileShare.service;


import in.yashsachan.SecureFileShare.model.FileMetadata;
import in.yashsachan.SecureFileShare.model.ShareLink;
import in.yashsachan.SecureFileShare.repository.ShareLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShareLinkService {

    @Autowired
    private ShareLinkRepository shareLinkRepository;

    public ShareLink generateShareLink(FileMetadata file, LocalDateTime expiration) {
        ShareLink shareLink = new ShareLink();
        shareLink.setFile(file);
        shareLink.setToken(UUID.randomUUID().toString());
        shareLink.setExpiration(expiration);
        shareLink.setUsed(false);

        return shareLinkRepository.save(shareLink);
    }

    public Optional<ShareLink> findByToken(String token) {
        return shareLinkRepository.findByToken(token);
    }

    public void markUsed(ShareLink shareLink) {
        shareLink.setUsed(true);
        shareLinkRepository.save(shareLink);
    }
}
