package filestorage.FileController;

import filestorage.Entity.FileEntity;
import filestorage.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileRepository repo;

    @Value("${file.storage.location}")
    private String storageLocation;
    private Path storagePath;

    public FileController(FileRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void init() throws IOException {
        storagePath = Paths.get(storageLocation).toAbsolutePath().normalize();
        Files.createDirectories(storagePath);
    }

    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        String id = UUID.randomUUID().toString();
        String clean = StringUtils.cleanPath(file.getOriginalFilename());
        Path target = storagePath.resolve(id + "_" + clean);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        FileEntity fe = new FileEntity(id, clean, target.toString());
        repo.save(fe);
        return id;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id) throws Exception {
        FileEntity fe = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found: " + id));
        Resource res = new UrlResource(Paths.get(fe.getPath()).toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fe.getFilename() + "\"")
                .body(res);
    }
}
