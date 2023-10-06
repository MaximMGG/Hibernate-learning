package com.hibernate.project.pojo;

public class MessageEntity {
    
    Long id;
    String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    
    public MessageEntity() {}
    
    public MessageEntity(Long id, String text) {
        this.id = id;
        this.text = text;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof MessageEntity)) return false;
        MessageEntity other = (MessageEntity) obj;
        return (this.getId() == other.getId()) && this.getText().equals(other.getText());
    }

    @Override
    public String toString() {
        return "MessageEntity [id=" + id + ", text=" + text + "]";
    }

}
