import { Component, OnInit, Inject } from "@angular/core";
import { GroupExpenseService } from "app/services/group-expense.service";
import { UtilityService } from "app/services/form-validation-utility.service";
import { GroupService } from "app/services/group.service";
import { GroupExpense } from "app/model/group-expense.model";
import { MatDialogRef } from "@angular/material/dialog";
import { AlertService } from "app/services/alert.service";
import { Observable, Subject } from "rxjs";
import { Group } from "app/model/group.model";
import { UserRegistration } from "app/model/user-registartion.model";
import { utils } from "protractor";

@Component({
  selector: "app-add-group-expense",
  templateUrl: "./add-group-expense.component.html",
  styleUrls: ["./add-group-expense.component.scss"],
})
export class AddGroupExpenseComponent implements OnInit {
  token: string;
  expenseList: GroupExpense[] = [];
  groupExpense: GroupExpense;
  paidByVal: string;
  memberShares: Map<string, number>;
  mems: any[][] = [];
  memEmail: Array<string>;
  ratio: number;
  user: UserRegistration;
  percentJson: any;
  totalError: boolean;
  totalErrorMsg: string = "";
  viewedGroup: Group;
  step = 0;
  total=0;


  utility: UtilityService;
  constructor(
    private groupService: GroupService,
    private groupExpenseService: GroupExpenseService,
    private alertService: AlertService,
    public dialogRef: MatDialogRef<AddGroupExpenseComponent>,
    utility: UtilityService,
  ) {
    this.utility = utility;
    this.ratio = 0;
    console.log("Add group constructor");
    this.user = JSON.parse(localStorage.getItem("currentUser")).user;
    console.log(this.user);
    this.groupExpense = new GroupExpense();
    this.viewedGroup = new Group();
    this.getValuesFromGroup();
  }

  ngOnInit(): void {}

  addGroupExpense() {
    this.percentJson = this.onValChange();
    this.groupExpenseService
      .addGroupExpense(
        this.groupExpense.name,
        this.viewedGroup.groupId,
        this.groupExpense.amount,
        this.percentJson,
        this.groupExpense.paidBy,
        this.groupExpense.createdBy
      )
      .subscribe(
        (data) => {
          this.expenseList.push(data);
          this.alertService.success("Expense Added Successful", true);
          this.close();
        },
        (error) => {
          console.log(error);
          this.alertService.error(error);
        }
      );
  }

  onValChange() {
    let jsonObject = {};

    this.mems.map((items) => {

      this.groupExpense.dividePercentage.set(items[0].id, items[1]/100);
      // total += items[1];
    });
    if (this.total != 100) {
      this.alertService.error("Sum of all ratios should be 100");
      this.totalError = true;
      this.totalErrorMsg = "Sum of all ratios should be 100";
    }
    this.groupExpense.dividePercentage.forEach((value, key) => {
      jsonObject[key] = value;
    });

    return jsonObject;
  }

  getValuesFromGroup() {
    this.groupExpense.createdBy = this.utility.data.createdBy;
    this.viewedGroup = this.utility.data;
    this.utility.data.memberEmail.array.forEach(element => {
      this.mems.push([element, this.ratio]);
    });


    // this.utility.data.memberEmail.forEach(element => {
    //   this.mems.push([element, this.ratio]);
    // });
    // this.groupService.getGroupById().subscribe((data) => {
    //   console.log("in add group get");
    //   console.log(data);
      // this.viewedGroup = data;
      // data.members.forEach((element) => {
      //   this.mems.push([element, this.ratio]);
      // });
    // });
  }


  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  close() {
    this.dialogRef.close();
    localStorage.removeItem("currentGroupId");
  }

  onPaidByChange(val: any) {
    console.log("Event Triggered");
    console.log(val);
    this.groupExpense.paidBy = val;
  }

  onPercentChange(){
    this.mems.map((items) => {
      this.total += items[1];
    });
  }
}
