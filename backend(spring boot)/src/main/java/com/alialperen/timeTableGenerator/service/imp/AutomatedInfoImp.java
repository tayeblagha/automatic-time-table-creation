package com.alialperen.timeTableGenerator.service.imp;

import com.alialperen.timeTableGenerator.entity.*;
import com.alialperen.timeTableGenerator.entity.enums.TypeRoom;
import com.alialperen.timeTableGenerator.repository.*;
import com.alialperen.timeTableGenerator.service.AutomatedInfo;
import com.alialperen.timeTableGenerator.service.RoomOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AutomatedInfoImp implements AutomatedInfo {
    private final MajorRepository majorRepository;
    private final DepartmentRepository departmentRepository;
    private final SemesterRepository semesterRepository;
    private final ClassesRepository classesRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository  roomRepository;
    private final TeacherClassMajorRepository  teacherClassMajorepository;
    private final RoomOrderService roomOrderService;
    private final StudentRepository studentRepository;


    public  static  int counter=0;
    public Major createMajor(Major major) {
        return majorRepository.save(major);
    }

    public void  CreateTeacherClassMajor(Major mj,Classes cl,Teacher t){
        TeacherClassMajor tcm = new TeacherClassMajor();
        tcm.setTeacher(t);
        tcm.setMajor(mj);
        tcm.setClasses(cl);
         this.teacherClassMajorepository.save(tcm);
    }
    public Student createStudent(Classes c){
        String firstName = randomTeacherFirstName();
        String lastName = randomTeacherLastName();
        Student s = new Student();
        s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setClasses(c);
        s.setLogin(firstName.toLowerCase() + lastName.toLowerCase()+counter);
        s.setPassword(s.getLogin()); // Default password
        s.setEmail(firstName.toLowerCase() + "." + lastName.toLowerCase()+counter + "@mail.com");
        counter+=1;
        return studentRepository.save(s);
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }
    public void createRooms(){
        for (int i=1; i<=40;i++) {
            Classroom room = new Classroom();
            room.setCapacity(40);
            room.setTypeRoom(TypeRoom.Room);
            room.setNumRoom(i);
            roomRepository.save(room);
        }
        for (int i=1; i<=3;i++) {
            Classroom room = new Classroom();
            room.setCapacity(25);
            room.setTypeRoom(TypeRoom.ComputerLab);
            room.setNumRoom(i);
            roomRepository.save(room);
        }
        for (int i=1; i<=2;i++) {
            Classroom room = new Classroom();
            room.setCapacity(25);
            room.setTypeRoom(TypeRoom.ElectricityLab);
            room.setNumRoom(i);
            roomRepository.save(room);
        }

        for (int i=1; i<=3;i++) {
            Classroom room = new Classroom();
            room.setCapacity(25);
            room.setTypeRoom(TypeRoom.Workshop);
            room.setNumRoom(i);
            roomRepository.save(room);
        }



    }

    public List<Semester> getFirstNSemesters(int n) {
        return semesterRepository.findFirstNSemesters(n);
    }

    private void createMajorWithSubjects(List<Classes> classes,String majorName, String majorLabel, Department department, List<Semester> semesters, List<GradeDuration> gradeDurations) {
        Major major = new Major();
        major.setName(majorName);
        major.setLabel(majorLabel);
        major.setDepartment(department);
        major.setSemesters(semesters);
        major.setGradeDurations(gradeDurations);
        createMajor(major);

        // Create a teacher for the major
        String teacherFirstName = randomTeacherFirstName();
        String teacherLastName = randomTeacherLastName();
        Teacher t= createTeacher(teacherFirstName, teacherLastName, department, major);
        int counter=0;
        for(Classes cl:classes){

            CreateTeacherClassMajor(major, cl, t);
            counter+=1;
            counter%=4;
            if (counter==0){  t= createTeacher(randomTeacherFirstName(), randomTeacherLastName(), department, major);  }

        }
    }

    private Teacher createTeacher(String firstName, String lastName, Department department, Major major) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
         teacher.setLogin(firstName.toLowerCase() + lastName.toLowerCase()+counter);
        teacher.setPassword(teacher.getLogin()); // Default password
        teacher.setDepartments(List.of(department));
        teacher.setMajors(List.of(major));

        // Save teacher logic here (assuming a TeacherRepository exists)

        teacher.setEmail(firstName.toLowerCase() + "." + lastName.toLowerCase()+counter + "@mail.com");
        counter+=1;
        return teacherRepository.save(teacher);

    }


    private String randomTeacherFirstName() {
        String[] firstNames = {
                "Ahmed", "Ali", "Fatima", "Aisha", "Omar", "Mariam",
                "Yusuf", "Sara", "Hassan", "Layla", "Zayd", "Huda",
                "Khalid", "Noura", "Ibrahim", "Samira", "Rami", "Nadia",
                "Yasmin", "Mustafa", "Zainab", "Faisal", "Noor", "Amira",
                "Rasha", "Sami", "Khadija", "Mahmoud", "Jana", "Imran",
                "Dalia", "Hassan", "Muneer", "Salma", "Tariq", "Zahra",
                "Rami", "Fouad", "Reem", "Abdullah", "Jamal", "Maya",
                "Tala", "Karim", "Asma", "Osman", "Ayman", "Mona",
                "Rania", "Bilal", "Hind", "Khalil", "Nour", "Rayan",
                "Zain", "Shams", "Yara", "Hassan", "Maha", "Adnan",
                "Sana", "Waleed", "Nadia", "Saad", "Lina", "Farida",
                "Rifaat", "Ruqaya", "Omar", "Salah", "Yara", "Amina",
                "Kamil", "Kawthar", "Samir", "Amin", "Zaynab", "Badr",
                "Faris", "Nada", "Laila", "Raed", "Siham", "Tamer",
                "Hussein", "Mariam", "Rasha", "Fayez", "Manar", "Anwar"
        };
        return firstNames[(int) (Math.random() * firstNames.length)];
    }

    private String randomTeacherLastName() {
        String[] lastNames = {
                "Al-Farsi", "Al-Mansouri", "Al-Hassan", "Al-Bassam", "Al-Sayed",
                "Al-Khouri", "Al-Jabari", "Al-Shamsi", "Al-Nasser", "Al-Muqaddam",
                "Al-Qassem", "Al-Rashid", "Al-Fayed", "Al-Hamdan", "Al-Tamimi",
                "Al-Bukhari", "Al-Abbasi", "Al-Maliki", "Al-Mustafa", "Al-Jazeera",
                "Al-Salem", "Al-Hakim", "Al-Qudsi", "Al-Munir", "Al-Haddad",
                "Al-Siddiq", "Al-Khatib", "Al-Baghdadi", "Al-Karim", "Al-Mahdi",
                "Al-Sheikh", "Al-Amin", "Al-Ghamdi", "Al-Bayati", "Al-Rami",
                "Al-Dosari", "Al-Dhaheri", "Al-Mulla", "Al-Sharif", "Al-Jundi",
                "Al-Maliki", "Al-Talib", "Al-Wahhab", "Al-Omari", "Al-Karaki",
                "Al-Hashimi", "Al-Sharawi", "Al-Najjar", "Al-Din", "Al-Mahfouz",
                "Al-Hashemi", "Al-Saadi", "Al-Hawari", "Al-Sahli", "Al-Burhan",
                "Al-Mousa", "Al-Jamil", "Al-Aziz", "Al-Raouf", "Al-Sari",
                "Al-Din", "Al-Mazrui", "Al-Karim", "Al-Rifai", "Al-Hassan",
                "Al-Moudi", "Al-Fakih", "Al-Ghazi", "Al-Mashaqa", "Al-Tayeb",
                "Al-Saidi", "Al-Farouq", "Al-Rahman", "Al-Omran", "Al-Mansour",
                "Al-Bader", "Al-Awadhi", "Al-Mu'adhdhin", "Al-Turki", "Al-Rizq"
        };
        return lastNames[(int) (Math.random() * lastNames.length)];
    }




    public List<Classes> createClassesForDepartment(Department department) {
        List<Classes> cls= new ArrayList<>();
        int numberOfStudents = 20; // Default number of students per class
        String[] labels = {"A", "B"}; // Labels for classes

        for (int grade = 1; grade <= 4; grade++) {
            int numberClass=3;
            if (grade==3)  { numberClass=5;}
            if (grade==2)  { numberClass=7;}
            else if (grade==1)  { numberClass=7;}
            for (int i=1 ; i<=numberClass;i++ ) {

                Classes newClass = new Classes();
                newClass.setDepartment(department);
                newClass.setGrade(grade);
                String followName = (grade == 1) ? "ère" : "ème";
                newClass.setName(grade + followName + i);
                newClass.setNumberOfStudents(numberOfStudents);
                newClass.setLabel(grade +followName+ " année " + department.getName() + " " + i);
                classesRepository.save(newClass);
                roomOrderService.initializeItems(newClass);
                Random rand = new Random();
                // Generate a random number between 20 (inclusive) and 25 (inclusive)
                int randomNumber = rand.nextInt(6) + 20;
                for (int j=1;j<=randomNumber;j++){
                    createStudent(newClass);
                }
                cls.add(newClass);
            }
        }
        return cls;
    }

    public void AutomateData() {

        List<Semester> semesters = getFirstNSemesters(4); // Update to 4 semesters

        createRooms();

// Mathematics Department
        Department mathDepartment = new Department();
        mathDepartment.setName("Science");
        mathDepartment.setLabel("شعبة العلوم ");
        createDepartment(mathDepartment);
        List<Classes> mathClasses = createClassesForDepartment(mathDepartment);
        createMajorWithSubjects(mathClasses, "Mathematics", "الرياضيات Mathematics", mathDepartment, semesters, List.of(
                new GradeDuration(1, 5), new GradeDuration(2, 5), new GradeDuration(3, 5), new GradeDuration(4, 6)));
        createMajorWithSubjects(mathClasses, "Physics", "الفيزياء Physics", mathDepartment, semesters, List.of(
                new GradeDuration(1, 2), new GradeDuration(2, 3), new GradeDuration(3, 3), new GradeDuration(4, 4)));
        createMajorWithSubjects(mathClasses, "Chemistry", " الكيمياء Chemistry", mathDepartment, semesters, List.of(
                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 2)));
        createMajorWithSubjects(mathClasses, "Natural Sciences", "العلوم الطبيعية Life Sciences", mathDepartment, semesters, List.of(
                new GradeDuration(1, 4), new GradeDuration(2, 4), new GradeDuration(3, 4), new GradeDuration(4, 4)));
        createMajorWithSubjects(mathClasses, "Informatics", "المعلوماتية Informatics", mathDepartment, semesters, List.of(
                new GradeDuration(1, 2), new GradeDuration(2, 2), new GradeDuration(3, 2), new GradeDuration(4, 2)));
        createMajorWithSubjects(mathClasses, "Arabic", "اللغة العربية Arabic", mathDepartment, semesters, List.of(
                new GradeDuration(1, 4), new GradeDuration(2, 3), new GradeDuration(3, 3), new GradeDuration(4, 4)));
        createMajorWithSubjects(mathClasses, "French", "اللغة الفرنسية French", mathDepartment, semesters, List.of(
                new GradeDuration(1, 4), new GradeDuration(2, 4), new GradeDuration(3, 4), new GradeDuration(4, 4)));
        createMajorWithSubjects(mathClasses, "English", "اللغة الإنجليزية English", mathDepartment, semesters, List.of(
                new GradeDuration(1, 2), new GradeDuration(2, 2), new GradeDuration(3, 2), new GradeDuration(4, 2)));

        createMajorWithSubjects(mathClasses, "Arts", "Arts فن الرسم", mathDepartment, semesters, List.of(
                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 1)));
        createMajorWithSubjects(mathClasses, "Theatre", "Theatre مسرح", mathDepartment, semesters, List.of(
                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 1)));


        createMajorWithSubjects(mathClasses, "Sport", "Sport تربية بدنية", mathDepartment, semesters, List.of(
                new GradeDuration(1, 2), new GradeDuration(2, 1), new GradeDuration(3, 2), new GradeDuration(4, 2)));

        createMajorWithSubjects(mathClasses, "Philosophy", "Philosophy فلسفة", mathDepartment, semesters, List.of(
                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 1)));

        createMajorWithSubjects(mathClasses, "Citizenship education", "Citizenship تربية وطنية", mathDepartment, semesters, List.of(
                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 1)));

        createMajorWithSubjects(mathClasses, "Islam education", "Islam الفكر الإسلامي", mathDepartment, semesters, List.of(
                new GradeDuration(1, 2), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 1)));


// Experimental Sciences Department
        /*
        Department sciencesDepartment = new Department();
        sciencesDepartment.setName("Experimental Sciences");
        sciencesDepartment.setLabel("شعبة العلوم التجريبية Experimental Sciences");
        createDepartment(sciencesDepartment);
        List<Classes> experimentalClasses = createClassesForDepartment(sciencesDepartment);

        createMajorWithSubjects(experimentalClasses, "English", "اللغة الإنجليزية English", sciencesDepartment, semesters, List.of(
                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 1)));
        createMajorWithSubjects(experimentalClasses, "Arts", "Arts الفنون التشكيلية", sciencesDepartment, semesters, List.of(
                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 1)));
        createMajorWithSubjects(experimentalClasses, "Mathematics", "الرياضيات Mathematics", sciencesDepartment, semesters, List.of(
                new GradeDuration(1, 4), new GradeDuration(2, 4), new GradeDuration(3, 5), new GradeDuration(4, 4)));
        createMajorWithSubjects(experimentalClasses, "Physics", "الفيزياء Physics", sciencesDepartment, semesters, List.of(
                new GradeDuration(1, 4), new GradeDuration(2, 4), new GradeDuration(3, 3), new GradeDuration(4, 4)));
        createMajorWithSubjects(experimentalClasses, "Chemistry", "العلوم الطبيعية (الكيمياء) Chemistry", sciencesDepartment, semesters, List.of(
                new GradeDuration(1, 4), new GradeDuration(2, 4), new GradeDuration(3, 1), new GradeDuration(4, 4)));
        createMajorWithSubjects(experimentalClasses, "Life Sciences", "العلوم الحياتية Life Sciences", sciencesDepartment, semesters, List.of(
                new GradeDuration(1, 3), new GradeDuration(2, 3), new GradeDuration(3, 4), new GradeDuration(4, 3)));
        createMajorWithSubjects(experimentalClasses, "Informatics", "المعلوماتية Informatics", mathDepartment, semesters, List.of(
                new GradeDuration(1, 2), new GradeDuration(2, 2), new GradeDuration(3, 2), new GradeDuration(4, )));


// Literary and Human Sciences Department

        Department literaryDepartment = new Department();
        literaryDepartment.setName("Literary and Human Sciences");
        literaryDepartment.setLabel("شعبة الأدب والعلوم الإنسانية Literary and Human Sciences");
        createDepartment(literaryDepartment);
        List<Classes> literaryClasses = createClassesForDepartment(literaryDepartment);

        createMajorWithSubjects(literaryClasses, "Arts", "Arts الفنون التشكيلية", literaryDepartment, semesters, List.of(
                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 1)));
        createMajorWithSubjects(literaryClasses, "English", "اللغة الإنجليزية English", literaryDepartment, semesters, List.of(
                new GradeDuration(1, 2), new GradeDuration(2, 2), new GradeDuration(3, 2), new GradeDuration(4, 2)));
        createMajorWithSubjects(literaryClasses, "Arabic", "اللغة العربية Arabic", literaryDepartment, semesters, List.of(
                new GradeDuration(1, 4), new GradeDuration(2, 4), new GradeDuration(3, 4), new GradeDuration(4, 4)));
        createMajorWithSubjects(literaryClasses, "French", "اللغة الفرنسية French", literaryDepartment, semesters, List.of(
                new GradeDuration(1, 3), new GradeDuration(2, 3), new GradeDuration(3, 3), new GradeDuration(4, 4)));
        createMajorWithSubjects(literaryClasses, "History", "التاريخ  History ", literaryDepartment, semesters, List.of(
                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 1)));
        createMajorWithSubjects(literaryClasses, "Philosophy", "الفلسفة Philosophy", literaryDepartment, semesters, List.of(
                new GradeDuration(1, 3), new GradeDuration(2, 3), new GradeDuration(3, 3), new GradeDuration(4, 1)));
        createMajorWithSubjects(literaryClasses, "Geography", "الجغرافيا Geography", literaryDepartment, semesters, List.of(
                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1), new GradeDuration(4, 1)));

        */
//        // Economics Department
//        Department economicsDepartment = new Department();
//        economicsDepartment.setName("Economics");
//        economicsDepartment.setLabel("شعبة الاقتصاد Economics");
//        createDepartment(economicsDepartment);
//        createClassesForDepartment(economicsDepartment);
//
//        createMajorWithSubjects("Sport", "Sport التربية البدنية", economicsDepartment, semesters, List.of(
//                new GradeDuration(1, 1), new GradeDuration(2, 1), new GradeDuration(3, 1)));
//        createMajorWithSubjects("English", "اللغة الإنجليزية English", economicsDepartment, semesters, List.of(
//                new GradeDuration(1, 2), new GradeDuration(2, 2), new GradeDuration(3, 2)));
//        createMajorWithSubjects("Mathematics", "الرياضيات Mathematics", economicsDepartment, semesters, List.of(
//                new GradeDuration(1, 3), new GradeDuration(2, 3), new GradeDuration(3, 3)));
//        createMajorWithSubjects("Economics", "الاقتصاد Economics", economicsDepartment, semesters, List.of(
//                new GradeDuration(1, 4), new GradeDuration(2, 4), new GradeDuration(3, 4)));
//        createMajorWithSubjects("French", "اللغة الفرنسية French", economicsDepartment, semesters, List.of(
//                new GradeDuration(1, 3), new GradeDuration(2, 3), new GradeDuration(3, 3)));
//        createMajorWithSubjects("History and Geography", "التاريخ والجغرافيا History and Geography", economicsDepartment, semesters, List.of(
//                new GradeDuration(1, 3), new GradeDuration(2, 3), new GradeDuration(3, 3)));

    }
}
