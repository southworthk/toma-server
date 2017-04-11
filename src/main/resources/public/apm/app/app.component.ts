import {Component} from 'angular2/core';
import {Router} from 'angular2/router';
import {IUser} from './user';
import {ProductService} from './product.service';

@Component({
    selector: 'my-app',
    templateUrl: './app/myapp_template.html'
})


export class AppComponent {
  count = 0;
  title = "Hello World";
  showFeatures = true;
    users: IUser[];
    constructor(private _productService: ProductService){

    }

  increaseCount(){
    this.count++;
  }

  loginUser(){
      this.showFeatures = false;
  }

}
