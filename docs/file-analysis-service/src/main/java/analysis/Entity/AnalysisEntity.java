package analysis.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AnalysisEntity {
    @Id
    private String fileId;
    private int paragraphs;
    private int words;
    private int characters;
    private String fileHash;

    public String getFileId() { return fileId; }
    public void setFileId(String fileId) { this.fileId = fileId; }

    public int getParagraphs() { return paragraphs; }
    public void setParagraphs(int paragraphs) { this.paragraphs = paragraphs; }

    public int getWords() { return words; }
    public void setWords(int words) { this.words = words; }

    public int getCharacters() { return characters; }
    public void setCharacters(int characters) { this.characters = characters; }

    public String getFileHash() { return fileHash; }
    public void setFileHash(String hash) { this.fileHash = hash; }
}
