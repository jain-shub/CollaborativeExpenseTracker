import { Component, OnInit, Inject } from "@angular/core";
import {
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatDialogConfig,
  MatDialog,
} from "@angular/material/dialog";
import { FormGroup, FormBuilder, FormArray, Validators } from "@angular/forms";
import { Group } from "../../model/group.model";
import { GroupService } from "../../services/group.service";
import { AlertService } from "../../services/alert.service";
import { AddGroupExpenseComponent } from "app/group-expense/add-group-expense/add-group-expense.component";
import { GetGroupExpenseComponent } from "app/group-expense/get-group-expense/get-group-expense.component";
import { UtilityService } from "app/services/form-validation-utility.service";
import { UserRegistration } from "app/model/user-registartion.model";

@Component({
  selector: "app-view-group",
  templateUrl: "./view-group.component.html",
  styleUrls: ["./view-group.component.scss"],
})
export class ViewGroupComponent implements OnInit {
  form: FormGroup;
  group: Group;
  id: string;
  name: string;
  groupType: string;
  memberEmail: string[];
  createdBy: string;
  createdByName: string;
  memEmail: Array<string>;
  memberEmailString: string;
  updField: Boolean = false;
  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<ViewGroupComponent>,
    private groupService: GroupService,
    private alertService: AlertService,
    private dialog: MatDialog,
    private utilityService: UtilityService,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    console.log("Selected 2");
    console.log(data);
    this.group = new Group();
    this.id = utilityService.data.id;
    this.name = utilityService.data.name;
    this.groupType = utilityService.data.groupType;
    console.log(utilityService.data);
    let rep1 = utilityService.data.memberEmailString.replace("[", "");
    let rep2 = rep1.replace("]", "");
    let rep3 = rep2.replace(/\"/g, "");
    this.memberEmailString = rep3;
    console.log(this.memberEmailString);
    this.memberEmail = this.memberEmailString.split(",");
    console.log(this.memberEmail[this.memberEmail.length - 1]);
    this.createdBy = this.memberEmail[this.memberEmail.length - 1];
    this.memEmail = [];
    localStorage.setItem("currentGroupId", this.id);
    utilityService.data.memberEmail = this.memberEmail;
    utilityService.data.memberEmailString = this.memberEmailString;
    utilityService.data.createdBy = this.createdBy;

  }

  ngOnInit(): void {
    this.computeMemberEmail();
    this.form = this.fb.group({
      name: [this.name, Validators.required],
      id: [this.id, Validators.required],
      groupType: [this.groupType, Validators.required],
      createdByName: [this.createdByName, Validators.required],
      memEmail: [this.memEmail, Validators.required],
      // memEmail: this.fb.array([this.fb.group({ email: this.memEmail })]),
    });
  }

  computeMemberEmail() {
    //console.log(this.memberEmail);
    for (var i = 0; i < this.memberEmail.length; i++) {
      var str = this.memberEmail[i];
      this.memEmail.push(Object.values(str)[2]);
    }
  }

  save() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    this.dialog.open(AddGroupExpenseComponent, dialogConfig);
    this.dialogRef.close(this.form.value);
  }

  viewGroupExpenses() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    this.dialog.open(GetGroupExpenseComponent, dialogConfig);
    this.dialogRef.close(this.form.value);
  }

  close() {
    this.dialogRef.close();
    localStorage.removeItem("currentGroupId");
  }

  updIcon() {
    this.updField = true;
  }

  updateGroup() {
    this.group.groupId = this.id;
    this.group.groupType = this.groupType;
    this.group.memberEmail=this.memberEmail;
    this.group.memberEmailString = this.memberEmailString;
    this.group.name = this.name;
    this.group.createdBy = this.createdBy;
    this.groupService.updateGroup(this.group).subscribe((data) => {
      //this.userExpenseList = this.userExpenseList.filter((u) => u !== expense);
      // this.userExpense = data;
    });
  }
}
