import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';

import { IUser } from './user';

@Injectable()
export class ProductService {
    private _userUrl = 'https://jsonplaceholder.typicode.com/users';

    constructor(private _http: Http) { }

    getProducts(): Observable<IUser[]> {
        return this._http.get(this._userUrl)
            .map((response: Response) => <IUser[]> response.json())
            .do(data => console.log('All: ' +  JSON.stringify(data)))
            .catch(this.handleError);
    }

    getProduct(id: number): Observable<IUser> {
        return this.getUsers()
            .map((users: IUser[]) => users.find(p => p.id === id));
    }

    private handleError(error: Response) {
        // in a real world app, we may send the server to some remote logging infrastructure
        // instead of just logging it to the console
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}
