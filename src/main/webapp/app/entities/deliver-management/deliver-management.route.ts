import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DeliverManagementComponent } from './deliver-management.component';
import { DeliverManagementDetailComponent } from './deliver-management-detail.component';
import { DeliverManagementPopupComponent } from './deliver-management-dialog.component';
import { DeliverManagementDeletePopupComponent } from './deliver-management-delete-dialog.component';

export const deliverManagementRoute: Routes = [
    {
        path: 'deliver-management',
        component: DeliverManagementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.deliverManagement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'deliver-management/:id',
        component: DeliverManagementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.deliverManagement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deliverManagementPopupRoute: Routes = [
    {
        path: 'deliver-management-new',
        component: DeliverManagementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.deliverManagement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deliver-management/:id/edit',
        component: DeliverManagementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.deliverManagement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deliver-management/:id/delete',
        component: DeliverManagementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.deliverManagement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
