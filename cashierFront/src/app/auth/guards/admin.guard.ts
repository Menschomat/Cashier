import { Injectable } from "@angular/core";
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
} from "@angular/router";
import { UserService } from "../../services/user.service";
import { map } from "rxjs/operators";

@Injectable({ providedIn: "root" })
export class AdminGuard implements CanActivate {
  constructor(private router: Router, private userService: UserService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.userService.getUser().pipe(
      map((user: any) => {
        if (localStorage.getItem("cashierUserToken") && user.role == "ADMIN") {
          return true;
        }
        this.router.navigate(["/"]);
        return false;
      })
    );
  }
}
