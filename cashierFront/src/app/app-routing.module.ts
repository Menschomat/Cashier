import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { HomeComponent } from "./view/home/home.component";
import { LoginComponent } from "./view/login/login.component";
import { AuthGuard } from "./auth/auth.guard";
import { AdministrationComponent } from "./view/administration/administration.component";
import { UserSettingsComponent } from "./view/user-settings/user-settings.component";

const appRoutes: Routes = [
  { path: "", component: HomeComponent, canActivate: [AuthGuard] },
  { path: "login", component: LoginComponent },
  {
    path: "admin",
    component: AdministrationComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "settings",
    component: UserSettingsComponent,
    canActivate: [AuthGuard]
  },

  // otherwise redirect to home
  { path: "**", redirectTo: "" }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
