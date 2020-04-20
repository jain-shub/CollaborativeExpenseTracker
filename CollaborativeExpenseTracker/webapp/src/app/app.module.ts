import { BrowserModule } from "@angular/platform-browser";
import { NgModule, NO_ERRORS_SCHEMA } from "@angular/core";

import { AppRoutingModule } from "./app-routing/app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { SignUpComponent } from "./user/sign-up/sign-up.component";
import { HomeComponent } from "./home-component/home-component.component";
import { ViewMaterialModule } from "./view-material/view-material.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import {
  ErrorStateMatcher,
  ShowOnDirtyErrorStateMatcher,
} from "@angular/material/core";
import { LoginComponent } from "./user/login/login.component";
import { DashboardComponent } from "./user/dashboard/dashboard.component";
import { MatInputModule } from "@angular/material/input";
import { AppHeaderComponent } from "./core/app-header/app-header.component";
import { AppFooterComponent } from "./core/app-footer/app-footer.component";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatIconModule } from "@angular/material/icon";
import { MatGridListModule } from "@angular/material/grid-list";
import { MDBBootstrapModule } from "angular-bootstrap-md";
import { UpdateUserComponent } from "./user/update-user/update-user.component";
import { DashboardSidenavComponent } from "./user/dashboard/dashboard-sidenav/dashboard-sidenav.component";
import { LayoutModule } from "@angular/cdk/layout";
import { MatButtonModule } from "@angular/material/button";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatListModule } from "@angular/material/list";
import { MatSelectModule } from '@angular/material/select';
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { AlertComponent } from "./alert/alert.component";
import { AddExpenseComponent } from "./expense/add-expense/add-expense.component";
import { AddGroupComponent } from "./group/add-group/add-group.component";
import { ViewGroupComponent } from "./group/view-group/view-group.component";
import { DeleteGroupComponent } from "./group/delete-group/delete-group.component";
import { AuthInterceptor } from "./interceptor/auth.interceptor";
import { GetExpenseComponent } from "./expense/get-expense/get-expense.component";
import { UserPagesComponent } from "./user/user-pages/user-pages.component";
import { PageNotFoundComponent } from "./page-not-found/page-not-found.component";
import { MatCardModule } from "@angular/material/card";
import { MatDialogModule } from "@angular/material/dialog";
import { GetAllGroupComponent } from "./group/get-all-group/get-all-group.component";
import { AddGroupExpenseComponent } from "./group-expense/add-group-expense/add-group-expense.component";
import { DeleteGroupExpenseComponent } from "./group-expense/delete-group-expense/delete-group-expense.component";
import { GetGroupExpenseComponent } from "./group-expense/get-group-expense/get-group-expense.component";
import { BankingComponent } from "./banking/banking.component";
import { NgxPlaidLinkModule } from "ngx-plaid-link";
import {MatMenuModule} from "@angular/material/menu";
import {MatExpansionModule} from '@angular/material/expansion';

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    HomeComponent,
    LoginComponent,
    DashboardComponent,
    AppHeaderComponent,
    AppFooterComponent,
    UpdateUserComponent,
    DashboardSidenavComponent,
    AlertComponent,
    AddExpenseComponent,
    AddGroupComponent,
    ViewGroupComponent,
    DeleteGroupComponent,
    GetExpenseComponent,
    UserPagesComponent,
    PageNotFoundComponent,
    GetAllGroupComponent,
    AddGroupExpenseComponent,
    DeleteGroupExpenseComponent,
    GetGroupExpenseComponent,
    BankingComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ViewMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatGridListModule,
    MDBBootstrapModule,
    LayoutModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    HttpClientModule,
    MatCardModule,
    MatDialogModule,
    NgxPlaidLinkModule,
    MatSelectModule,
    MatMenuModule,
    MatExpansionModule
  ],
  schemas: [NO_ERRORS_SCHEMA],
  exports: [ViewMaterialModule],
  providers: [
    { provide: ErrorStateMatcher, useClass: ShowOnDirtyErrorStateMatcher },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
  entryComponents: [AddGroupComponent, ViewGroupComponent],
})
export class AppModule {}
