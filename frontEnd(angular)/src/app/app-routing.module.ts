import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManagmentTeacherComponent } from './components/managment/managment-prof/managment-prof.component';
import { AddNewProfComponent } from './components/add/add-new-prof/add-new-prof.component';
import { HomeComponent } from './components/home/home.component';
import { ManagmentMajorComponenet } from './components/managment/managment-major/managment-major.component';
import { AddNewMajorComponent } from './components/add/add-new-majors/add-new-majors.component';
import { AddNewDepartementComponent } from './components/add/add-new-departement/add-new-departement.component';
import { ManagmentClassesComponent } from './components/managment/managment-classes/managment-classes.component';
import { AddNewClasseComponent } from './components/add/add-new-classes/add-new-classe.component';
import { ManagmentRoomsComponent } from './components/managment/managment-rooms/managment-rooms.component';
import { AddNewRoomComponent } from './components/add/add-new-room/add-new-room.component';
import { EditProfComponent } from './components/edit/edit-prof/edit-prof.component';
import { NotFoundComponent } from './components/widgets/not-found/not-found.component';
import { ManagmentDepartmentComponent } from './components/managment/managment-departement/managment-departement.component';
import { EditRoomComponent } from './components/edit/edit-room/edit-room.component';
import { IndexPageComponent } from './components/index-page/index-page.component';

import { EditProfileComponent } from './components/edit/edit-profile/edit-profile.component';
import { NotAvaliablityComponent } from './components/managment/not-avaliable/not-avaliablity.component';
import { AddNewNotAvaliableComponent } from './components/add/add-new-not-avaliable/add-new-not-avaliable.component';
import { SchoolGeneralInfoComponent } from './components/school-general-info/school-general-info.component';
import { AddSubjectComponentComponent } from './components/add/add-new-majors/add-subject-component/add-subject-component.component';
import { MajorGradeComponent } from './components/managment/managment-major/major-grade/major-grade.component';
import { ScheduleComponent } from './components/Schedule/schedule/schedule.component';
import { ManagmentRoomOrderComponent } from './components/managment/managment-room-order/managment-room-order.component';
import { ManagmentStudentComponent } from './components/managment/managment-student/managment-student.component';
import { AddNewStudentComponent } from './components/add/add-new-student/add-new-student.component';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { TeacherScheduleComponent } from './components/teacher-schedule/teacher-schedule.component';
import { TeacherDetailsComponent } from './components/teacher-details/teacher-details.component';
import { TimetableDisplayerComponent } from './components/timetable-displayer/timetable-displayer.component';
import { StudentListComponent } from './components/student-list/student-list.component';
const routes: Routes = [
  { path :'' , component: HomeComponent},
  { path :'index' , component: IndexPageComponent},

    { path :'home' , component: HomeComponent},

    { path :'school' , component: SchoolGeneralInfoComponent},
    { path :'teachers' , component: ManagmentTeacherComponent},
    { path :'teachers/add' , component: AddNewProfComponent},
    { path :'teachers/edit/:teacher_id' , component: AddNewProfComponent},
    { path :'teachers/details/:teacher_id' , component: TeacherDetailsComponent},
    
    { path :'teachers/timetables' , component: TeacherScheduleComponent},


    
    { path :'majors' , component: ManagmentMajorComponenet},
    { path :'majors/add' , component: AddNewMajorComponent},
    { path :'majors/:id/grade_duration' , component: AddSubjectComponentComponent},
    { path :'majors/edit/:major_id' , component: AddNewMajorComponent},
    // { path :'majors/edit' , component: EditMajorComponent},


    { path :'departments' , component: ManagmentDepartmentComponent},
    { path :'departments/add' , component: AddNewDepartementComponent},
    { path :'departments/edit/:id' , component: AddNewDepartementComponent},


    { path :'classes' , component: ManagmentClassesComponent},
    { path :'classes/add' , component: AddNewClasseComponent},
    { path :'classes/edit/:id' , component: AddNewClasseComponent},
    { path :'classes/majors/class/:class_id/grade/:grade_id/department/:department_id' , component: MajorGradeComponent},
    { path :'classes/rooms/class/:classe_id' , component: ManagmentRoomOrderComponent},
    { path :'classes/students/:class_id' , component: StudentListComponent},

    

    { path :'classrooms' , component: ManagmentRoomsComponent},
    { path :'classrooms/add' , component: AddNewRoomComponent},

    //{ path :'schedules' , component: ScheduleComponent},
    { path :'schedules' , component:TimetableDisplayerComponent },
    // { path :'schedule/:grade_id/:department_id' , component: ScheduleComponent},



    {path:'students',component:ManagmentStudentComponent},
    {path:'students/add',component:AddNewStudentComponent},
    {path:'students/edit/:student_id',component:AddNewStudentComponent},
    {path:'students/details/:student_id',component:UserDetailsComponent},




    

    {path:'teachers/edit',component:EditProfComponent},
    { path :'classrooms/edit' , component: EditRoomComponent},
    { path :'classrooms/edit/:id' , component: AddNewRoomComponent},
  
    { path :'profile/edit' , component: EditProfileComponent},
    { path :'notAvailables' , component: NotAvaliablityComponent},
    { path :'notAvailables/add' , component: AddNewNotAvaliableComponent},
    { path :'**' , component: NotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
