import { Component, OnInit } from "@angular/core";
import { UserService } from "../../services/user.service";
import { ExpenseService } from "../../services/expense.service";
import { Expense } from "../../model/expense.model";
import { Router } from "@angular/router";
import { FormArray, FormGroup, FormControl } from "@angular/forms";

@Component({
  selector: "app-get-expense",
  templateUrl: "./get-expense.component.html",
  styleUrls: ["./get-expense.component.scss"],
})
export class GetExpenseComponent implements OnInit {
  userService: UserService;
  userExpenseList: Expense[];
  amountOwes: Expense[] = [];
  amountOwedBy: Expense[] = [];
  userExpense: Expense;
  address : string;
  loggedUserName : string;
  type:string;
  expenseType: string[] = ["SPLIT","FULL","CUSTOM"];
  dataSource = this.userExpenseList;

  constructor(private router: Router, private expenseService: ExpenseService) {}

  controls: FormArray;

  ngOnInit(): void {
    this.expenseService.refreshNeeded$.subscribe(() => {
      this.getAllExpenses();
    });
    this.getAllExpenses();
  }

  getAllExpenses() {
    this.expenseService.getExpense().subscribe((data) => {
      this.userExpenseList = data;
      this.computeExpenses();
    });
  }

  getControl(index: number, field: string): FormControl {
    return this.controls.at(index).get(field) as FormControl;
  }

  deleteExpense(expense: Expense) {
    this.expenseService.delete(expense.id).subscribe((data) => {
      this.userExpenseList = this.userExpenseList.filter((u) => u !== expense);
    });
  }

  updateRowData(expense: Expense) {
    // expense.type = this.type;
    let index = expense.type.localeCompare("SPLIT");
    console.log(index);

    if(expense.type.localeCompare("SPLIT") == 0){
      console.log("Split");
      expense.fromUserSplitPercentageValue = '50';
      expense.toUserSplitPercentageValue = '50';
    }
    if(expense.type.localeCompare("FULL") == 0){
      console.log("Full");
      expense.fromUserSplitPercentageValue = '100';
      expense.toUserSplitPercentageValue = '0';
    }
    this.expenseService.update(expense).subscribe((data) => {
      //this.userExpenseList = this.userExpenseList.filter((u) => u !== expense);
      // this.userExpense = data;
    });
  }

  computeExpenses() {
    const loggedUser = localStorage.getItem("currentUser");
    let splitted = loggedUser.split(",");
    Object.keys(splitted).forEach((key) => {
      let val = splitted[key].replace(/"/g, " ");
      if(val.includes("firstName")){
        var fname = val.split(":")[1].replace(/\s/g, "");
        this.loggedUserName = fname + " ";
      }
      if(val.includes("lastName")){
        var lname = val.split(":")[1].replace(/\s/g, "");
        this.loggedUserName = this.loggedUserName.concat(lname);
      }
      if (val.includes("email")) {
        let emailAddress = val.split(":");
        this.address = emailAddress[1];
      }
    });
    let n = this.userExpenseList.length;
    for (let i = 0; i < n; i++) {
      let index = this.userExpenseList[i].fromUserName.localeCompare(this.loggedUserName);
      if (index == 0) {
        this.amountOwedBy.push(this.userExpenseList[i]);
      } else {
        this.amountOwes.push(this.userExpenseList[i]);
      }
    }
  }

  onExpenseTypeChange(val:any){
    console.log("Event Triggered");
    console.log(val);
    this.type=val;
  }
}
