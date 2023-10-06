package com.hibernate.project.pojo;

import java.util.Objects;

public class Message {
    
    String text;

    public Message(String text) {
        setText(text);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(getText(), message.getText());
    }

    public int hashCode() {
        return Objects.hash(getText());
    }

    public String toString() {
        return String.format("Message{text='%s'}", getText());
    }

}

