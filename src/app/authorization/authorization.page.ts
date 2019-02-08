import { Component, OnInit } from '@angular/core';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.page.html',
  styleUrls: ['./authorization.page.scss'],
})
export class AuthorizationPage implements OnInit {

  constructor(private menuCtrl: MenuController) { }

  ngOnInit() {
    console.log("AUTH Opened");
      this.menuCtrl.enable(false);
  }

}
