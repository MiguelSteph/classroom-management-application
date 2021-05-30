import { Component, OnInit } from "@angular/core";
import { UserService } from "../../core/services/user.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { faCheckSquare, faTimes } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "users-list",
  templateUrl: "./users-list.component.html",
  styleUrls: ["./users-list.component.css"],
})
export class UsersListComponent implements OnInit {
  role: string = "";
  roles: Array<any> = [
    { name: "Administrator", role: "ROLE_ADMIN" },
    { name: "Supervisor", role: "ROLE_SUPERVISOR" },
    { name: "Group Leader", role: "ROLE_GROUPLEADER" },
  ];
  users: Array<any> = [];
  searchInput: string = "";

  faCheckSquareIcon = faCheckSquare;
  faTimesIcon = faTimes;

  constructor(
    private userService: UserService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.loadUsersPerRoles();
  }

  loadUsersPerRoles = () => {
    if (this.role == "") {
      this.role = this.roles[0]["role"];
    }
    this.userService.getUsersByRole(this.role).subscribe((data) => {
      this.users = data as Object[];
    });
  };

  filterUsers = () => {
    if (this.searchInput.length === 0) {
      return this.users;
    }
    return this.users.filter((user) => {
      return (
        user.firstName.toUpperCase().indexOf(this.searchInput.toUpperCase()) >=
          0 ||
        user.lastName.toUpperCase().indexOf(this.searchInput.toUpperCase()) >=
          0 ||
        user.email.toUpperCase().indexOf(this.searchInput.toUpperCase()) >= 0
      );
    });
  };

  showAddOrUpdateUserModal(content) {
    this.modalService
      .open(content, { ariaLabelledBy: "modal-basic-title" })
      .result.then((result) => {
        if (result === "UserAdded" || result === "UserUpdated") {
          this.loadUsersPerRoles();
        }
      });
  }
}
