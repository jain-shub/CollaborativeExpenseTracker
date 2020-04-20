import { Component, OnInit } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";
import { FormGroup, FormBuilder, FormArray, Validators } from "@angular/forms";
import { Group } from "../../model/group.model";
import { GroupService } from "../../services/group.service";
import { AlertService } from "../../services/alert.service";
import { UtilityService } from 'app/services/form-validation-utility.service';

@Component({
  selector: "app-add-group",
  templateUrl: "./add-group.component.html",
  styleUrls: ["./add-group.component.scss"],
})
export class AddGroupComponent implements OnInit {
  form: FormGroup;
  group: Group;
  // public members: any[] = [{ value: "" }];
  utility: UtilityService = new UtilityService();

  constructor(
    utility: UtilityService,
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<AddGroupComponent>,
    private groupService: GroupService,
    private alertService: AlertService
  ) {
    this.utility = utility;
    this.group = new Group();
    this.form = fb.group({
      name: [, Validators.required],
      memberEmail: this.fb.array([this.fb.group({ email: "" })]),
    });
  }
  ngOnInit(): void {}

  get memberEmail() {
    return this.form.get("memberEmail") as FormArray;
  }

  addMember() {
    this.memberEmail.push(this.fb.group({ email: "" }));
  }

  onSubmit() {
    let memEmail: Array<string> = [];
    for (var i = 0; i < this.memberEmail.value.length; i++) {
      var str = this.memberEmail.value[i];
      Object.keys(str).forEach((key) => {
        memEmail.push(str[key]);
      });
    }

    this.groupService.addGroup(this.group.name, memEmail).subscribe(
      (data) => {
        this.alertService.success("Registration successful", true);
      },
      (error) => {
        console.log(error);
          let err: any;
          err = error;
          err.statusText =
            "Invalid field values, or email entered is not found. Please enter correct values as per hint!";
          this.alertService.error(err.statusText);
      }
    );
    this.dialogRef.close(this.form.value);
  }

  close() {
    this.dialogRef.close();
  }

  removeMemberAddField(index:number){
    this.memberEmail.removeAt(index);
  }
}
