import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GitService} from "../git.service";

@Component({
  selector: 'app-aboutuspage',
  templateUrl: './aboutuspage.component.html',
  styleUrl: './aboutuspage.component.css'
})
export class AboutuspageComponent implements OnInit{
  quk1seData: any;
  haguelData: any;

  devData: any[] = [];

  constructor(private gitService: GitService) { }

  ngOnInit(): void {
    this.quk1seData = localStorage.getItem('quk1seData');

    if (this.quk1seData) {
      this.quk1seData = JSON.parse(this.quk1seData);
      this.devData.push(this.quk1seData);
    } else {
      console.log('No user data found in localStorage');
    }
    console.log(this.quk1seData);

    this.haguelData = localStorage.getItem('haguelData');

    if (this.haguelData) {
      this.haguelData = JSON.parse(this.haguelData);
      this.devData.push(this.haguelData);
    } else {
      console.log('No user data found in localStorage');
    }

  }

  goToUserGithub(link: string) {
    window.location.href = link;
  }
}
