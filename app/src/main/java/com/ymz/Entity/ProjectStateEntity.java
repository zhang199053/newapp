package com.ymz.Entity;

/**
 * Administrator  ：zhouyuru
 * 2020/10/10
 * Describe ：项目实体
 */
public class ProjectStateEntity {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectState() {
        return ProjectState;
    }

    public void setProjectState(String projectState) {
        ProjectState = projectState;
    }

    private String ProjectState;
}
