import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EmiratesGrpOnlineShopSharedModule } from '../../shared';
import {
    DealerInfoService,
    DealerInfoPopupService,
    DealerInfoComponent,
    DealerInfoDetailComponent,
    DealerInfoDialogComponent,
    DealerInfoPopupComponent,
    DealerInfoDeletePopupComponent,
    DealerInfoDeleteDialogComponent,
    dealerInfoRoute,
    dealerInfoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dealerInfoRoute,
    ...dealerInfoPopupRoute,
];

@NgModule({
    imports: [
        EmiratesGrpOnlineShopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DealerInfoComponent,
        DealerInfoDetailComponent,
        DealerInfoDialogComponent,
        DealerInfoDeleteDialogComponent,
        DealerInfoPopupComponent,
        DealerInfoDeletePopupComponent,
    ],
    entryComponents: [
        DealerInfoComponent,
        DealerInfoDialogComponent,
        DealerInfoPopupComponent,
        DealerInfoDeleteDialogComponent,
        DealerInfoDeletePopupComponent,
    ],
    providers: [
        DealerInfoService,
        DealerInfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EmiratesGrpOnlineShopDealerInfoModule {}
