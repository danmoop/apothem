import { Component, OnInit } from '@angular/core';
import Crypto from 'crypto-js';
import axios from 'axios';
import { AlertController, NavController } from '@ionic/angular';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.page.html',
  styleUrls: ['./signin.page.scss'],
})
export class SigninPage implements OnInit {

  private username = "";
  private password = "";

  public API = "http://localhost:1337/";

  constructor(private navCtrl: NavController, private alertCtrl: AlertController) { }

  ngOnInit() {
  }

  proceed() {
    if(this.username != "" && this.password != "")
    {
      var user = {
        username: this.username,
        password: Crypto.MD5(this.password).toString()
      }

      axios.post(this.API + "login", user)
        .then(response => {
          if(response.data == "") this.alert("Error", "Wrong credentials");
          
          else
          {
            var user1 = {
              name: response.data.name,
              username: response.data.username,
              token: response.data.token,
              topics: response.data.topics
            }

            localStorage.setItem('user', JSON.stringify(user1));

            this.navCtrl.navigateRoot('/home');
          }
        })
        .catch(err => this.alert("Error", err));
    }

    else this.alert("Error", "All fields are required!");
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