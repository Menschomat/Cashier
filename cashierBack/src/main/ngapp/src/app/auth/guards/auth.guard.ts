import { Injectable } from "@angular/core";
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
} from "@angular/router";
import { UserService } from "src/app/services/user.service";
import { map, catchError } from "rxjs/operators";
import { throwError, empty, of } from "rxjs";

@Injectable({ providedIn: "root" })
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private userService: UserService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    let out = this.userService.getUser().pipe(
      map((user: any) => {
        if (localStorage.getItem("cashierUserToken") && user.initialized) {
          return true;
        } else if (
          localStorage.getItem("cashierUserToken") &&
          user &&
          !user.initialized
        ) {
          this.router.navigate(["/init"]);
          return false;
        }
        this.router.navigate(["/login"], {
          queryParams: { returnUrl: state.url }
        });
        return false;
      }),

      catchError(err => {
        this.router.navigate(["/login"], {
          queryParams: { returnUrl: state.url }
        });
        return of(false);
      })
    );

    if (out) return out;
    this.router.navigate(["/login"]);
    return false;
  }
}
