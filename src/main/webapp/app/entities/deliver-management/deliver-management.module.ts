import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmiratesGrpOnlineShopSharedModule } from '../../shared';
import {
    DeliverManagementService,
    DeliverManagementPopupService,
    DeliverManagementComponent,
    DeliverManagementDetailComponent,
    DeliverManagementDialogComponent,
    DeliverManagementPopupComponent,
    DeliverManagementDeletePopupComponent,
    DeliverManagementDeleteDialogComponent,
    deliverManagementRoute,
    deliverManagementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...deliverManagementRoute,
    ...deliverManagementPopupRoute,
];

@NgModule({
    imports: [
        EmiratesGrpOnlineShopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DeliverManagementComponent,
        DeliverManagementDetailComponent,
        DeliverManagementDialogComponent,
        DeliverManagementDeleteDialogComponent,
        DeliverManagementPopupComponent,
        DeliverManagementDeletePopupComponent,
    ],
    entryComponents: [
        DeliverManagementComponent,
        DeliverManagementDialogComponent,
        DeliverManagementPopupComponent,
        DeliverManagementDeleteDialogComponent,
        DeliverManagementDeletePopupComponent,
    ],
    providers: [
        DeliverManagementService,
        DeliverManagementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EmiratesGrpOnlineShopDeliverManagementModule {}
