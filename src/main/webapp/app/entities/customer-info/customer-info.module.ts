import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmiratesGrpOnlineShopSharedModule } from '../../shared';
import {
    CustomerInfoService,
    CustomerInfoPopupService,
    CustomerInfoComponent,
    CustomerInfoDetailComponent,
    CustomerInfoDialogComponent,
    CustomerInfoPopupComponent,
    CustomerInfoDeletePopupComponent,
    CustomerInfoDeleteDialogComponent,
    customerInfoRoute,
    customerInfoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...customerInfoRoute,
    ...customerInfoPopupRoute,
];

@NgModule({
    imports: [
        EmiratesGrpOnlineShopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CustomerInfoComponent,
        CustomerInfoDetailComponent,
        CustomerInfoDialogComponent,
        CustomerInfoDeleteDialogComponent,
        CustomerInfoPopupComponent,
        CustomerInfoDeletePopupComponent,
    ],
    entryComponents: [
        CustomerInfoComponent,
        CustomerInfoDialogComponent,
        CustomerInfoPopupComponent,
        CustomerInfoDeleteDialogComponent,
        CustomerInfoDeletePopupComponent,
    ],
    providers: [
        CustomerInfoService,
        CustomerInfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EmiratesGrpOnlineShopCustomerInfoModule {}
