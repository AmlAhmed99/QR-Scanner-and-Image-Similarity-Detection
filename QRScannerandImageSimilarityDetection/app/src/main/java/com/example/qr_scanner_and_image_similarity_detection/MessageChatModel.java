package com.example.qr_scanner_and_image_similarity_detection;

public class MessageChatModel {
    private String text;
    private String time;
    private int viewType;

    public MessageChatModel(String text, String time, int viewType) {
        this.text = text;
        this.time = time;
        this.viewType = viewType;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public int getViewType() {
        return viewType;
    }
}
