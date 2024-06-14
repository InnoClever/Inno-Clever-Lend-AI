package ai.inno.clever.domain.rbrotest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileInfoDTO implements Serializable {

    private Long id;

    private String ownerId;

    private String originalFilename;

    private String name;

    private String contentType;

    private String bizType;

    private Long entityId;

    private String extension;

    private Boolean modify;

    private String path;

    private byte[] file;

    private String data;

    private String fileContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Boolean getModify() {
        return modify;
    }

    public void setModify(Boolean modify) {
        this.modify = modify;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileInfoDTO)) {
            return false;
        }

        FileInfoDTO fileInfoDTO = (FileInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileInfoDTO{" +
            "id=" + getId() +
            ", ownerId='" + getOwnerId() + "'" +
            ", originalFilename='" + getOriginalFilename() + "'" +
            ", name='" + getName() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", bizType='" + getBizType() + "'" +
            ", entityId=" + getEntityId() +
            ", extension='" + getExtension() + "'" +
            ", modify='" + getModify() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}

