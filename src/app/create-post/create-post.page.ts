import { Component, OnInit } from '@angular/core';

import { EdittopicsPage } from './../edittopics/edittopics.page';
import { AlertController, NavController, LoadingController } from '@ionic/angular';

import axios from 'axios';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.page.html',
  styleUrls: ['./create-post.page.scss'],
})
export class CreatePostPage implements OnInit {

  selectedTopic = "";
  title = "";
  content = "";

  public API = "http://localhost:1337/";

  topics = [
    {
      text: 'Anthropology',
    },
    {
      text: 'Art',
    },
    {
      text: 'Biology',
    },
    {
      text: 'Chemistry',
    },
    {
      text: 'Child Development'
    },
    {
      text: 'Communication Studies',
    },
    {
      text: 'Computer Science',
    },
    {
      text: 'Dance',
    },
    {
      text: 'Economics',
    },
    {
      text: 'Engineering',
    },
    {
      text: 'English Literature',
    },
    {
      text: 'Environmental Studies',
    },
    {
      text: 'Film/TV Production',
    },
    {
      text: 'Global Studies',
    },
    {
      text: 'Graphic Design',
    },
    {
      text: 'History',
    },
    {
      text: 'Intercultural Studies',
    },
    {
      text: 'Journalism',
    },
    {
      text: 'Kinesiology',
    },
    {
      text: 'Liberal Arts',
    },
    {
      text: 'Math',
    },
    {
      text: 'Philosophy',
    },
    {
      text: 'Photography',
    },
    {
      text: 'Physics',
    },
    {
      text: 'Political Science',
    },
    {
      text: 'Psychology',
    },
    {
      text: 'Real Estate',
    },
    {
      text: 'Sociology',
    },
    {
      text: 'Speech Communication',
    }
  ];
  
  constructor(private route: ActivatedRoute, private loadingCtrl: LoadingController, private navCtrl: NavController, private alertCtrl: AlertController) { }

  ngOnInit() {
  }

  ionViewDidEnter()
  {
    var topic = this.route.snapshot.paramMap.get('topic');

    if(topic != '') 
      this.selectedTopic = topic;
  }

  post()
  {
    if(this.areInputsValid())
    {
      var _post = {
        author: this.getUser().username,
        content: this.content,
        title: this.title,
        topic: this.selectedTopic
      }
  
      var finalObject = {
        user: this.getUser(),
        post: _post
      }
  
      if(this.getUser().topics.indexOf(this.selectedTopic) == -1)
        this.subscribe(this.selectedTopic);
  
      axios.post(this.API + "publishAPost", finalObject)
        .then(response => {
          if(response.data == true)
            this.navCtrl.navigateRoot('/topic/' + this.selectedTopic);
          else this.alert("Error", "Unable to post");
        })
        .catch(err => this.alert("Error", err));
    }

    else this.alert("Error", "All fields are required!");
  }

  getUser()
  {
    return JSON.parse(localStorage.getItem('user'));
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

  subscribe(topic) {

    var _user = this.getUser();

    var user1 = {
      user: _user,
      item: topic
    }

    axios.post(this.API + "subscribeToTopic", user1)
      .then(() => {
      })
      .catch(err => this.alert("Error", err));
  }

  areInputsValid()
  {
    return this.selectedTopic != "" && this.title != "" && this.content != "";
  }
}
