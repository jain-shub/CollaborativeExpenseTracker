import { Component, OnInit } from "@angular/core";
import { UtilityService } from "app/services/form-validation-utility.service";
import { UserRegistration } from "app/model/user-registartion.model";
import { Router } from "@angular/router";
import { AlertService } from "../../services/alert.service";
import { UserService } from "app/services/user.service";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  http: HttpClient;
  utility: UtilityService;
  private userServiceObj: UserService;
  user: UserRegistration;
  private token: string;
  private _shown = false;

  constructor(
    utilityObj: UtilityService,
    private router: Router,
    private alertService: AlertService,
    private userService: UserService
  ) {
    //Logout if already Logged in
    this.userService.logout();
    this.utility = utilityObj;
    this.userServiceObj = userService;
    this.user = new UserRegistration();
  }

  ngOnInit(): void {}

  login() {
    this.userService.login(this.user.email, this.user.password).subscribe(
      (response: Response) => {
        // login successful if there's a jwt token in the response
        let usr: any;
        usr = response;
        console.log(JSON.stringify(usr.user));
        console.log(usr.jwtToken);
        if (usr.jwtToken) {
          console.log(JSON.stringify(usr));
          // store user details and jwt token in local storage to keep usr logged in between page refreshes
          localStorage.setItem("currentUser", JSON.stringify(usr));
          localStorage.setItem("token", usr.jwtToken);
          this.user = usr as UserRegistration;
          this.token = usr.jwtToken;
        }
        this.userServiceObj.setUserInDashboard(this.user, this.token, false);
        this.router.navigate(["userPages"]);
      },
      (error) => {
        let err: any;
        err = error;
        err.statusText = "Invalid credentials, Please enter correct email and password!";
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
