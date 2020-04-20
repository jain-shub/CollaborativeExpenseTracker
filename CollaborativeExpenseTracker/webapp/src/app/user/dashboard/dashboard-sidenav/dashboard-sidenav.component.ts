import { Component, OnInit } from "@angular/core";
import { BreakpointObserver, Breakpoints } from "@angular/cdk/layout";
import { Observable } from "rxjs";
import { map, shareReplay } from "rxjs/operators";
import { UtilityService } from "app/services/form-validation-utility.service";
import { AddGroupComponent } from "../../../group/add-group/add-group.component";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { Group } from "app/model/group.model";
import { AddExpenseComponent } from "app/expense/add-expense/add-expense.component";
import { Router } from "@angular/router";

@Component({
  selector: "app-dashboard-sidenav",
  templateUrl: "./dashboard-sidenav.component.html",
  styleUrls: ["./dashboard-sidenav.component.scss"],
})
export class DashboardSidenavComponent {
  opened = false;

  groupList: any[];

  utility: UtilityService = new UtilityService();
  constructor(
    private breakpointObserver: BreakpointObserver,
    utility: UtilityService,
    private dialog: MatDialog,
    private router: Router
  ) {
    this.utility = utility;
  }

  friends = [""];
  groups = [""];

  userName = "ExpenseDistLogo";

  emailMatcher = this.utility.emailMatcher;
  emailFormControl = this.utility.emailFormControl;
  emailTip = this.utility.emailTip;

  friendBalances() {}

  groupBalances() {}

  onCreate() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    this.dialog.open(AddGroupComponent, dialogConfig);
  }

  onAddExpense() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    this.dialog.open(AddExpenseComponent, dialogConfig);
  }

  onBanking() {
    this.router.navigateByUrl("/banking");
  }
}
