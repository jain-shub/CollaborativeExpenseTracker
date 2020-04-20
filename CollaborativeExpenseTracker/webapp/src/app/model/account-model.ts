/**
 * Model Account Class
 */
export class Account {
  id: string;
  accessToken: string;
  itemId: string;
  institutionId: string;
  institutionName: string;
  accountName: string;
  accountType: string;
  accountSubtype: string;
  institution: { name: string; institution_id: string };
  publicToken: string;

  constructor() {}
}
