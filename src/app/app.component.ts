import { Component } from '@angular/core';

import { Platform, NavController, Events } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html'
})
export class AppComponent {
  public appPages = [
    {
      title: 'Home',
      url: '/home',
      icon: 'home'
    },
    {
      title: 'My Profile',
      url: '/home',
      icon: 'people'
    },
    {
      title: 'Messages',
      url: '/home',
      icon: 'mail'
    },
    {
      title: 'Starred',
      url: '/home',
      icon: 'star-outline'
    }
  ];

  public user = {};

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private navCtrl: NavController,
    private events: Events
    ) {

    this.initializeApp();

    events.subscribe("setUser", (user) => {
      this.user = user;
      console.log('accepted');
    })
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleBlackTranslucent();
      this.splashScreen.hide();

    });
  }

  logout()
  {
    localStorage.removeItem('user');
    this.navCtrl.navigateRoot('authorization');
  }
}