import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { saveAs } from 'file-saver';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { CategoryService } from 'src/app/services/category.service';
import { NoteService } from 'src/app/services/note.service';
import { ReportService } from 'src/app/services/report.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-manage-report',
  templateUrl: './manage-report.component.html',
  styleUrls: ['./manage-report.component.scss']
})
export class ManageReportComponent implements OnInit {

  displayedColumns: string[] = ['title', 'description', 'category', 'edit'];
  dataSource: any = [];
  manageReportForm: any = FormGroup;
  categories: any = [];
  notes: any = [];
  title: any = [];
  description: any = [];
  responseMessage: any;

  constructor(private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    private noteService: NoteService,
    private snackBarService: SnackbarService,
    private reportService: ReportService,
    private ngxService: NgxUiLoaderService) { }

  ngOnInit(): void {
    this.ngxService.start();
    this.getCategories();
    this.manageReportForm = this.formBuilder.group({
      name:[null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]],
      email:[null, [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]],
      note:[null, [Validators.required]],
      category:[null, [Validators.required]],
      title: [null, [Validators.required]],
      description: [null, [Validators.required]]
    });
  }

  getCategories() {
    this.categoryService.getFilteredCategories().subscribe((response:any) => {
      this.ngxService.stop();
      this.categories = response;
    }, (error:any) => {
      this.ngxService.stop();
      console.log(error);
      if(error.error?.message) {
        this.reportService = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackBarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

  getNoteByCategory(value:any) {
    this.noteService.getNoteByCategory(value.id).subscribe((response:any) => {
      this.notes = response;
      this.manageReportForm.controls['title'].setValue('');
      this.manageReportForm.controls['description'].setValue('');
    }, (error:any) => {
      console.log(error);
      if(error.error?.message) {
        this.reportService = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackBarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

  getNoteDetails(value:any) {
    this.noteService.getById(value.id).subscribe((response:any) => {
      //this.title = response.title;
      this.manageReportForm.controls['title'].setValue(response.title);
      this.manageReportForm.controls['description'].setValue(response.description);
    }, (error:any) => {
      this.ngxService.stop();
      console.log(error);
      if(error.error?.message) {
        this.reportService = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackBarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

  validateSubmit() {
    if(this.manageReportForm.controls['name'].value == null || 
    this.manageReportForm.controls['email'].value == null) {
      return true;
    } else {
      return false;
    }
  }

  validateNoteAdd() {
    if(this.manageReportForm.controls['category'].value == null || 
    this.manageReportForm.controls['note'].value == null) {
      return true;
    } else {
      return false;
    }
  }

  add() {
    var formData = this.manageReportForm.value;
    var note = this.dataSource.find((e: {id: number}) => e.id === formData.note.id);
    if(note == undefined) {
      this.dataSource.push({id: formData.note.id, title: formData.note.title, category: formData.category.name, description: formData.note.description});
      this.dataSource = [...this.dataSource];
      this.snackBarService.openSnackBar(GlobalConstants.noteAdded, "Note added successfully!");
    } else {
      this.snackBarService.openSnackBar(GlobalConstants.noteExistError, GlobalConstants.error);
    }
  }

  handleDeleteAction(value: any, element: any) {
    this.dataSource.splice(value, 1);
    this.dataSource = [...this.dataSource];
  }

  submitAction() {
    var formData = this.manageReportForm.value;
    var data = {
      name: formData.name,
      email: formData.email,
      noteDetails: JSON.stringify(this.dataSource)
    }
    this.ngxService.start();
    this.reportService.generateReport(data).subscribe((response:any) => {
      this.downloadFile(response?.uuid);
      this.manageReportForm.reset();
      this.dataSource = []; 
    }, (error:any) => {
      console.log(error);
      if(error.error?.message) {
        this.reportService = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackBarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

  downloadFile(fileName: string) {
    var data = {
      uuid: fileName
    }
    this.reportService.getPdf(data).subscribe((response:any) => {
      saveAs(response, fileName + '.pdf');
      this.ngxService.stop();
    })
  }

}
