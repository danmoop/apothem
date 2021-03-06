import { Component, OnInit } from '@angular/core';
import { NavParams, NavController, AlertController, ToastController } from '@ionic/angular';
import { ActivatedRoute } from '@angular/router';

import * as moment from 'moment';

import axios from 'axios';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.page.html',
  styleUrls: ['./topic.page.scss'],
})
export class TopicPage implements OnInit {

  public topic = "";

  public API = "http://localhost:1337/";

  public usersAmount = 0;

  public posts = [];

  loaded = false;

  constructor(private toastCtrl: ToastController, private alertCtrl: AlertController, private navCtrl: NavController, private route: ActivatedRoute) { }

  ngOnInit() {
  }

  ionViewWillEnter()
  {
    this.topic = this.route.snapshot.paramMap.get('topic');

    var _user = this.getUser();

    var user1 = {
      user: _user,
      topic: this.topic
    }

    axios.post(this.API + "isUserSubscribed", user1)
      .then(response => {
        if(response.data == false) this.navCtrl.navigateRoot('/home');
      })
      .catch(err => console.log(err));

    this.getAllPosts();
  }

  ionViewDidEnter()
  {
    this.getUsersSubscribed();
  }

  getUsersSubscribed()
  {
    axios.post(this.API + "getUsersSubscribedOnTopic", {
      topic: this.topic
    })
      .then(response => this.usersAmount = response.data)
      .catch(err => console.log(err));
  }

  getUser()
  {
    return JSON.parse(localStorage.getItem('user'));
  }

  getAllPosts() {
    axios.post(this.API + "getAllPosts", {topic: this.topic})
    .then(response => {
      this.posts = response.data;
      this.posts.reverse();
      this.loaded = true;
    })
    .catch(err => console.log(err));
    
    this.getUsersSubscribed();
  }

  refreshAllPosts(event) {
    axios.post(this.API + "getAllPosts", {topic: this.topic})
    .then(response => {
      this.posts = response.data;
      this.posts.reverse();
      
      if(event !== undefined) event.target.complete();
    })
    .catch(err => console.log(err));

    this.getUsersSubscribed();
    console.log(this.posts);
  }

  returnDate(createdOn)
  {
    return moment(createdOn,'DD.MM.YYYY HH:mm:ss').fromNow();
  }

  post()
  {
    this.navCtrl.navigateForward('/create-post/' + this.topic);
  }

  viewPost(post)
  {
    localStorage.setItem('post', JSON.stringify(post));
    this.navCtrl.navigateForward('/view-post');
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
                this.navCtrl.navigateRoot('/home');
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
}