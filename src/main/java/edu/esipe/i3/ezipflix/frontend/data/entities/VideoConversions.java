package edu.esipe.i3.ezipflix.frontend.data.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class VideoConversions {

    private String uuid;
    private String originPath;
    private String targetPath;
    private String bucket;
    private String sequence;


    public VideoConversions() {
    }

    public VideoConversions(String uuid, String originPath, String targetPath, String bucket, String sequence) {
        this.uuid = uuid;
        this.originPath = originPath;
        this.targetPath = targetPath;
        this.bucket = bucket;
        this.sequence = sequence;
    }

    public VideoConversions(String uuid, String originPath, String targetPath) {
        this.uuid = uuid;
        this.originPath = originPath;
        this.targetPath = targetPath;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOriginPath() {
        return originPath;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String toJson() throws JsonProcessingException {
        final ObjectMapper _mapper = new ObjectMapper();
        final Map<String, String> _map = new HashMap<String, String>();
        _map.put("uuid", uuid);
        _map.put("originPath", originPath);
        _map.put("targetPath", targetPath);
        _map.put("bucket", bucket);
        _map.put("sequence", sequence);
        byte [] _bytes = _mapper.writeValueAsBytes(_map);
        return new String(_bytes);
    }
}
