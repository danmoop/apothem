import { Component } from '@angular/core';
import axios from 'axios';
import { AlertController, NavController } from '@ionic/angular';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})

export class HomePage {
  public authorized = false;

  public message = "";
  
  public API = "http://localhost:1337/";

  constructor(private alertCtrl: AlertController, private navCtrl: NavController) {

  }

  ionViewWillEnter()
  {
    var user = localStorage.getItem('user');

    if(user == null) this.navCtrl.navigateRoot('authorization');

    else this.authorized = true;
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

}
