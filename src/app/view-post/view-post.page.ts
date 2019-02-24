import {
  Component,
  OnInit,
  ViewChild
} from '@angular/core';
import {
  ActivatedRoute
} from '@angular/router';

import * as moment from 'moment';
import axios from 'axios';
import {
  NavController,
  AlertController,
  ToastController
} from '@ionic/angular';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.page.html',
  styleUrls: ['./view-post.page.scss']
})
export class ViewPostPage implements OnInit {

  @ViewChild('content') content: any;

  constructor(private toastCtrl: ToastController, private alertCtrl: AlertController, private navCtrl: NavController,
    private route: ActivatedRoute) {}

  post = null;

  loaded = false;

  user = null;

  API = "http://localhost:1337/";

  commentText = "";

  ngOnInit() {}

  ionViewDidEnter() {
    var post = localStorage.getItem('post');

    this.post = JSON.parse(post);
    this.user = this.getUser();

    this.loaded = true;
  }

  returnDate(createdOn) {
    return moment(createdOn, 'DD.MM.YYYY HH:mm:ss').fromNow();
  }

  deletePost() {
    this.alertCtrl.create({
      header: "Are you sure?",
      subHeader: null,
      message: "Do you really want to delete this post?",
      buttons: [{
          text: 'Yes',
          handler: () => {
            var obj = {
              user: this.user,
              post: this.post
            }

            axios.post(this.API + "deletePost", obj)
              .then(response => {
                this.navCtrl.back();
                this.toastCtrl.create({
                  message: 'Deleted',
                  duration: 1500
                }).then(alert => alert.present());
              })
              .catch(err => this.alert("Error", err));
          }
        },
        {
          text: 'No'
        }
      ]
    }).then(alert => alert.present());
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user'));
  }

  alert(header, message) {
    this.alertCtrl.create({
      header: header,
      subHeader: null,
      message: message,
      buttons: ['OK']
    }).then(alert => alert.present());
  }

  refreshPost(event) {
    axios.post(this.API + "refreshPost", this.post)
      .then(response => {
        this.post = response.data;
        if (event != null) event.target.complete();
      })
      .catch(err => this.alert("Error", err));
  }

  sendComment() {
    var obj = {
      user: this.user,
      comment: {
        author: this.user.username,
        message: this.commentText
      },
      post: this.post
    }

    axios.post(this.API + "publishAComment", obj)
      .then(response => this.post = response.data)
      .catch(err => this.alert("Error", err));

    this.commentText = "";

    setTimeout(() => {
      this.content.scrollToBottom(500);
    }, 500);
  }

  toggleMenu() {
    this.alertCtrl.create({
      header: 'Action',
      subHeader: null,
      message: null,
      buttons: [{
          text: 'Refresh',
          handler: () => {
            this.refreshPost(null);

            setTimeout(() => {
              this.content.scrollToBottom(500);

              this.toastCtrl.create({
                message: 'Refreshed',
                duration: 1500
              }).then(alert => alert.present());
            }, 500);
          }
        },
        {
          text: 'Delete',
          role: 'cancel',
          handler: () => {
            this.deletePost();
          }
        }
      ]
    }).then(alert => alert.present());
  }
}