import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { JhipMicroserviceGway01SharedModule, UserRouteAccessService } from './shared';
import { JhipMicroserviceGway01HomeModule } from './home/home.module';
import { JhipMicroserviceGway01AdminModule } from './admin/admin.module';
import { JhipMicroserviceGway01AccountModule } from './account/account.module';
import { JhipMicroserviceGway01EntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        JhipMicroserviceGway01SharedModule,
        JhipMicroserviceGway01HomeModule,
        JhipMicroserviceGway01AdminModule,
        JhipMicroserviceGway01AccountModule,
        JhipMicroserviceGway01EntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class JhipMicroserviceGway01AppModule {}
