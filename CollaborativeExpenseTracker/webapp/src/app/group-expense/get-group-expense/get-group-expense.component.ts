import { Component, OnInit } from '@angular/core';
import { GroupService } from 'app/services/group.service';
import { GroupExpense } from 'app/model/group-expense.model';
import { Router } from '@angular/router';
import { GroupExpenseService } from 'app/services/group-expense.service';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { UtilityService } from 'app/services/form-validation-utility.service';
import { UserRegistration } from 'app/model/user-registartion.model';
import { Group } from 'app/model/group.model';

@Component({
  selector: 'app-get-group-expense',
  templateUrl: './get-group-expense.component.html',
  styleUrls: ['./get-group-expense.component.scss']
})
export class GetGroupExpenseComponent implements OnInit {
  form: FormGroup;
  groupService: GroupService;
  groupExpenseList: GroupExpense[];
  groupExpense : GroupExpense;
  dataSource = this.groupExpenseList;
  mems: any[][] = [];
  // groupName: string;
  // members: UserRegistration[];
  // group: Group;

  constructor(private router: Router, private groupExpenseService: GroupExpenseService, private utility:UtilityService, public dialogRef: MatDialogRef<GetGroupExpenseComponent>,) {}
  controls: FormArray;

  ngOnInit(): void {
    this.groupExpenseService.refreshNeeded$.subscribe(() => {
      this.getGroupExpenses();
    });
    this.getGroupExpenses();

  }

  getGroupExpenses(){
    this.groupExpenseService.getGroupExpenses().subscribe((data) => {
      this.groupExpenseList = data;
    });
    this.onPercentGet();
    // this.computeExpenses();
  }

  getControl(index: number, field: string): FormControl {
    return this.controls.at(index).get(field) as FormControl;
  }

  onPercentGet() {
    this.mems.map((items) => {
      items[0] = this.groupExpense.dividePercentage.key;
      items[1]=this.groupExpense.dividePercentage.value*100;
      // this.groupExpense.dividePercentage.set(items[0].id, items[1]/100);
    });
  }

  deleteExpense(groupExpense: GroupExpense) {
    this.groupExpenseService.delete(groupExpense.id).subscribe((data) => {
      this.groupExpenseList = this.groupExpenseList.filter((u) => u !== groupExpense);
    });
  }

  updateRowData(groupExpense: GroupExpense) {
    this.groupExpenseService.update(groupExpense).subscribe((data) => {
    });
  }

  close() {
    this.dialogRef.close();
    localStorage.removeItem("currentGroupId");
  }

}
