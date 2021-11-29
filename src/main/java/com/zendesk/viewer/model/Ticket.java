package com.zendesk.viewer.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {

    @JsonProperty("assignee_id")
    private String assigneeId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("description")
    private String description;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("group_id")
    private String groupId;

    @JsonProperty("requester_id")
    private String requesterId;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("id")
    private int id;



    public Ticket(){

    }

    public Ticket(String assigneeId, LocalDateTime createdAt, String description, String groupId,
                  String requestorId, LocalDateTime updatedAt,
                  String priority, List<String> tags, String subject, int id) {
        this.assigneeId = assigneeId;
        this.createdAt = createdAt;
        this.description = description;
        this.groupId = groupId;
        this.requesterId = requestorId;
        this.updatedAt = updatedAt;
        this.priority = priority;
        this.tags = tags;
        this.subject = subject;
        this.id = id;
    }



    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
