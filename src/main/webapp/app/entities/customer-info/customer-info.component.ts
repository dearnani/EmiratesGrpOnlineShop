import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CustomerInfo } from './customer-info.model';
import { CustomerInfoService } from './customer-info.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-customer-info',
    templateUrl: './customer-info.component.html'
})
export class CustomerInfoComponent implements OnInit, OnDestroy {
customerInfos: CustomerInfo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private customerInfoService: CustomerInfoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.customerInfoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.customerInfos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCustomerInfos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CustomerInfo) {
        return item.id;
    }
    registerChangeInCustomerInfos() {
        this.eventSubscriber = this.eventManager.subscribe('customerInfoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
