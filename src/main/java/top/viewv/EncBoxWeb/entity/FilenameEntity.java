package top.viewv.EncBoxWeb.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FilenameEntity {

    @Id
    private String filenameuuid;

    private String filename;

    public String getFilenameuuid() {
        return filenameuuid;
    }

    public void setFilenameuuid(String filenameuuid) {
        this.filenameuuid = filenameuuid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
