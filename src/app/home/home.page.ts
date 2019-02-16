import { Component } from '@angular/core';
import axios from 'axios';
import { AlertController, NavController, ToastController, MenuController } from '@ionic/angular';
import { NavigationExtras } from '@angular/router';

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
      name: 'Sociology',
      content: 'Everything about sociology'
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
    var user = this.getUser();

    if(user == null) this.navCtrl.navigateRoot('authorization');

    else 
      this.refreshProfile();        
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

    let ls_user = this.getUser();
    
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

  editTopics()
  {
    this.navCtrl.navigateForward('/edittopics');
  }

  unsubscribe(topic)
  {
    this.alertCtrl.create({
      header: "Confirm",
      subHeader: null,
      message: "Do you really want to unsubscribe from " + topic + "?",
      buttons: [
        {
          text: 'Yes',
          handler: () => {
            var _user = this.getUser();

            var user1 = {
              user: _user,
              item: topic
            }

            axios.post(this.API + "unsubscribeFromTopic", user1)
              .then(() => {
                this.toastCtrl.create({
                  message: "Unsubscribed from " + topic,
                  duration: 2000
                }).then(toast => toast.present());

                this.refreshProfile();
              })
              .catch(err => console.log(err));
          }
        },
        {
          text: 'No',
        }
      ]
    }).then(alert => alert.present());
  }

  subscribe(topic) {

    var _user = this.getUser();

    var user1 = {
      user: _user,
      item: topic
    }

    axios.post(this.API + "subscribeToTopic", user1)
      .then(() => {
        this.toastCtrl.create({
          message: "Subscribed to " + topic,
          duration: 2000
        }).then(toast => toast.present());

        this.refreshProfile();
      })
      .catch(err => this.alert("Error", err));
  }

  exploreTopic(topic)
  {
    this.navCtrl.navigateForward('/topic/' + topic);
  }

  getUser()
  {
    return JSON.parse(localStorage.getItem('user'));
  }

  openCreatePostPage()
  {
    this.navCtrl.navigateForward('/create-post');
  }

}