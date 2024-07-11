import {Component} from '@angular/core';

@Component({
  selector: 'app-mainpage',
  templateUrl: './mainpage.component.html',
  styleUrl: './mainpage.component.css'
})
export class MainpageComponent  {
  images = [
      'assets/carousel_pic',
    'assets/carousel_pic',
    'assets/carousel_pic3.jpg',
    'assets/carousel_pic4.jpg',
  ]
  imageId = 3;
  currImg = '/assets/carousel_pic4.jpg';

  frwrdClick() {
    if (this.imageId == 4) {
      this.imageId = 1;
    }
    else this.imageId += 1;
    this.currImg = 'assets/carousel_pic' + this.imageId + '.jpg';
  }
  backClick() {
    if (this.imageId == 1) {
      this.imageId = 4;
    }
    else this.imageId -= 1;
    this.currImg = 'assets/carousel_pic' + this.imageId + '.jpg';
  }
}