package com.epam.project.webappcourses.web.command;

public class CommandResponse {

    private DispatchType dispatchType;
    private String url;

    public CommandResponse(DispatchType dispatchType, String url) {
        this.dispatchType = dispatchType;
        this.url = url;
    }

    public DispatchType getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(DispatchType dispatchType) {
        this.dispatchType = dispatchType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public enum DispatchType {
        SEND_REDIRECT,
        FORWARD
    }
}

