import { Component, OnInit } from '@angular/core';
import { AlertController, NavController } from '@ionic/angular';
import axios from 'axios';

import Crypto from 'crypto-js';

@Component({
  selector: 'app-createaccount',
  templateUrl: './createaccount.page.html',
  styleUrls: ['./createaccount.page.scss'],
})
export class CreateaccountPage implements OnInit {

  private name = "";
  private username = "";
  private password = "";

  API = "http://localhost:1337/";

  constructor(private navCtrl: NavController, private alertCtrl: AlertController) { }

  ngOnInit() {
  }

  proceed()
  {
    if(this.name != "" && this.username != "" && this.password != "")
    {
      var user = {
        name: this.name,
        username: this.username,
        password: Crypto.MD5(this.password).toString()
      }

      axios.post(this.API + "register", user)
        .then(response => {
          if(response.data == "Success")
          {
            let modal = this.alertCtrl.create({
              header: "Success",
              subHeader: null,
              message: "Account registered successfully. Now you can sign in",
              buttons: ['Ok']
            }).then(modal => modal.present()).finally(() => this.navCtrl.navigateRoot('/signin'));
          }
          else this.alert("Oops", "This username is already taken");
        })
        .catch(err => this.alert("Error", err));
    }

    else
      this.alert("Oops", "All fields are required!");
  }

  alert(header, message)
  {
    let modal = this.alertCtrl.create({
      header: header,
      subHeader: null,
      message: message,
      buttons: ['OK']
    }).then(modal => modal.present());
  }

}
