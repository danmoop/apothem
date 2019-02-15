import { Component, OnInit } from '@angular/core';
import { NavParams, NavController } from '@ionic/angular';
import { ActivatedRoute } from '@angular/router';

import axios from 'axios';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.page.html',
  styleUrls: ['./topic.page.scss'],
})
export class TopicPage implements OnInit {

  public topic: string = "";

  public API = "http://localhost:1337/";

  public users = [];

  public posts = [];

  constructor(private navCtrl: NavController, private route: ActivatedRoute) { }

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
    axios.post(this.API + "getUsersSubscribedOnTopic", {
      topic: this.topic
    })
      .then(response => this.users = response.data)
      .catch(err => console.log(err));
  }

  getUser()
  {
    return JSON.parse(localStorage.getItem('user'));
  }

  createTopic() {
    
    var post = {
      author: this.getUser().username,
      title: "Random",
      topic: this.topic
    }

    axios.post(this.API + "createSomePosts", post)
      .then(response => console.log(response))
      .catch(err => console.log(err));
  }

  getAllPosts(event) {
    axios.post(this.API + "getAllPosts", {topic: this.topic})
    .then(response => {
      this.posts = response.data;

      if(event !== undefined) event.target.complete();
    })
    .catch(err => console.log(err));
  }
}
