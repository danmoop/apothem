import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import axios from 'axios';
import { AlertController, NavController } from '@ionic/angular';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.page.html',
  styleUrls: ['./profile-page.page.scss'],
})
export class ProfilePagePage implements OnInit {

  API = "http://localhost:1337/"; 
  user = null;
  loaded = false;

  constructor(private navCtrl: NavController, private alertCtrl: AlertController, private route: ActivatedRoute) { }

  ngOnInit() {
  }

  ionViewDidEnter()
  {
    var user = this.route.snapshot.paramMap.get('username');
    
    axios.post(this.API + "getUserProfile", {username: user})
      .then(response => {

        if(response.data != ''){
          this.user = response.data;
          this.loaded = true;
        }

        else
          this.navCtrl.navigateRoot('/home');
      })
      .catch(err => this.alert("Error", err));
  }
  
  alert(header, message)
  {
    this.alertCtrl.create({
      header: header,
      subHeader: null,
      message: message,
      buttons: ['OK']
    }).then(alert => alert.present());
  }

  createNewDialog()
  {
    var obj = {
      user: this.getUser(),
      recepient: this.user.username
    }

    axios.post(this.API + "createNewDialog", obj)
      .then(response => {
        this.navCtrl.navigateRoot('messages');
      })
      .catch(err => this.alert("Error", err));
  }

  getUser()
  {
    return JSON.parse(localStorage.getItem('user'));
  }
}
