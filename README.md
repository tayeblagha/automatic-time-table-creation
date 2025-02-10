# Summary of the Automatic Timetable Creation Application

The Automatic Timetable Creation application is a user-friendly platform designed to simplify the process of scheduling classes in educational institutions. Developed with Spring Boot and Angular, the application leverages advanced algorithms to automatically generate weekly timetables for various classes, optimizing the allocation of teachers, classrooms, and subjects while considering their availability.


![Timetable Example](https://github.com/tayeblagha/public-Images/blob/main/0.png?raw=true)


**Spring boot**: Java JDK 17


**Angular**: CLI: 18.2.12Node: 22.11.0 Package Manager: npm 10.9.0  

## Key Features

1. **Automatic Scheduling**: The application automatically creates weekly timetables for classes, reducing the manual effort involved in planning schedules.
![Timetable Example](https://github.com/tayeblagha/public-Images/blob/main/1.png?raw=true)

2. **Majors, Classes, Teachers, Students and Classroom Management**: It takes into account the availability of teachers and classrooms, ensuring that no conflicting schedules arise by checking against their busy times.
**Majors**
<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/tayeblagha/public-Images/blob/main/2.png?raw=true" alt="Timetable Example" style="width: 48%;">
  <img src="https://github.com/tayeblagha/public-Images/blob/main/3.png?raw=true" alt="Timetable Example" style="width: 48%;">
</div>

**Classes**
<div style="display: flex; justify-content: space-between; flex-wrap: wrap;">
  <img src="https://github.com/tayeblagha/public-Images/blob/main/4.png?raw=true" alt="Timetable Example" style="width: 48%;">
  <img src="https://github.com/tayeblagha/public-Images/blob/main/5.png?raw=true" alt="Timetable Example" style="width: 48%;">
  <img src="https://github.com/tayeblagha/public-Images/blob/main/6.png?raw=true" alt="Timetable Example" style="width: 48%;">
  <img src="https://github.com/tayeblagha/public-Images/blob/main/7.png?raw=true" alt="Timetable Example" style="width: 48%;">
</div>

**Teachers**
<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/tayeblagha/public-Images/blob/main/8.png?raw=true" alt="Timetable Example" style="width: 48%;">
  <img src="https://github.com/tayeblagha/public-Images/blob/main/9.png?raw=true" alt="Timetable Example" style="width: 48%;">
</div>

**Students**
<div style="display: flex; justify-content: space-between;">
  <img src="https://github.com/tayeblagha/public-Images/blob/main/10.png?raw=true" alt="Timetable Example" style="width: 48%;">
  <img src="https://github.com/tayeblagha/public-Images/blob/main/11.png?raw=true" alt="Timetable Example" style="width: 48%;">
</div>


**Rooms**
![Timetable Example](https://github.com/tayeblagha/public-Images/blob/main/12.png?raw=true)

3. **Subject Allocation**: Each subject is allocated based on predefined session requirements, allowing for an efficient distribution of teaching hours.
4. **User-Friendly Interface**: Built with Angular, the platform provides an intuitive interface that makes it easy for administrators and staff to generate and view schedules.
5. **Historical Data and Updates**: Users can manage and update existing timetables, ensuring that adjustments can be made whenever necessary.

## Performance Analysis

The application's performance is optimized to handle a large number of classes, teachers, and subjects while maintaining a user-friendly interface.

### Time Complexity
For **C** classes, the total complexity is approximately:

\[
O(C \log C) + O(C) \times O(T + S + R)
\]

Since \(O(C \log C)\) dominates for large \(C\), the overall complexity simplifies to:

\[
O(C \log C + C(T + S + R))
\]

If teachers (\(T\)), subjects (\(S\)), and rooms (\(R\)) grow with classes (\(C\)), then:

\[
**O(C^2)**
\]

### Space Complexity
The most memory-intensive structures are:

- **Teacher availability map** → \(O(T \times D)\) (where \(D = 6\), constant)
- **Room availability map** → \(O(R \times D)\)
- **Timetable storage** → \(O(C \times D)\)

Total space complexity:

\[
O(C + T + R)
\]

If teachers, subjects, and rooms scale with classes:

\[
**O(C)**
\]

Note: The time and space complexity analysis assumes that the number of days and hours are constant (<10), as specified in the problem statement.

This application is not only a time-saver but also helps improve the efficiency of educational institutions in managing their class schedules, ultimately leading to a more organized and effective learning environment.
