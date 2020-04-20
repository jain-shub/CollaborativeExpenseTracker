import { Component, OnInit } from "@angular/core";
import { UtilityService } from "app/services/form-validation-utility.service";
import { ExpenseService } from "app/services/expense.service";
import { HttpClient } from "@angular/common/http";
import { Expense } from "app/model/expense.model";
import { Routes, Router } from "@angular/router";
import { AlertService } from "../../services/alert.service";
import { UserService } from "app/services/user.service";
import { UserRegistration } from "app/model/user-registartion.model";
import { MatDialogRef } from "@angular/material/dialog";
import { FormGroup, FormControl } from "@angular/forms";
import { Validators } from "@angular/forms";

@Component({
  selector: "app-add-expense",
  templateUrl: "./add-expense.component.html",
  styleUrls: ["./add-expense.component.scss"],
})
export class AddExpenseComponent implements OnInit {
  private http: HttpClient;
  user: UserRegistration;
  token: string;
  expenseList: Expense[] = [];
  expense: Expense = new Expense();
  expenseService: ExpenseService = new ExpenseService(this.http);
  userService: UserService;
  utility: UtilityService;
  selectedValue: string;
  type: string = "CUSTOM";
  expenseType: string[] = ["SPLIT", "FULL", "CUSTOM"];

  formSubmitted = false;
  myForm: FormGroup;
  email: FormControl;
  expensename: FormControl;
  amount: FormControl;
  exptype: FormControl;
  toPerc: FormControl;
  fromPerc: FormControl;

  constructor(
    utility: UtilityService,
    expenseService: ExpenseService,
    private router: Router,
    private alertService: AlertService,
    userService: UserService,
    public dialogRef: MatDialogRef<AddExpenseComponent>
  ) {
    this.expenseService = expenseService;
    this.userService = userService;
    this.utility = utility;
  }

  ngOnInit(): void {
    this.email = new FormControl("", [
      Validators.required,
      Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$"),
    ]);
    this.exptype = new FormControl("", Validators.required);
    this.expensename = new FormControl("", Validators.required);
    this.amount = new FormControl("", [Validators.required, Validators.min(1)]);
    this.toPerc = new FormControl("", [
      Validators.required,
      Validators.max(100),
      Validators.min(0),
    ]);
    this.fromPerc = new FormControl(
      "",
      [Validators.required, Validators.max(100)],
      Validators.min[0]
    );
    this.myForm = new FormGroup({
      email: this.email,
      exptype: this.exptype,
      expensename: this.expensename,
      amount: this.amount,
      toPerc: this.toPerc,
      fromPerc: this.fromPerc,
    });
  }

  addExpense() {
    this.expenseService
      .addExpense(
        this.expense.toUserName,
        this.expense.name,
        this.expense.amount,
        this.type,
        this.expense.fromUserSplitPercentageValue,
        this.expense.toUserSplitPercentageValue
      )
      .subscribe(
        (data) => {
          this.expenseList.push(data);
          this.alertService.success("Expense Added Successful", true);
          this.close();
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
    this.formSubmitted = true;
  }

  logout() {
    this.userService.logout();
    this.router.navigate(["/login"]);
  }

  close() {
    this.dialogRef.close();
  }

  updateUserAccount() {
    let isAccountClicked: boolean = true;
    this.userService.setUserInDashboard(
      this.user,
      this.token,
      isAccountClicked
    );
    this.router.navigate(["userPages/edit"]);
  }

  onExpenseTypeChange(val: any) {
    console.log("Event Triggered");
    console.log(val);
    this.type = val;
  }
}
