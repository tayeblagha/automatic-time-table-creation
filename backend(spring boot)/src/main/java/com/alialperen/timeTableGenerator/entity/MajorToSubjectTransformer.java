package com.alialperen.timeTableGenerator.entity;
import java.util.ArrayList;
import java.util.List;

public class MajorToSubjectTransformer {

    public static Subject transformMajorToSubjects(Major major,int grade) {
        Subject s =new Subject();

        for (GradeDuration gradeDuration : major.getGradeDurations()) {
            if (gradeDuration.getGrade()!=grade){
                continue;
            }
            int hours = gradeDuration.getDurationPerWeek();
            List<Integer> sessionDurations = calculateSessionDurations(hours);
            int sessionsPerWeek = sessionDurations.size();
            String subjectName = major.getName();

            s= new Subject(major.getId(),subjectName, hours, sessionsPerWeek, sessionDurations);
        }

         return  s;
    }

    private static List<Integer> calculateSessionDurations(int hours) {
        List<Integer> sessionDurations = new ArrayList<>();

        while (hours > 2) {
            sessionDurations.add(2);
            hours -= 2;
        }

        if (hours > 0) {
            sessionDurations.add(hours);
        }

        return sessionDurations;
    }

//    public static void main(String[] args) {
//        // Example usage
//        List<GradeDuration> gradeDurations = new ArrayList<>();
//        gradeDurations.add(new GradeDuration(1, 3)); // Grade 1 with 7 hours/week
//        gradeDurations.add(new GradeDuration(2, 5)); // Grade 2 with 5 hours/week
//
//        Major major = Major.builder()
//                .id(1L)
//                .name("Mathematics")
//                .label("MATH")
//                .gradeDurations(gradeDurations)
//                .build();
//
//        Subject subject = transformMajorToSubjects(major,1);
//
//
//            System.out.println(subject);
//
//    }
}
