import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmiratesGrpOnlineShopSharedModule } from '../../shared';
import {
    ProductInfoService,
    ProductInfoPopupService,
    ProductInfoComponent,
    ProductInfoDetailComponent,
    ProductInfoDialogComponent,
    ProductInfoPopupComponent,
    ProductInfoDeletePopupComponent,
    ProductInfoDeleteDialogComponent,
    productInfoRoute,
    productInfoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...productInfoRoute,
    ...productInfoPopupRoute,
];

@NgModule({
    imports: [
        EmiratesGrpOnlineShopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductInfoComponent,
        ProductInfoDetailComponent,
        ProductInfoDialogComponent,
        ProductInfoDeleteDialogComponent,
        ProductInfoPopupComponent,
        ProductInfoDeletePopupComponent,
    ],
    entryComponents: [
        ProductInfoComponent,
        ProductInfoDialogComponent,
        ProductInfoPopupComponent,
        ProductInfoDeleteDialogComponent,
        ProductInfoDeletePopupComponent,
    ],
    providers: [
        ProductInfoService,
        ProductInfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EmiratesGrpOnlineShopProductInfoModule {}
