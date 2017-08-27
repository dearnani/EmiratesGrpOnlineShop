import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CustomerInfoComponent } from './customer-info.component';
import { CustomerInfoDetailComponent } from './customer-info-detail.component';
import { CustomerInfoPopupComponent } from './customer-info-dialog.component';
import { CustomerInfoDeletePopupComponent } from './customer-info-delete-dialog.component';

export const customerInfoRoute: Routes = [
    {
        path: 'customer-info',
        component: CustomerInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.customerInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'customer-info/:id',
        component: CustomerInfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.customerInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const customerInfoPopupRoute: Routes = [
    {
        path: 'customer-info-new',
        component: CustomerInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.customerInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-info/:id/edit',
        component: CustomerInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.customerInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-info/:id/delete',
        component: CustomerInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.customerInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
