package com.nighthawk.spring_portfolio.mvc.mortevision;

class Comment {
    private String user;
    private String text;
    
    public Comment(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Comment by " + user + ": " + text;
    }
}

class Assignment {
    private int id;
    private String title;
    private List<Comment> comments;

    public Assignment(int id, String title) {
        this.id = id;
        this.title = title;
        this.comments = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Assignment " + id + ": " + title;
    }
}

