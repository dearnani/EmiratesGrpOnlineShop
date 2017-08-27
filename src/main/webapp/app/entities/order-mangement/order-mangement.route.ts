import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OrderMangementComponent } from './order-mangement.component';
import { OrderMangementDetailComponent } from './order-mangement-detail.component';
import { OrderMangementPopupComponent } from './order-mangement-dialog.component';
import { OrderMangementDeletePopupComponent } from './order-mangement-delete-dialog.component';

export const orderMangementRoute: Routes = [
    {
        path: 'order-mangement',
        component: OrderMangementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.orderMangement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'order-mangement/:id',
        component: OrderMangementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.orderMangement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderMangementPopupRoute: Routes = [
    {
        path: 'order-mangement-new',
        component: OrderMangementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.orderMangement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-mangement/:id/edit',
        component: OrderMangementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.orderMangement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-mangement/:id/delete',
        component: OrderMangementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.orderMangement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
