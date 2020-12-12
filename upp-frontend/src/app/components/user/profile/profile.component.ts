import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthQuery, AuthService } from 'src/app/shared';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  id: number;
  userSub: Subscription;

  constructor(
    private authService: AuthService,
    private authQuery: AuthQuery
  ) { }

  ngOnInit() {
    
    this.userSub = this.authQuery.user$.subscribe((user) => {
      this.id = user.id;
    });

    this.authService.profile(this.id).subscribe((foundUser) => {
      console.log(foundUser);
    });
  }

}
