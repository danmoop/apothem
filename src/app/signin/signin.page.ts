import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.page.html',
  styleUrls: ['./signin.page.scss'],
})
export class SigninPage implements OnInit {

  private username = "";
  private password = "";

  constructor() { }

  ngOnInit() {
  }

  proceed() {
    console.log(this.username + "\n" + this.password);
  }
}
