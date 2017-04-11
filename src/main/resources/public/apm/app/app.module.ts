import {NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { AppComponent }  from './app.component';
import { TOMUpdateComponent} from './tom/tomupdate.component';


@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        RouterModule.forRoot([
            { path: 'tom', component: TOMUpdateComponent },
            { path: '', redirectTo: 'welcome', pathMatch: 'full' },
            { path: '**', redirectTo: 'http://www.google.com', pathMatch: 'full' }
        ])
    ],
    declarations: [
        AppComponent,
        TOMUpdateComponent,
        ROUTER_DIRECTIVES
    ],
    bootstrap: [ AppComponent ]
})
export class AppModule { }