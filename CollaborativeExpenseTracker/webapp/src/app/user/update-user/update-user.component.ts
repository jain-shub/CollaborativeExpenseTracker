import { Component, OnInit } from "@angular/core";
import { NgForm } from "@angular/forms";
import { UserService } from "../../services/user.service";
import { UserRegistration } from "../../model/user-registartion.model";
import { Router } from "@angular/router";
import { MatDialogRef } from "@angular/material/dialog";
import { AlertService } from "app/services/alert.service";

@Component({
  selector: "app-update-user",
  templateUrl: "./update-user.component.html",
  styleUrls: ["./update-user.component.scss"],
})
export class UpdateUserComponent implements OnInit {
  private userEmail: string;
  user: UserRegistration;
  userService: UserService;
  private token: string;
  constructor(
    userService: UserService,
    private router: Router,
    public dialogRef: MatDialogRef<UpdateUserComponent>,
    private alertService: AlertService
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

  onSaveUpdate() {
    this.userService.updateUser(this.user, this.token).subscribe((response) => {
      this.user = response;
      this.alertService.success("Registration successful", true);
    }),
      (error) => {
        console.log(error);
        this.alertService.error(error);
      };
    this.dialogRef.close();
  }

  close() {
    this.dialogRef.close();
  }
}
