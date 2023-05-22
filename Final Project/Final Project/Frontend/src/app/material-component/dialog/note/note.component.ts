import { Component, OnInit, EventEmitter, Inject} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from 'src/app/services/category.service';
import { NoteService } from 'src/app/services/note.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.scss']
})
export class NoteComponent implements OnInit {

  onAddNote = new EventEmitter();
  onEditNote = new EventEmitter();
  noteForm:any = FormGroup;
  dialogAction:any = "Add";
  action:any = "Add";
  responseMessage:any;
  categories:any = [];

  constructor(@Inject(MAT_DIALOG_DATA) public dialogData:any, 
    private formBuilder: FormBuilder,
    private noteService: NoteService,
    public dialogRef: MatDialogRef<NoteComponent>,
    private categoryService: CategoryService,
    private snackBarService: SnackbarService) { }

  ngOnInit(): void {
    this.noteForm = this.formBuilder.group({
      title: [null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]],
      description: [null, [Validators.required]],
      categoryId: [null, [Validators.required]]
    });
    if(this.dialogData.action === "Edit") {
      this.dialogAction = "Edit";
      this.action = "Update";
      this.noteForm.patchValue(this.dialogData.data);
    }
    this.getCategories();
  }

  getCategories() {
    this.categoryService.getCategories().subscribe((response:any) => {
      this.categories = response;
    }, (error:any) => {
      console.log(error);
      if(error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackBarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

  handleSubmit() {
    if(this.dialogAction === "Edit") {
      this.edit();
    } else {
      this.add();
    }
  }

  add() {
    var formData = this.noteForm.value;
    var data = {
      title: formData.title,
      description: formData.description,
      categoryId: formData.categoryId
    }

    this.noteService.add(data).subscribe((response:any) => {
      this.dialogRef.close();
      this.onAddNote.emit();
      this.responseMessage = response.message;
      this.snackBarService.openSnackBar(this.responseMessage, "Succes");
    }, (error:any) => {
      console.log(error);
      if(error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackBarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

  edit() {
    var formData = this.noteForm.value;
    var data = {
      id: this.dialogData.data.id,
      title: formData.title,
      description: formData.description,
      categoryId: formData.categoryId
    }

    this.noteService.update(data).subscribe((response:any) => {
      this.dialogRef.close();
      this.onEditNote.emit();
      this.responseMessage = response.message;
      this.snackBarService.openSnackBar(this.responseMessage, "Succes");
    }, (error:any) => {
      console.log(error);
      if(error.error?.message) {
        this.responseMessage = error.error?.message;
      } else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackBarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }
}
