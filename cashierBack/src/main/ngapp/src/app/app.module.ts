import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
//Angular Material Components
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatSliderModule} from '@angular/material/slider';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatMenuModule} from '@angular/material/menu';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatListModule} from '@angular/material/list';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCardModule} from '@angular/material/card';
import {MatStepperModule} from '@angular/material/stepper';
import {MatTabsModule} from '@angular/material/tabs';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatChipsModule} from '@angular/material/chips';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatDialogModule} from '@angular/material/dialog';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {MatPaginatorModule} from '@angular/material/paginator';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MainNavComponent } from './view/main-wrapper/main-nav/main-nav.component';
import { BookingCardComponent } from './components/booking-card/booking-card.component';
import { OverviewCardComponent } from './view/home/overview-card/overview-card.component';
import { NewTransactionDialogComponent } from './components/new-transaction-dialog/new-transaction-dialog.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http'; 
import { FormsModule, ReactiveFormsModule, FormGroup } from '@angular/forms';
import {MatBadgeModule} from '@angular/material/badge';
import { MccColorPickerModule } from 'material-community-components';
import { TagEditorComponent } from './components/tag-editor/tag-editor.component';
import { ChartCardComponent } from './components/charts/chart-card/chart-card.component';
import { HomeComponent } from './view/home/home.component';
import { LoginComponent } from './view/login/login.component';
import { JwtInterceptor } from './auth/helpers/jwt.interceptor';
import { ErrorInterceptor } from './auth/helpers/error.interceptor';
import { NgxCurrencyModule } from "ngx-currency";
import { StackBarChartCardComponent } from './components/charts/stack-bar-chart-card/stack-bar-chart-card.component';
import { AdministrationComponent } from './view/administration/administration.component';
import { UserSettingsComponent } from './view/user-settings/user-settings.component';
import { UserListComponent } from './view/administration/user-list/user-list.component';
import { UserEditDialogComponent } from './view/administration/user-edit-dialog/user-edit-dialog.component';
import { SidebarContentComponent } from './view/main-wrapper/sidebar-content/sidebar-content.component';
import { MainWrapperComponent } from './view/main-wrapper/main-wrapper.component';
import { TagDetailComponent } from './view/tag-detail/tag-detail.component';
import { ChangePasswordComponent } from './view/user-settings/change-password/change-password.component';
import { EditUserComponent } from './view/user-settings/edit-user/edit-user.component';
import { GravatarModule } from  'ngx-gravatar';
import { ScheduledTaskCardComponent } from './view/user-settings/scheduled-task-card/scheduled-task-card.component';
import { NewScheduledTaskComponent } from './view/user-settings/scheduled-task-card/new-scheduled-task/new-scheduled-task.component';
import { BarChartCardComponent } from './components/charts/bar-chart-card/bar-chart-card.component';
import { InitComponent } from './view/init/init.component';
import { MainFooterComponent } from './view/main-wrapper/main-footer/main-footer.component';
 
@NgModule({
  declarations: [
    AppComponent,
    MainNavComponent,
    BookingCardComponent,
    OverviewCardComponent,
    NewTransactionDialogComponent,
    TagEditorComponent,
    ChartCardComponent,
    HomeComponent,
    LoginComponent,
    StackBarChartCardComponent,
    AdministrationComponent,
    UserSettingsComponent,
    UserListComponent,
    UserEditDialogComponent,
    SidebarContentComponent,
    MainWrapperComponent,
    TagDetailComponent,
    ChangePasswordComponent,
    EditUserComponent,
    ScheduledTaskCardComponent,
    NewScheduledTaskComponent,
    BarChartCardComponent,
    InitComponent,
    MainFooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCheckboxModule,
    MatCheckboxModule,
    MatButtonModule,
    MatInputModule,
    MatAutocompleteModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatRadioModule,
    MatSelectModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatMenuModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatStepperModule,
    MatTabsModule,
    MatExpansionModule,
    MatButtonToggleModule,
    MatChipsModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    MatDialogModule,
    MatTooltipModule,
    MatSnackBarModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    FontAwesomeModule,
    HttpClientModule,
    MatNativeDateModule,
    FormsModule,
    ReactiveFormsModule,
    MatBadgeModule,
    MccColorPickerModule,
    NgxCurrencyModule,
    GravatarModule,
    FormsModule
  ],
  providers: [MatDatepickerModule,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
  entryComponents: [NewTransactionDialogComponent, TagEditorComponent, UserEditDialogComponent, NewScheduledTaskComponent]
})
export class AppModule { 
 

}
