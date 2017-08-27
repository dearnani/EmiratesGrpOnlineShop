import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PaymentDetails } from './payment-details.model';
import { PaymentDetailsService } from './payment-details.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-payment-details',
    templateUrl: './payment-details.component.html'
})
export class PaymentDetailsComponent implements OnInit, OnDestroy {
paymentDetails: PaymentDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private paymentDetailsService: PaymentDetailsService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.paymentDetailsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.paymentDetails = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPaymentDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PaymentDetails) {
        return item.id;
    }
    registerChangeInPaymentDetails() {
        this.eventSubscriber = this.eventManager.subscribe('paymentDetailsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
