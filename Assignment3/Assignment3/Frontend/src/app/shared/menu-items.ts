import { Injectable } from "@angular/core";

export interface Menu {
    state:string;
    name:string;
    type:string;
    icon:string;
    role:string;
}

const MENUITEMS = [
    {state:'dashboard', name:'Dashboard', type:'link', icon:"sticky_note_2", role:''},
    {state:'category', name:'Manage Category', type:'link', icon:"dynamic_form", role:'admin'},
    {state:'note', name:'Manage Notes', type:'link', icon:"edit_calendar", role:'admin'},
    {state:'report', name:'Manage Reports', type:'link', icon:"print", role:''},
    {state:'view-report', name:'View Reports', type:'link', icon:"backup_table", role:''},
    {state:'user', name:'Manage Users', type:'link', icon:"people", role:'admin'}
]

@Injectable()
export class MenuItems {
    getMenuitem(): Menu[] {
        return MENUITEMS;
    }
}