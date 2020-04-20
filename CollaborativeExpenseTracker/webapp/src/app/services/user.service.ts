import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Subject, Observable, throwError } from "rxjs";
import { map, catchError } from "rxjs/operators";
import { UserRegistration } from "../model/user-registartion.model";
import { Expense } from "app/model/expense.model";
import { error } from "protractor";

@Injectable({
  providedIn: "root",
})
export class UserService {
  private users: UserRegistration[] = [];
  private usersUpdated = new Subject<UserRegistration[]>();
  private dashboardUser: UserRegistration;
  private token: string;
  private isAccountClicked: boolean;

  constructor(private http: HttpClient) {}

  getUserList(): Observable<UserRegistration[]> {
    return this.http.get<UserRegistration[]>("http://localhost:8084/api/user");
  }

  setUserInDashboard(
    user: UserRegistration,
    token: string,
    isAccountCliked: boolean
  ) {
    console.log(user);
    console.log(token);
    this.dashboardUser = user;
    this.token = token;
    this.isAccountClicked = isAccountCliked;
  }

  getUserInDashboard() {
    return this.dashboardUser;
  }

  getToken() {
    return this.token;
  }

  isAccountSettingsClicked() {
    return this.isAccountClicked;
  }

  login(email: string, password: string) {
    return this.http.post("http://localhost:8084/api/auth/authenticate", {
      email,
      password,
    });
  }

  // remove user from local storage to log user out
  logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("currentUser");
  }

  getAllUsers() {
    this.http
      .get<{ message: string; users: any }>("http://localhost:8084/api/user")
      .pipe(
        map((userData) => {
          return userData.users.map((user) => {
            return {
              email: user.email,
              userName: user.userName,
            };
          });
        })
      )
      .subscribe((transformedPosts) => {
        this.users = transformedPosts;
        this.usersUpdated.next([...this.users]);
      });
  }

  getPostUpdateListener() {
    return this.usersUpdated.asObservable();
  }

  getUserDetails(userEmail: string) {
    return { ...this.users.find((u) => u.email === userEmail) };
  }

  addUser(
    firstName: string,
    lastName: string,
    userEmail: string,
    password: string
  ) {
    const user: UserRegistration = {
      firstName: firstName,
      lastName: lastName,
      email: userEmail,
      dueExpense: new Expense(),
      owedExpense: new Expense(),
      amountDue: 0,
      amountOwed: 0,
      password: password,
      createdDate: new Date(),
      isVerified: false,
    };
    return this.http.post<UserRegistration>(
      "http://localhost:8084/api/auth",
      user
    );
  }

  updateUser(user: UserRegistration, token: string) {
    let usr = JSON.parse(localStorage.getItem("currentUser")).user.userId;
    console.log(JSON.parse(localStorage.getItem("currentUser")).user.userId);
    console.log("in user service " + user);
    return this.http.post<UserRegistration>(
      "http://localhost:8084/api/user/put/"+ usr,
      user
    );
  }
}
