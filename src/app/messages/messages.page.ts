import { Component, OnInit } from '@angular/core';

import axios from 'axios';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.page.html',
  styleUrls: ['./messages.page.scss'],
})
export class MessagesPage implements OnInit {

  user = {};

  API = "http://localhost:1337/";

  ngOnInit(){

  }

  constructor() { }

  ionViewDidEnter()
  {
    this.refreshProfile(null);
  }

  getUser()
  {
    return JSON.parse(localStorage.getItem('user'));
  }

  refreshProfile(event)
  {
    axios.post(this.API + "getUser", this.getUser())
      .then(response => {
        if(response.data == "")
        {
          localStorage.removeItem('user');
        }
        else {
          localStorage.setItem('user', JSON.stringify(response.data));
          this.user = response.data;
          if(event != null) event.target.complete();
        }
      })
      .catch(err => {
        console.log(err);
      });
  }

  deleteDialog(dialog)
  {
    var obj = {
      user: this.getUser(),
      dialog: dialog
    }

    axios.post(this.API + "removeDialog", obj)
      .then(response => {
        this.user = response.data;
      })
      .catch(err => console.log(err));
  }
}
