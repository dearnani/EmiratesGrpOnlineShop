import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DealerInfoComponent } from './dealer-info.component';
import { DealerInfoDetailComponent } from './dealer-info-detail.component';
import { DealerInfoPopupComponent } from './dealer-info-dialog.component';
import { DealerInfoDeletePopupComponent } from './dealer-info-delete-dialog.component';

export const dealerInfoRoute: Routes = [
    {
        path: 'dealer-info',
        component: DealerInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.dealerInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dealer-info/:id',
        component: DealerInfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.dealerInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dealerInfoPopupRoute: Routes = [
    {
        path: 'dealer-info-new',
        component: DealerInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.dealerInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dealer-info/:id/edit',
        component: DealerInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.dealerInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dealer-info/:id/delete',
        component: DealerInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'emiratesGrpOnlineShopApp.dealerInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
