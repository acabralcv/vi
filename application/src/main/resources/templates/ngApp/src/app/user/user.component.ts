import { Component, OnInit } from '@angular/core';

@Component({
  selector: '[app-user]',
  template: `<div>
                <div class="">

                    Ng if, switch and For
                    <hr />  

                    <button (click)="showIfElse()">{{showName ? 'Hide' : 'Show'}} </button>
                    <h2 *ngIf="showName; else elseBlock">
                        Ok this an value fron if condition
                    </h2>

                    <ng-template #elseBlock>
                        <h2>This is an hiden block</h2>
                    </ng-template>

                    <br>
                    <br>
                    IF, Then and else

                    <button (click)="showIfElse1()">{{showName1 ? 'Hide' : 'Show'}} </button>
                    <h2 *ngIf="showName1; then thenBlock1 else elseBlock2"></h2>

                    <ng-template #thenBlock1>
                        <h2>This is an hiden block 1</h2>
                    </ng-template>

                    <ng-template #elseBlock2>
                        <h2>This is an hiden block 2</h2>
                    </ng-template>

                    
                    <hr />  
                    <input id="{{inputId}}" type="text" />
                    <input id="{{inputId}}" [disabled]="isDisabled" type="text" value="ID"/>
                    
                    <hr />                    
                    <div class="text-success">Class bindig</div>
                    <div [class]="textSuccess">Class bindig</div>
                    <div [class.text-danger]="hasError">Class bindig</div>

                    <div [ngClass]="messageClass">Class bindig with ngClass</div>

                    <hr />
                    <div [style.color]="'orange'">Style bindig</div>
                    <div [style.color]="hasError ? 'red' : 'sgreen'">Style bindig</div>
                    <div [style.color]="blueColor">Style bindig</div>
                    <div [ngStyle]="titleStyle">Style bindig with ngStyle</div>

                    <hr />
                    <button (click)="onClick()">Onclick</button>
                    <br>
                    <button (click)="onClick1()">Onclick with property</button>
                    <br>Greeting: {{greeting}}
                    <hr />
                    <button (click)="onClick2($event)">Onclick with event</button>


                    <hr />
                    <input #userNameID type="text" /> 
                    <button (click)="logMessage(userNameID.value)">logMessage</button>
                    <br />Your name is: {{userName}}

                    <hr />Model::<br />
                    <input [(ngModel)]="name" type="text" /> 
                    <br />The name type is: {{name}}
                    
                    
                </div>
            </div>`,
  styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit {
    
    public name = 'Adilson cabral';
    public inputId = 'inputId';
    public dataUsers = []
    public url = window.location.href
    public isDisabled = true

    public showName = true
    public showName1 = true

    //class
    public textSuccess = "text-success"
    public hasError = false
    public isPescial = true;
    public messageClass = {
        "text-success": !this.hasError,
        "text-danger": this.hasError,
        "text-special": this.isPescial,
    }

    //style binding
    public blueColor = 'blue'
    public titleStyle = {
        color: "orange",
        fontStyle: "italic"
    }


    constructor() { }

    ngOnInit() {}

    getUser = function(){
        return "My name is " + this.name
    }

    showIfElse = function(){
        this.showName = !this.showName
    }

    showIfElse1 = function(){
        this.showName1 = !this.showName1
    }
    
    
    
    //events
    public greeting = ''
    public userName = ''

    onClick = function(){
        console.log("Cliecked:: ")
    }
    
    onClick1= function(){
        this.greeting = 'Hello this the value updated by event click'
    }
    
    onClick2= function(event){
        console.log(event.type)
    }
    
    logMessage= function(value){
        console.log(value)
        this.userName = value
    }
    
    
    public name = ''

}
