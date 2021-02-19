package com.xyk.Entity;

/**
 * Administrator  ：zhouyuru
 * 2020/10/10
 * Describe ：通话状态实体
 */
public class CallStateEntity {
    private String id;
    private String CallState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCallState() {
        return CallState;
    }

    public void setCallState(String callState) {
        CallState = callState;
    }
}
