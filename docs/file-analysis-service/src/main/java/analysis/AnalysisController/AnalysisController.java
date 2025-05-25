package analysis.AnalysisController;


import analysis.Entity.AnalysisEntity;
import analysis.Repository.AnalysisRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    private final RestTemplate rest;
    private final AnalysisRepository repo;
    private final String storageUrl;

    public AnalysisController(RestTemplate rest,
                              AnalysisRepository repo,
                              @Value("${file.storage.url}") String storageUrl) {
        this.rest = rest;
        this.repo = repo;
        this.storageUrl = storageUrl;
    }

    @PostMapping("/analyze")
    public AnalysisEntity analyze(@RequestBody FileIdRequest req) {
        return repo.findById(req.getFileId()).orElseGet(() -> {
            String text = rest.getForObject(storageUrl + "/" + req.getFileId(), String.class);
            String[] paras = text.split("\\r?\\n\\s*\\r?\\n");
            String[] words = text.trim().split("\\s+");
            String hash = md5(text);

            AnalysisEntity ae = new AnalysisEntity();
            ae.setFileId(req.getFileId());
            ae.setFileHash(hash);
            ae.setParagraphs(paras.length);
            ae.setWords(words.length);
            ae.setCharacters(text.length());
            return repo.save(ae);
        });
    }

    @PostMapping("/compare")
    public MatchResult compare(@RequestBody CompareRequest req) {
        AnalysisEntity a1 = analyze(new FileIdRequest(req.getFileId1()));
        AnalysisEntity a2 = analyze(new FileIdRequest(req.getFileId2()));
        boolean match = a1.getFileHash().equals(a2.getFileHash());
        return new MatchResult(match);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<AnalysisEntity> getResult(@PathVariable String fileId) {
        return repo.findById(fileId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public static class FileIdRequest {
        private String fileId;
        public FileIdRequest() {}
        public FileIdRequest(String fileId) {
            this.fileId = fileId;
        }
        public String getFileId() { return fileId; }
        public void setFileId(String fileId) { this.fileId = fileId; }
    }

    public static class CompareRequest {
        private String fileId1;
        private String fileId2;

        public CompareRequest() {}

        public CompareRequest(String fileId1, String fileId2) {
            this.fileId1 = fileId1;
            this.fileId2 = fileId2;
        }

        public String getFileId1() { return fileId1; }
        public void setFileId1(String fileId1) { this.fileId1 = fileId1; }

        public String getFileId2() { return fileId2; }
        public void setFileId2(String fileId2) { this.fileId2 = fileId2; }
    }

    public static class MatchResult {
        private final boolean match;
        public MatchResult(boolean match) { this.match = match; }
        public boolean isMatch() { return match; }
    }

    // Вспомогательный метод для MD5
    private static String md5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(s.getBytes(StandardCharsets.UTF_8));
            Formatter fmt = new Formatter();
            for (byte b : digest) {
                fmt.format("%02x", b);
            }
            return fmt.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5 calculation failed", e);
        }
    }
}
