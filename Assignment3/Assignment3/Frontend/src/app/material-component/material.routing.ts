import { Routes } from '@angular/router';
import { ManageCategoryComponent } from './manage-category/manage-category.component';
import { RouteGuardService } from '../services/route-guard.service';
import { ManageNoteComponent } from './manage-note/manage-note.component';
import { ManageReportComponent } from './manage-report/manage-report.component';
import { ViewReportComponent } from './view-report/view-report.component';
import { ManageUserComponent } from './manage-user/manage-user.component';


export const MaterialRoutes: Routes = [
    {
        path: 'category', 
        component: ManageCategoryComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },

    {
        path: 'note', 
        component: ManageNoteComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },

    {
        path: 'report', 
        component: ManageReportComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin', 'user']
        }
    },

    {
        path: 'view-report', 
        component: ViewReportComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin', 'user']
        }
    },

    {
        path: 'user', 
        component: ManageUserComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    }
];
