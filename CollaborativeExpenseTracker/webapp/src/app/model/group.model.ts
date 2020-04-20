import { UserRegistration } from './user-registartion.model';

export class Group {
  groupId: string ="";
  name: string;
  groupType: string = "DEFAULT";
  balance: number;
  createdBy: string = "";
  memberEmail: Array<string>;
  memberEmailString: string;
  members: any[];

  constructor() {}
}
