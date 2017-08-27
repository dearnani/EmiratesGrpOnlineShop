import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProductInfoComponent } from './product-info.component';
import { ProductInfoDetailComponent } from './product-info-detail.component';
import { ProductInfoPopupComponent } from './product-info-dialog.component';
import { ProductInfoDeletePopupComponent } from './product-info-delete-dialog.component';

export const productInfoRoute: Routes = [
    {
        path: 'product-info',
        component: ProductInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.productInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'product-info/:id',
        component: ProductInfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.productInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productInfoPopupRoute: Routes = [
    {
        path: 'product-info-new',
        component: ProductInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.productInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-info/:id/edit',
        component: ProductInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.productInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-info/:id/delete',
        component: ProductInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.productInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
