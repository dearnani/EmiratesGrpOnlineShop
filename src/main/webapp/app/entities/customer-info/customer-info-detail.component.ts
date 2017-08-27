import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CustomerInfo } from './customer-info.model';
import { CustomerInfoService } from './customer-info.service';

@Component({
    selector: 'jhi-customer-info-detail',
    templateUrl: './customer-info-detail.component.html'
})
export class CustomerInfoDetailComponent implements OnInit, OnDestroy {

    customerInfo: CustomerInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private customerInfoService: CustomerInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerInfos();
    }

    load(id) {
        this.customerInfoService.find(id).subscribe((customerInfo) => {
            this.customerInfo = customerInfo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCustomerInfos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'customerInfoListModification',
            (response) => this.load(this.customerInfo.id)
        );
    }
}
