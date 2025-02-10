import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ActionsService } from 'src/app/services/actions.service';
import { ClassesService } from 'src/app/services/classe.service';
import { ScheduleService } from 'src/app/services/Schedule.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-actions',
  templateUrl: './actions.component.html',
  styleUrls: ['./actions.component.css']
})
export class ActionsComponent implements OnInit{

    spinnerGenerate:boolean=false;
  spinnerImport:boolean=false;
  spinnerExport:boolean=false;
  importStatus:boolean=false;
  generateStatus:boolean=false;
  
  constructor(private actons:ActionsService,private cl:ClassesService,private el:ScheduleService , private router: Router) { }

  ngOnInit(): void {
    this.cl.getClasses(0,6).subscribe(
      data=>{
        console.log(data);
        if(data.numberOfElements>0){
          this.importStatus=true;
        }
      }
    )
    this.el.getElements().subscribe(
      data=>{
        console.log(data);
        if(data.length>0){
          if(data[0].day!="" && data[0].day!=null && data[0].day)
            this.generateStatus=true;
        }
      }
    )
  }


   handleImport(event: any) {
  //   console.log("import");
  //   const file = event.target.files[0]; // Get the first file from the FileList

  //   if (file) {
  //     this.spinnerImport = true;
  //     this.importFile(file);
  //   }
  }
  importFile(file: File) {

    // this.actons.importFile(file).subscribe(
    //   (data) => {
    //     this.spinnerImport = false;
    //     this.importStatus = true;

    //     // If done, show a success message
    //     Swal.fire('Imported !', 'File imported successfully.', 'success');
    //    this.router.navigateByUrl('/home', { skipLocationChange: true }).then(() => this.router.navigate(["/home"]));
    //    window.location.reload();
    //   },
    //   (error) => {
    //     this.spinnerImport = false;

    //     // If there is an error, show an error message
    //     Swal.fire('Error !', "An error occurred during the import.", 'error');
    //   }
    // );
  }

 

  handleExport(){
    // console.log("export")
    //  // ask if he really wants to import the file
    //  Swal.fire({
    //   title: 'Do you really want to export the data ?',
    //   icon: 'info',
    //   showCancelButton: true,
    //   confirmButtonColor: '#3085d6',
    //   cancelButtonColor: '#d33',

    //   confirmButtonText: 'Yes, export !',
    //   cancelButtonText: 'Cancel'
    // }).then((result) => {
    //   if (result.isConfirmed) {
    //     // if he confirms, then import the file
    //     // upload  file
    //     this.spinnerExport=true;

    //     this.actons.exportFile().subscribe(
    //       data=>{
    //         this.spinnerExport=false;
    //         console.log(data)
    //         // if done, then show a success message
    //         downloadFile(data, "application/pdf");
    //       },
    //       error=>{
    //         this.spinnerExport=false;
    //         console.log(error)
    //         // if done, then show a success message
    //     Swal.fire(
    //       'Error !',
    //       'An error occurred during the export.',
    //       'error'
    //     )
    //       }
    //     )


    //     // show a loading spinner
        
        

        
    //   }
    // }
    // )
  }
   handleGenerate(){
  //    // ask if he really wants to import the file
  //    Swal.fire({
  //     title: 'Do you really want to generate the schedules?',
  //     text: "This operation may take some time.",
  //     icon: 'info',
  //     showCancelButton: true,
  //     confirmButtonColor: '#3085d6',
  //     cancelButtonColor: '#d33',

  //     confirmButtonText: 'Yes, generate !',
  //     cancelButtonText: 'Cancel'
  //   }).then((result) => {
  //     if (result.isConfirmed) {
  //       // if he confirms, then import the file
  //       // upload  file
  //       this.spinnerGenerate=true;
  //      this.actons.generateSchedule().subscribe(  
  //         data=>{
  //           this.generateStatus=true;
  //           this.spinnerGenerate=false;
  //       // if done, then show a success message
  //       Swal.fire(
  //         'Generated !',
  //         'Schedule generated successfully',
  //         'success'
  //       )
  //         }
        

  //       )

  //       // show a loading spinner
        
        

  //     }
  //   }
  //   )
  //}
// }

//  function downloadFile(data: Blob, arg1: string) {
//   const blob = new Blob([data], { type: arg1 });
//   const url = window.URL.createObjectURL(blob);
//   window.open(url);
}


}

