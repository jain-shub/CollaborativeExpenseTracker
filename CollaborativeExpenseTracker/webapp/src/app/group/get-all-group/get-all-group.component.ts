import { Component, OnInit } from "@angular/core";
import { AlertService } from "../../services/alert.service";
import { GroupService } from "../../services/group.service";
import { Group } from "app/model/group.model";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { ViewGroupComponent } from "../../group/view-group/view-group.component";
import { UtilityService } from 'app/services/form-validation-utility.service';

@Component({
  selector: "app-get-all-group",
  templateUrl: "./get-all-group.component.html",
  styleUrls: ["./get-all-group.component.scss"],
})
export class GetAllGroupComponent implements OnInit {
  groupList: Group[];
  group: Group;

  constructor(
    private alertService: AlertService,
    private groupService: GroupService,
    private dialog: MatDialog,
    private utiliy: UtilityService

  ) {
    this.group = new Group();
  }

  ngOnInit(): void {
    this.groupService.refreshNeeded$.subscribe(() => {
      this.getAllGroups();
    });
    this.getAllGroups();
  }

  getAllGroups() {
    console.log("in get all groups");
    this.groupService.getGroupDetails().subscribe(
      (data) => {
        console.log(data);
        this.groupList = data;
      },
      (error) => {
        console.log(error);
        this.alertService.error(error);
      }
    );
  }

  onCreate(group: Group) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    console.log("Selected Group");
    console.log(group);
    this.utiliy.data = {
      id: group.groupId,
      name: group.name,
      groupType: group.groupType,
      memberEmail: group.members,
      createdBy: group.createdBy,
      memberEmailString: group.memberEmailString
    };
    console.log(this.utiliy.data);
    console.log("Selected 1");
    this.dialog.open(ViewGroupComponent, this.utiliy.data);
  }
}
