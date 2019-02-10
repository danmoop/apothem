import { Component } from '@angular/core';
import axios from 'axios';
import { AlertController, NavController, ToastController } from '@ionic/angular';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})

export class HomePage {
  public authorized = false;

  public message = "";
  
  public API = "http://localhost:1337/";

  constructor(private toastCtrl: ToastController, private alertCtrl: AlertController, private navCtrl: NavController) {

  }

  ionViewWillEnter()
  {
    var user = localStorage.getItem('user');

    if(user == null) this.navCtrl.navigateRoot('authorization');

    else 
    {
      let ls_user = JSON.parse(localStorage.getItem('user'));

      axios.post(this.API + "getUser", ls_user)
        .then(response => {
          if(response.data == false)
          {
            localStorage.removeItem('user');
            this.navCtrl.navigateRoot('authorization');
          }
          else {
            this.authorized = true;
            this.toastCtrl.create({
              message: "Hello, " + ls_user.name,
              duration: 2500
            }).then(toast => toast.present());
          }
        })
        .catch(err => {
          this.alert("Error", err);
          this.navCtrl.navigateRoot('authorization');
        });
    }
  }

  sendRequest()
  {
    axios.get(this.API + "/hello")
      .then(response => this.message = response.data)
      .catch(err => console.log(err));
  }

  getID(){ 
    axios.get(this.API + "/getId")
      .then(response => this.message = response.data)
      .catch(err => console.log(err));
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

  logout()
  {
    localStorage.removeItem('user');
    this.navCtrl.navigateRoot('authorization');
  }

}
