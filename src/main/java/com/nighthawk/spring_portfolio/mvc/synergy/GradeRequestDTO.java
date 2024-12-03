package com.nighthawk.spring_portfolio.mvc.synergy;

public class GradeRequestDTO {
    public final Long studentId;
    public final Long assignmentId;
    public final Double gradeSuggestion;
    public final String explanation;

    public GradeRequestDTO(Long studentId, Long assignmentId, Double gradeSuggestion, String explanation) {
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.gradeSuggestion = gradeSuggestion;
        this.explanation = explanation;
    }
}
