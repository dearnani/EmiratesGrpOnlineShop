import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmiratesGrpOnlineShopSharedModule } from '../../shared';
import {
    OrderMangementService,
    OrderMangementPopupService,
    OrderMangementComponent,
    OrderMangementDetailComponent,
    OrderMangementDialogComponent,
    OrderMangementPopupComponent,
    OrderMangementDeletePopupComponent,
    OrderMangementDeleteDialogComponent,
    orderMangementRoute,
    orderMangementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...orderMangementRoute,
    ...orderMangementPopupRoute,
];

@NgModule({
    imports: [
        EmiratesGrpOnlineShopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrderMangementComponent,
        OrderMangementDetailComponent,
        OrderMangementDialogComponent,
        OrderMangementDeleteDialogComponent,
        OrderMangementPopupComponent,
        OrderMangementDeletePopupComponent,
    ],
    entryComponents: [
        OrderMangementComponent,
        OrderMangementDialogComponent,
        OrderMangementPopupComponent,
        OrderMangementDeleteDialogComponent,
        OrderMangementDeletePopupComponent,
    ],
    providers: [
        OrderMangementService,
        OrderMangementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EmiratesGrpOnlineShopOrderMangementModule {}
