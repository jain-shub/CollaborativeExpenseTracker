import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "../home-component/home-component.component";
import { LoginComponent } from "../user/login/login.component";
import { DashboardComponent } from "../user/dashboard/dashboard.component";
import { SignUpComponent } from "../user/sign-up/sign-up.component";
import { UpdateUserComponent } from "../user/update-user/update-user.component";
import { AddExpenseComponent } from "../expense/add-expense/add-expense.component";
import { UserPagesComponent } from "app/user/user-pages/user-pages.component";
import { PageNotFoundComponent } from "app/page-not-found/page-not-found.component";
import { AddGroupComponent } from "../group/add-group/add-group.component";
import { BankingComponent } from "app/banking/banking.component";

const routes: Routes = [
  {
    path: "",
    component: HomeComponent,
    pathMatch: "full",
  },
  {
    path: "login",
    component: LoginComponent,
  },
  {
    path: "banking",
    component: BankingComponent,
  },
  {
    path: "signup",
    component: SignUpComponent,
  },
  {
    path: "userPages",
    component: UserPagesComponent,
    children: [
      {
        path: "dashboard",
        component: DashboardComponent,
      },
      {
        path: "addexpense",
        component: AddExpenseComponent,
      },
      {
        path: "edit",
        component: UpdateUserComponent,
      },
      {
        path: "addGroup",
        component: AddGroupComponent,
      },
    ],
  },
  { path: "**", component: PageNotFoundComponent },
];

@NgModule({
  declarations: [],
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
