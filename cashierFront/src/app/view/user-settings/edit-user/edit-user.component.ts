import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { FrontendUser } from 'src/app/model/user-management/frontend-user';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {
  
  user: FrontendUser = {} as FrontendUser;
  changeCheckUser: FrontendUser = {} as FrontendUser;
  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getUser().subscribe(user => {
     this.user = user;
     this.changeCheckUser = JSON.parse(JSON.stringify(user));

    });
  }
  submitUserdata(){
    console.log(this.user);
    
    this.userService.updateUser(this.user).subscribe(user =>{
      this.user=user;
      this.changeCheckUser = JSON.parse(JSON.stringify(user));
    })
  }
  equalCheck(a,b):boolean{
    return JSON.stringify(a) === JSON.stringify(b);
  }

}
