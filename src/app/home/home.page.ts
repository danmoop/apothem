import { Component } from '@angular/core';
import axios from 'axios';
import { AlertController, NavController, ToastController, MenuController } from '@ionic/angular';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})

export class HomePage {
  public authorized = false;

  public message = "";
  
  public API = "http://localhost:1337/";

  public user = null;

  public cards = [
    {
      name: 'Math',
      content: 'Everything about math'
    },
    {
      name: 'Physics',
      content: 'Everything about physics'
    },
    {
      name: 'Computer Science',
      content: 'Everything about CS'
    },
    {
      name: 'History',
      content: 'Everything about history'
    }
  ];

  constructor(private menuCtrl: MenuController, private toastCtrl: ToastController, private alertCtrl: AlertController, private navCtrl: NavController) {

  }

  ionViewWillEnter()
  {
    var user = localStorage.getItem('user');

    if(user == null) this.navCtrl.navigateRoot('authorization');

    else 
    {
      this.toastCtrl.create({
        message: "Hello, " + JSON.parse(localStorage.getItem('user')).name,
        duration: 2000
      }).then(toast => toast.present());
      this.refreshProfile();        
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

  refreshProfile()
  {
    let ls_user = JSON.parse(localStorage.getItem('user'));

    axios.post(this.API + "getUser", ls_user)
      .then(response => {
        if(response.data == "")
        {
          localStorage.removeItem('user');
          this.navCtrl.navigateRoot('authorization');
        }
        else {
          if(!this.authorized) this.authorized = true;
          
          localStorage.setItem('user', JSON.stringify(response.data));
          this.user = response.data;
          this.menuCtrl.enable(true);
        }
      })
      .catch(err => {
        this.alert("Error", err);
        this.navCtrl.navigateRoot('authorization');
      });
  }

}
