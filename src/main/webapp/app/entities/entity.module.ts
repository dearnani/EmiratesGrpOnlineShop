import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EmiratesGrpOnlineShopProductInfoModule } from './product-info/product-info.module';
import { EmiratesGrpOnlineShopDealerInfoModule } from './dealer-info/dealer-info.module';
import { EmiratesGrpOnlineShopPaymentDetailsModule } from './payment-details/payment-details.module';
import { EmiratesGrpOnlineShopLocationModule } from './location/location.module';
import { EmiratesGrpOnlineShopCustomerInfoModule } from './customer-info/customer-info.module';
import { EmiratesGrpOnlineShopOrderMangementModule } from './order-mangement/order-mangement.module';
import { EmiratesGrpOnlineShopDeliverManagementModule } from './deliver-management/deliver-management.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        EmiratesGrpOnlineShopProductInfoModule,
        EmiratesGrpOnlineShopDealerInfoModule,
        EmiratesGrpOnlineShopPaymentDetailsModule,
        EmiratesGrpOnlineShopLocationModule,
        EmiratesGrpOnlineShopCustomerInfoModule,
        EmiratesGrpOnlineShopOrderMangementModule,
        EmiratesGrpOnlineShopDeliverManagementModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EmiratesGrpOnlineShopEntityModule {}
