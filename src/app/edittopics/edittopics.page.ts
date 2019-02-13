import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import { NavController, AlertController } from '@ionic/angular';

@Component({
  selector: 'app-edittopics',
  templateUrl: './edittopics.page.html',
  styleUrls: ['./edittopics.page.scss'],
})
export class EdittopicsPage implements OnInit {

  API = "http://localhost:1337/";

  topics = [
    {
      text: 'Anthropology',
      isChecked: false
    },
    {
      text: 'Art',
      isChecked: false
    },
    {
      text: 'Biology',
      isChecked: false
    },
    {
      text: 'Chemistry',
      isChecked: false
    },
    {
      text: 'Child Development',
      isChecked: false
    },
    {
      text: 'Communication Studies',
      isChecked: false
    },
    {
      text: 'Computer Science',
      isChecked: false
    },
    {
      text: 'Dance',
      isChecked: false
    },
    {
      text: 'Economics',
      isChecked: false
    },
    {
      text: 'Engineering',
      isChecked: false
    },
    {
      text: 'English Literature',
      isChecked: false
    },
    {
      text: 'Environmental Studies',
      isChecked: false
    },
    {
      text: 'Film/TV Production',
      isChecked: false
    },
    {
      text: 'Global Studies',
      isChecked: false
    },
    {
      text: 'Graphic Design',
      isChecked: false
    },
    {
      text: 'History',
      isChecked: false
    },
    {
      text: 'Intercultural Studies',
      isChecked: false
    },
    {
      text: 'Journalism',
      isChecked: false
    },
    {
      text: 'Kinesiology',
      isChecked: false
    },
    {
      text: 'Liberal Arts',
      isChecked: false
    },
    {
      text: 'Philosophy',
      isChecked: false
    },
    {
      text: 'Photography',
      isChecked: false
    },
    {
      text: 'Political Science',
      isChecked: false
    },
    {
      text: 'Psychology',
      isChecked: false
    },
    {
      text: 'Real Estate',
      isChecked: false
    },
    {
      text: 'Sociology',
      isChecked: false
    },
    {
      text: 'Speech Communication',
      isChecked: false
    }
  ];

  constructor(private alertCtrl: AlertController, private navCtrl: NavController) { }

  ngOnInit() {
  }

  ionViewWillEnter()
  {
    var user = JSON.parse(localStorage.getItem('user'));

    for(var i = 0; i < this.topics.length; i++)
    {
      for(var q = 0; q < user.topics.length; q++)
      {
        if(user.topics[q] == this.topics[i].text)
          this.topics[i].isChecked = true;
      }
    }
  }

  applyChanges() {
    var topics = [];

    var user = JSON.parse(localStorage.getItem('user'));

    for(var i = 0; i < this.topics.length; i++)
    {
      if(this.topics[i].isChecked == true) topics.unshift(this.topics[i].text);
    }

    user.topics = topics;

    axios.post(this.API + "applyNewTopics", user)
      .then(() => this.navCtrl.navigateRoot('/home'))
      .catch(err => this.alert("Error", err));
  }

  alert(header, error)
  {
    this.alertCtrl.create({
      header: header,
      subHeader: null,
      message: error
    }).then(alert => alert.present());
  }
}
