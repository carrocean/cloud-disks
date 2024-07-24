package com.example.enums;

import org.springframework.http.MediaType;

public enum ContentTypeEnum {

    JSON(MediaType.APPLICATION_JSON, "json"),
    HTML(MediaType.TEXT_HTML, "html"),
    XML(MediaType.APPLICATION_XML, "xml"),
    PDF(MediaType.APPLICATION_PDF, "pdf"),
    DOCX(MediaType.APPLICATION_OCTET_STREAM, "docx"), // 通常使用OCTET_STREAM，但这里给出一个更具体的类型
    XLS(MediaType.APPLICATION_OCTET_STREAM, "xls"), // Excel文件通常使用OCTET_STREAM
    XLSX(MediaType.APPLICATION_OCTET_STREAM, "xlsx"),
    PPT(MediaType.APPLICATION_OCTET_STREAM, "ppt"), // PowerPoint文件通常使用OCTET_STREAM
    PPTX(MediaType.APPLICATION_OCTET_STREAM, "pptx"),
    JPG(MediaType.IMAGE_JPEG, "jpg"),
    JPEG(MediaType.IMAGE_JPEG, "jpeg"),
    PNG(MediaType.IMAGE_PNG, "png"),
    GIF(MediaType.IMAGE_GIF, "gif"),
    MP3(MediaType.APPLICATION_OCTET_STREAM, "mp3"), // 音频文件通常使用OCTET_STREAM
    WAV(MediaType.APPLICATION_OCTET_STREAM, "wav"),
    AAC(MediaType.APPLICATION_OCTET_STREAM, "aac"),
    RAR(MediaType.APPLICATION_OCTET_STREAM, "rar"), // RAR文件通常使用OCTET_STREAM
    TAR(MediaType.APPLICATION_OCTET_STREAM, "tar"),
    GZ(MediaType.APPLICATION_OCTET_STREAM, "gz");

    private final MediaType mediaType;
    private final String fileExtension;

    ContentTypeEnum(MediaType mediaType, String fileExtension) {
        this.mediaType = mediaType;
        this.fileExtension = fileExtension;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public static MediaType getMediaTypeByExtension(String fileExtension) {
        for (ContentTypeEnum type : ContentTypeEnum.values()) {
            if (type.getFileExtension().equalsIgnoreCase(fileExtension)) {
                return type.getMediaType();
            }
        }
        return MediaType.APPLICATION_OCTET_STREAM; // Default to application/octet-stream if not found
    }
}