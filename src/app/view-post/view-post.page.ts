import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import * as moment from 'moment';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.page.html',
  styleUrls: ['./view-post.page.scss'],
})
export class ViewPostPage implements OnInit {

  constructor(private route: ActivatedRoute) { }

  post = {};

  loaded = false;

  ngOnInit() {
  }

  ionViewDidEnter()
  {
    var post = localStorage.getItem('post');

    console.log(post);

    this.post = JSON.parse(post);

    this.loaded = true;
  }

  returnDate(createdOn)
  {
    return moment(createdOn,'DD-MM-YYYY HH:mm:ss').fromNow();
  }

}
