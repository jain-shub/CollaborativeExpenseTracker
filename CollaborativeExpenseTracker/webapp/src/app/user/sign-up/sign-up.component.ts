import { Component, OnInit } from "@angular/core";
import { UtilityService } from "app/services/form-validation-utility.service";
import { UserService } from "app/services/user.service";
import { UserRegistration } from "app/model/user-registartion.model";
import { NgForm } from "@angular/forms";
import { Routes, Router } from "@angular/router";
import { AlertService } from "../../services/alert.service";

@Component({
  selector: "app-sign-up",
  templateUrl: "./sign-up.component.html",
  styleUrls: ["./sign-up.component.scss"],
  providers: [UserService],
})
export class SignUpComponent implements OnInit {
  userList: UserRegistration[] = [];
  user: UserRegistration;
  userService: UserService;
  utility: UtilityService = new UtilityService();
  private _shown = false;
  constructor(
    utility: UtilityService,
    userService: UserService,
    private router: Router,
    private alertService: AlertService
  ) {
    this.utility = utility;
    this.userService = userService;
    this.user = new UserRegistration();
  }

  ngOnInit(): void {}

  addUser() {
    this.userService
      .addUser(
        this.user.firstName,
        this.user.lastName,
        this.user.email,
        this.user.password
      )
      .subscribe(
        (data) => {
          this.userList.push(data);
          this.alertService.success("Registration successful", true);
          this.router.navigate(["/login"]);
        },
        (error) => {
          let err: any;
          err = error;
          err.statusText = "Invalid details entered, Please enter all fields with valid values!";
          this.alertService.error(err.statusText);
        }
      );
  }

  viewPassword() {
    var x = document.getElementById("pwdField");
    this._shown = !this._shown;
    if (this._shown) {
      x.setAttribute("type", "text");
    } else {
      x.setAttribute("type", "password");
    }
  }
}
