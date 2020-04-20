import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { UserRegistration } from "app/model/user-registartion.model";
import { UserService } from "app/services/user.service";
import { MatDialogConfig, MatDialog } from "@angular/material/dialog";
import { AddGroupComponent } from "app/group/add-group/add-group.component";
import { UpdateUserComponent } from "../update-user/update-user.component";
import { AddExpenseComponent } from "app/expense/add-expense/add-expense.component";
// import { relative } from 'path';

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.scss"],
})
export class DashboardComponent implements OnInit {
  user: UserRegistration;
  token: string;
  userService: UserService;

  constructor(
    private router: Router,
    userService: UserService,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {
    this.userService = userService;
  }

  ngOnInit(): void {
    this.user = this.userService.getUserInDashboard();
    this.token = this.userService.getToken();
    if (!this.token) {
      this.router.navigate(["/login"]);
    }
  }

  logout() {
    this.userService.logout();
    this.router.navigate(["/login"]);
  }

  updateUserAccount() {
    let isAccountClicked: boolean = true;
    this.userService.setUserInDashboard(
      this.user,
      this.token,
      isAccountClicked
    );
    // this.router.navigate(["userPages/edit"]);

    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    this.dialog.open(UpdateUserComponent, dialogConfig);
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
