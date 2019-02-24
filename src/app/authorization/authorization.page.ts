import { Component, OnInit } from '@angular/core';
import { MenuController, NavController } from '@ionic/angular';

import { CreateaccountPage } from './../createaccount/createaccount.page';

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.page.html',
  styleUrls: ['./authorization.page.scss'],
})
export class AuthorizationPage implements OnInit {

  constructor(private navCtrl: NavController, private menuCtrl: MenuController) { }

  ngOnInit() {
    console.log("AUTH Opened");
      this.menuCtrl.enable(false);
  }

  showModal()
  {
    this.navCtrl.navigateForward('/createaccount');
  }

  signIn()
  {
    this.navCtrl.navigateForward('/signin');
  }

}
