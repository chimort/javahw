package filestorage.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FileEntity {
    @Id
    private String id;
    private String filename;
    private String path;

    public FileEntity() {}

    public FileEntity(String id, String filename, String path) {
        this.id = id;
        this.filename = filename;
        this.path = path;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
